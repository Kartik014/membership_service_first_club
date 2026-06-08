package com.firstclub.membership.repository;

import com.firstclub.membership.entity.MembershipHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipHistoryRepository extends JpaRepository<MembershipHistory, Long> {

}
