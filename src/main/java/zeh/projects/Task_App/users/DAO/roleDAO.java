package zeh.projects.Task_App.users.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zeh.projects.Task_App.users.models.Role;

public interface roleDAO extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
