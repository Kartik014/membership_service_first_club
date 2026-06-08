CREATE TABLE membership (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    tier_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    auto_renew BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_membership_user
        FOREIGN KEY(user_id)
        REFERENCES users(id),

    CONSTRAINT fk_membership_plan
        FOREIGN KEY(plan_id)
        REFERENCES membership_plans(id),

    CONSTRAINT fk_membership_tier
        FOREIGN KEY(tier_id)
        REFERENCES tiers(id)
);