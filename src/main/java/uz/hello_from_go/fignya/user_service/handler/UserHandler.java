package uz.hello_from_go.fignya.user_service.handler;

import uz.hello_from_go.fignya.user_service.service.UserService;
import uz.hello_from_go.fignya.user_service.dto.UserRequestDto;
import uz.hello_from_go.fignya.user_service.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        validateUserRequest(requestDto);
        return userService.createUser(requestDto);
    }

    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        return userService.getUserById(id);
    }

    public Optional<UserResponseDto> updateUser(Long id, UserRequestDto requestDto) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        validateUserRequest(requestDto);
        return userService.updateUser(id, requestDto);
    }

    public boolean deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        return userService.deleteUser(id);
    }

    private void validateUserRequest(UserRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Данные пользователя не могут быть null");
        }
        if (requestDto.getName() == null || requestDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя обязательно");
        }
        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email пользователя обязателен");
        }
        if (!isValidEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Неверный формат email");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
    }
}