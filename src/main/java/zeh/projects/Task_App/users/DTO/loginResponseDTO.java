package zeh.projects.Task_App.users.DTO;

import lombok.Data;

@Data
public class loginResponseDTO {
    private userResponseDTO user;
    private String token;

    public loginResponseDTO(userResponseDTO userResponseDTO, String token) {
        this.user = userResponseDTO;
        this.token = token;
    }
}
