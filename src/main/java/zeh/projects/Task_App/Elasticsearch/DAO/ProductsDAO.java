package zeh.projects.Task_App.Elasticsearch.DAO;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zeh.projects.Task_App.Elasticsearch.Models.Products;

public interface ProductsDAO extends ElasticsearchRepository<Products, String> {

}
