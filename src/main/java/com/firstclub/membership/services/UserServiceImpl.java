package com.firstclub.membership.services;

import com.firstclub.membership.DTO.CreateUserRequestDTO;
import com.firstclub.membership.DTO.UserResponseDTO;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.interfaces.UserService;
import com.firstclub.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private UserResponseDTO mapToResponse(User user){
        return new UserResponseDTO(
                user.getUserCode(),
                user.getName(),
                user.getEmail(),
                user.getCohort(),
                user.getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getUserCode(),
                        user.getName(),
                        user.getEmail(),
                        user.getEmail(),
                        user.getId()
                ))
                .toList();
    }

    @Override
    public UserResponseDTO addNewUser(CreateUserRequestDTO createUserRequestDTO) {
        if(userRepository.existsByEmail(createUserRequestDTO.email())){
            throw new IllegalStateException("Email already exists");
        }

        User newUser = User.builder()
                .userCode(UUID.randomUUID())
                .name(createUserRequestDTO.name())
                .cohort(createUserRequestDTO.cohort())
                .email(createUserRequestDTO.email())
                .build();

        newUser = userRepository.save(newUser);

        return mapToResponse(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id){
        if(!userRepository.existsById(id)){
            throw new IllegalStateException("User with id " + id + " does not exist");
        }

        User user = userRepository.findById(id).get();

        return mapToResponse(user);
    }
}
