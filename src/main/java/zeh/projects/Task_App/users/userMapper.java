package zeh.projects.Task_App.users;
import org.mapstruct.Mapper;
import zeh.projects.Task_App.users.DTO.userResponseDTO;
import zeh.projects.Task_App.users.models.User;

@Mapper(componentModel = "spring")
public interface userMapper {
    userResponseDTO userToUserResponseDto(User user);
}

