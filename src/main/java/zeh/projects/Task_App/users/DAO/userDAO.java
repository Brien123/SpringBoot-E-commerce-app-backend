package zeh.projects.Task_App.users.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zeh.projects.Task_App.users.models.User;

public interface userDAO extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
