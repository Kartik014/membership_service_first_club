package com.firstclub.membership.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tier_benefit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierBenefit {
    @EmbeddedId
    private TierBenefitId id;

    @ManyToOne
    @MapsId("tierId")
    @JoinColumn(name = "tier_id")
    private Tier tier;

    @ManyToOne
    @MapsId("benefitId")
    @JoinColumn(name = "benefit_id")
    private Benefit benefit;

}
