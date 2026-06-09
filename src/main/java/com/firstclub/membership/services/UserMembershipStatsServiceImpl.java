package com.firstclub.membership.services;

import com.firstclub.membership.DTO.Requests.UpdateUserMembershipStatsRequestDTO;
import com.firstclub.membership.DTO.Responses.UserMembershipStatsResponseDTO;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.entity.UserMembershipStats;
import com.firstclub.membership.exceptions.ResourceNotFoundException;
import com.firstclub.membership.interfaces.UserMembershipStatsService;
import com.firstclub.membership.repository.UserMembershipStatsRepository;
import com.firstclub.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserMembershipStatsServiceImpl implements UserMembershipStatsService {
    private final UserMembershipStatsRepository userMembershipStatsRepository;
    private final UserRepository userRepository;

    @Override
    public UserMembershipStatsResponseDTO addUserStats(Long userId, UpdateUserMembershipStatsRequestDTO updateUserMembershipStatsRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserMembershipStats stats = userMembershipStatsRepository.findByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("User membership stats not found"));

        stats.setTotalOrders(updateUserMembershipStatsRequestDTO.totalOrders());
        stats.setMonthlySpend(updateUserMembershipStatsRequestDTO.monthlySpend());
        stats.setMonthlyOrders(updateUserMembershipStatsRequestDTO.monthlyOrders());
        stats.setLastUpdated(LocalDateTime.now());

        userMembershipStatsRepository.save(stats);

        return new UserMembershipStatsResponseDTO(
                stats.getUserId(),
                stats.getTotalOrders(),
                stats.getMonthlyOrders(),
                stats.getMonthlySpend()
        );
    }
}
