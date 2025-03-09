package zeh.projects.Task_App.users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import zeh.projects.Task_App.users.DTO.userResponseDTO;
import zeh.projects.Task_App.users.models.User;

@Mapper(componentModel = "spring")
public interface userMapper {
    userMapper INSTANCE = Mappers.getMapper(userMapper.class);

    userResponseDTO userToUserResponseDto(User user);
}
