package com.firstclub.membership.repository;

import com.firstclub.membership.entity.UserMembershipStats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserMembershipStatsRepository extends JpaRepository<UserMembershipStats, Long> {
    Optional<UserMembershipStats> findByUser_Id(Long userId);
}
