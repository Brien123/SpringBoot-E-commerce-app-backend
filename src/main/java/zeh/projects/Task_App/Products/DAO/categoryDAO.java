package zeh.projects.Task_App.Products.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zeh.projects.Task_App.Products.models.Category;

public interface categoryDAO extends JpaRepository<Category, Long> {
    Category findById(long id);
}
