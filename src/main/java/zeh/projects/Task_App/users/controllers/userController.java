package zeh.projects.Task_App.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zeh.projects.Task_App.users.DTO.*;
import zeh.projects.Task_App.users.service.userService;

@RestController
@RequestMapping("/users")
public class userController {

    @Autowired
    private userService userService;

    @PostMapping("/signup")
    public ResponseEntity<userResponseDTO> signup(@RequestBody userRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.ok(userService.create(userRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<loginResponseDTO> login(@RequestBody loginRequestDTO loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<userResponseDTO> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(userService.get(username));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<userResponseDTO> update(@PathVariable long id, @RequestBody userUpdateDTO updateDTO) {
        return ResponseEntity.ok(userService.update(id, updateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivate(@PathVariable long id) {
        return ResponseEntity.ok(userService.deactivate(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<userResponseDTO>> get() {
        return ResponseEntity.ok(userService.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
}
