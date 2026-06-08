CREATE TABLE membership_history (
    id BIGSERIAL PRIMARY KEY,
    membership_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    old_tier_id BIGINT,
    new_tier_id BIGINT,
    remarks VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_history_membership
        FOREIGN KEY(membership_id)
        REFERENCES membership(id),

    CONSTRAINT fk_history_old_tier
        FOREIGN KEY(old_tier_id)
        REFERENCES tiers(id),

    CONSTRAINT fk_history_new_tier
        FOREIGN KEY(new_tier_id)
        REFERENCES tiers(id)
);

CREATE UNIQUE INDEX uq_active_membership_user
    ON membership(user_id)
    WHERE status = 'ACTIVE';