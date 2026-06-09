package com.firstclub.membership.repository;

import com.firstclub.membership.entity.Membership;
import com.firstclub.membership.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByUser_IdAndStatus(Long userId, MembershipStatus status);
    Optional<Membership> findByIdAndStatus(Long id, MembershipStatus status);
    boolean existsByUser_IdAndStatus(Long userId, MembershipStatus status);
}
