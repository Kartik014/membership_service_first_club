package com.firstclub.membership.repository;


import com.firstclub.membership.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiersRepository extends JpaRepository<Tier, Long> {
}
