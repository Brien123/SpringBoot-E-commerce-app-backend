package zeh.projects.Task_App.users.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class loginRequestDTO {
    @NotBlank(message="Enter username")
    private String username;
    @NotBlank(message="Enter Password")
    private String password;
}
