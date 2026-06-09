package com.firstclub.membership.repository;

import com.firstclub.membership.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TiersRepository extends JpaRepository<Tier, Long> {
    Optional<List<Tier>> findAllByOrderByPriorityAsc();
}
