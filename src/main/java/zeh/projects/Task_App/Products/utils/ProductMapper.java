package zeh.projects.Task_App.Products.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zeh.projects.Task_App.Products.DTO.productCreateDTO;
import zeh.projects.Task_App.Products.DTO.productResponseDTO;
import zeh.projects.Task_App.Products.DTO.productUpdateDTO;
import zeh.projects.Task_App.Products.models.Category;
import zeh.projects.Task_App.Products.models.Product;
import zeh.projects.Task_App.Utils.ImageUtils;
import zeh.projects.Task_App.users.models.User;

import java.io.IOException;

@Component
public class ProductMapper {

    @Autowired
    private ImageUtils imageUtils;

    public Product toEntity(productCreateDTO dto, User user, Category category) throws IOException {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(category);
        product.setAvailable(dto.isAvailable());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUser(user);
        if (dto.getImage() != null) {
            product.setImage_url(imageUtils.saveImage(dto.getImage()));
        }
        return product;
    }

    public productResponseDTO toDTO(Product product) {
        productResponseDTO dto = new productResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailable(product.isAvailable());
        dto.setImage_url(product.getImage_url());

        if (product.getCategory() != null) {
            dto.setCategory_id(product.getCategory().getId());
        }

        if (product.getUser() != null) {
            dto.setUser_id(product.getUser().getId());
        }

        return dto;
    }

    public void updateEntity(Product product, productUpdateDTO dto) {
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getCategory() != null) {
            product.setCategory(dto.getCategory());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getAvailable() != null) {
            product.setAvailable(true);
        }

    }

}
