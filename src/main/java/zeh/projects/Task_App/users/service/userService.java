package zeh.projects.Task_App.users.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zeh.projects.Task_App.users.DAO.userDAO;
import zeh.projects.Task_App.users.DAO.roleDAO;
import zeh.projects.Task_App.users.DTO.*;
import zeh.projects.Task_App.users.JWTUtil;
import zeh.projects.Task_App.users.models.Role;
import zeh.projects.Task_App.users.models.User;
import zeh.projects.Task_App.users.userMapper;
import zeh.projects.Task_App.users.usersMapper;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class userService {

    @Autowired
    private userDAO userDAO;

    @Autowired
    private roleDAO roleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private userMapper userMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private usersMapper usersMapper;

    public userResponseDTO create(userRegistrationDTO userRegistrationDTO){
        if(userDAO.existsByUsername(userRegistrationDTO.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userDAO.existsByEmail(userRegistrationDTO.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        Role userRole = roleDao.findByName("USER");
        if(userRole==null){
            throw new NoSuchElementException("Role not found");
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userDAO.save(user);
        return usersMapper.toDTO(user);
    }

    public userResponseDTO update(long id, userUpdateDTO updateDTO){
        User user = userDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        if(userDAO.existsByUsername(updateDTO.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userDAO.existsByEmail(updateDTO.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        if (updateDTO.getUsername() != null && !user.getUsername().equals(updateDTO.getUsername())) {
            if (userDAO.existsByUsername(updateDTO.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(updateDTO.getUsername());
        }

        if (updateDTO.getEmail() != null && !user.getEmail().equals(updateDTO.getEmail())) {
            if (userDAO.existsByEmail(updateDTO.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(updateDTO.getEmail());
        }

        if (updateDTO.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(updateDTO.getPassword()));
        }

        userDAO.save(user);
        return usersMapper.toDTO(user);
    }

    public userResponseDTO get(String name){
        try {
            User user = userDAO.findByUsername(name);
            if (user == null) {
                throw new EntityNotFoundException("User not found with username: " + name);
            }
            System.out.println(user);
            return usersMapper.toDTO(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id){
        User user = userDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userDAO.delete(user);
    }

    public String deactivate(long id){
        User user = userDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setEnabled(false);
        userDAO.save(user);
        return "User deactivated successfully";
    }

    public  Iterable<userResponseDTO> get(){
        return userDAO.findAll().stream()
                .map(usersMapper::toDTO)
                .collect(Collectors.toList());
    }

    public loginResponseDTO login(loginRequestDTO loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            User user = userDAO.findByUsername(username);
            if (user == null) {
                throw new EntityNotFoundException("User not found with username: " + username);
            }

            if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid credentials");
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoles()));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            String token = jwtUtil.generateToken(username, user.getRoles());

            return new loginResponseDTO(usersMapper.toDTO(user), token);
        } catch (Exception e) {

            System.err.println("Login error: " + e.getClass().getName() + ": " + e.getMessage());
            throw e;
        }
    }

    public String logout(String token){
        SecurityContextHolder.clearContext();
        return "Logged out successfully";
    }
}
