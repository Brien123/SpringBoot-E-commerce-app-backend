package zeh.projects.Task_App.Products.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import zeh.projects.Task_App.Products.models.Category;
import zeh.projects.Task_App.users.models.User;

@Data
public class productCreateDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message="Description is required")
    private String description;

    @NotBlank(message="Price is required")
    private Double price;

    @NotBlank(message = "Category id is required")
    private long category_id;

    @NotBlank(message = "Available is required")
    private boolean available;

    @NotBlank(message="user id is required")
    private long user_id;

    @NotBlank(message = "image is required")
    private MultipartFile image;

}
