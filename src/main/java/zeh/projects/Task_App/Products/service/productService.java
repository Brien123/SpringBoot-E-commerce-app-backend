package zeh.projects.Task_App.Products.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import zeh.projects.Task_App.Products.DAO.categoryDAO;
import zeh.projects.Task_App.Products.DAO.productDAO;
import zeh.projects.Task_App.Products.DTO.productCreateDTO;
import zeh.projects.Task_App.Products.DTO.productResponseDTO;
import zeh.projects.Task_App.Products.DTO.productUpdateDTO;
import zeh.projects.Task_App.Products.models.Category;
import zeh.projects.Task_App.Products.models.Product;
//import zeh.projects.Task_App.Products.productMapper;
import zeh.projects.Task_App.Products.utils.ProductMapper;
import zeh.projects.Task_App.Utils.ImageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zeh.projects.Task_App.users.DAO.userDAO;
import zeh.projects.Task_App.users.models.User;

import java.io.IOException;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
public class productService {
    @Autowired
    private productDAO productDAO;

    @Autowired
    private categoryDAO categoryDAO;

    @Autowired
    private ImageUtils imageUtils;

//    @Autowired
//    private productMapper productMapper;

    @Autowired
    private userDAO userDAO;

    @Autowired
    private ProductMapper productsMapper;

    public productResponseDTO create(productCreateDTO productCreateDTO) throws IOException {
        try{
            User user = userDAO.findById(productCreateDTO.getUser_id()).orElseThrow(() -> new EntityNotFoundException("User not found"));
            if(user==null){
                throw new EntityNotFoundException("user not found");
            }
            Category category = categoryDAO.findById(productCreateDTO.getCategory_id());

            if(category==null){
                throw new EntityNotFoundException("Category not found");
            }
            Product product = productsMapper.toEntity(productCreateDTO, user, category);
            productDAO.save(product);
            return productsMapper.toDTO(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public productResponseDTO get(long id){
        try {
            Product product = productDAO.findById(id);
            if (product == null) {
                throw new EntityNotFoundException("Product not found");
            }
            return productsMapper.toDTO(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<productResponseDTO> getAllProducts(User user, int page) {
        try {
            int pageSize = 20;
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Product> productPage = productDAO.findByUser(user, pageable);

            return productPage.getContent()
                    .stream()
                    .map(productsMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public productResponseDTO update(long id, long user_id, productUpdateDTO productUpdateDTO) {
        Product product = productDAO.findById(id);
        if(product==null){
            throw new EntityNotFoundException("Product now found");
        }

        User user = userDAO.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!product.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User not authorized to update this product");
        }



        productsMapper.updateEntity(product, productUpdateDTO);
        productDAO.save(product);

        return productsMapper.toDTO(product);
    }



}