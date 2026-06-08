package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.CreateUserRequestDTO;
import com.firstclub.membership.DTO.UserResponseDTO;
import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO addNewUser(CreateUserRequestDTO createUserRequestDTO);
    UserResponseDTO getUser(Long id);
}
