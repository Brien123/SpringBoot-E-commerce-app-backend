package zeh.projects.Task_App.users;

import org.springframework.stereotype.Component;
import zeh.projects.Task_App.users.DTO.userResponseDTO;
import zeh.projects.Task_App.users.models.User;

@Component
public class usersMapper {
    public userResponseDTO toDTO(User user){
        userResponseDTO dto = new userResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }
}
