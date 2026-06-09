package com.firstclub.membership.repository;

import com.firstclub.membership.entity.TierBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TierBenefitRepository extends JpaRepository<TierBenefit, Long> {
    Optional<List<TierBenefit>> findByTier_Id(Long tierId);
}
