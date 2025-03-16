package zeh.projects.Task_App.Products.DTO;

import lombok.Data;

@Data
public class productResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private long category_id;
    private boolean available;
    private String image_url;
    private long user_id;
}
