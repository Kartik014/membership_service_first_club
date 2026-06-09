package com.firstclub.membership.repository;

import com.firstclub.membership.entity.MembershipHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipHistoryRepository extends JpaRepository<MembershipHistory, Long> {
    Optional<List<MembershipHistory>> findByMembership_Id(Long membershipId);
}
