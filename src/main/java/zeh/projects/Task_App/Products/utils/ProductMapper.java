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
import zeh.projects.Task_App.Products.DAO.categoryDAO;
import zeh.projects.Task_App.Products.DAO.productDAO;
import java.io.IOException;
import java.util.Optional;

@Component
public class ProductMapper {

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private categoryDAO categoryDAO;

    @Autowired
    private productDAO productDAO;

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
        if(product.getCategory() != null){
            dto.setCategory_name(product.getCategory().getName());
        }
        return dto;
    }

    public Product updateEntity(long id, productUpdateDTO dto) throws IOException {
        Product product = productDAO.findById(id);
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getCategory_id() != null) {
            long cat_id = dto.getCategory_id();
            Category category = categoryDAO.findById(cat_id);
            product.setCategory(category);
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getAvailable() != null) {
            product.setAvailable(dto.getAvailable());
        }
        if (dto.getImage() != null) {
            product.setImage_url(imageUtils.saveImage(dto.getImage()));
        }
        return product;
    }

}
