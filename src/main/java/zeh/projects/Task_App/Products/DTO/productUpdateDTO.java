package zeh.projects.Task_App.Products.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import zeh.projects.Task_App.Products.models.Category;

@Data
public class productUpdateDTO {
    private String name;
    private String description;
    private Double price;
    private Category category;
    private Boolean available;
    private MultipartFile image;
}
