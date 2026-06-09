package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.Responses.BenefitResponseDTO;
import com.firstclub.membership.DTO.Requests.CreateUserRequestDTO;
import com.firstclub.membership.DTO.Responses.MembershipResponseDTO;
import com.firstclub.membership.DTO.Responses.UserResponseDTO;
import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO addNewUser(CreateUserRequestDTO createUserRequestDTO);
    UserResponseDTO getUser(Long id);
    MembershipResponseDTO getUserActiveMembership(Long userId);
    List<BenefitResponseDTO> getUserBenefits(Long userId);
}
