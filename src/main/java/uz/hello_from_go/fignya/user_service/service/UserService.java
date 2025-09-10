package uz.hello_from_go.fignya.user_service.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import uz.hello_from_go.fignya.user_service.dto.UserRequestDto;
import uz.hello_from_go.fignya.user_service.dto.UserResponseDto;
import uz.hello_from_go.fignya.user_service.model.User;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getName(), requestDto.getEmail());
        user.setId(idCounter.getAndIncrement());
        users.add(user);
        return mapToResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return users.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public Optional<UserResponseDto> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .map(this::mapToResponseDto)
                .findFirst();
    }

    public Optional<UserResponseDto> updateUser(Long id, UserRequestDto requestDto) {
        Optional<User> userOpt = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(requestDto.getName());
            user.setEmail(requestDto.getEmail());
            return Optional.of(mapToResponseDto(user));
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    private UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}