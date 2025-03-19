package zeh.projects.Task_App.Elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeh.projects.Task_App.Elasticsearch.DAO.ProductsDAO;
import zeh.projects.Task_App.Elasticsearch.Models.Products;
import zeh.projects.Task_App.Products.models.Product;

@Service
public class IndexingService {
    @Autowired
    private ProductsDAO productsDAO;

    public void indexProduct(Product product) {
        Products productElastic = mapToElasticProduct(product);
        productsDAO.save(productElastic);
    }

    public void updateProduct(Product product) {
        Products productElastic = mapToElasticProduct(product);
        productsDAO.save(productElastic);
    }

    public void deleteProduct(Long productId) {
        productsDAO.deleteById(productId.toString());
    }


    private Products mapToElasticProduct(Product product) {
        Products productElastic = new Products();
        productElastic.setId(product.getId().toString());
        productElastic.setName(product.getName());
        productElastic.setDescription(product.getDescription());
        productElastic.setPrice(product.getPrice());
        productElastic.setCategoryId(product.getCategory().getId().toString());
        productElastic.setUserId(product.getUser().getId().toString());
        productElastic.setAvailable(product.isAvailable());
        productElastic.setImageUrl(product.getImage_url());
        productElastic.setCreatedAt(product.getCreatedAt());
        productElastic.setUpdatedAt(product.getUpdatedAt());
        return productElastic;
    }


}
