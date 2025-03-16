package zeh.projects.Task_App.Products.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zeh.projects.Task_App.Products.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zeh.projects.Task_App.users.models.User;

import java.util.List;

public interface productDAO extends JpaRepository<Product, Long> {
    Product findById(long id);

    Page<Product> findByUserId(long userId, Pageable pageable);
    Page<Product> findByUser(User user, Pageable pageable);
}
