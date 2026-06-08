package com.firstclub.membership.entity;

import com.firstclub.membership.enums.MembershipAction;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "membership_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Enumerated(EnumType.STRING)
    private MembershipAction action;

    @ManyToOne
    @JoinColumn(name = "old_tier_id")
    private Tier oldTier;

    @ManyToOne
    @JoinColumn(name = "new_tier_id")
    private Tier newTier;

    private String remarks;

    private LocalDateTime createdAt;
}
