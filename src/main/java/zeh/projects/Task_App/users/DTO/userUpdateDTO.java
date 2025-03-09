package zeh.projects.Task_App.users.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import zeh.projects.Task_App.users.models.Role;
import java.util.Set;

@Data
public class userUpdateDTO {
    private String username;
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Email(message = "Invalid email format")
    private Set<Role> roles;

}
