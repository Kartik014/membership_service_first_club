CREATE TABLE tier_criteria (
    id BIGSERIAL PRIMARY KEY,
    tier_id BIGINT NOT NULL,
    criteria_type VARCHAR(50) NOT NULL,
    operator VARCHAR(10) NOT NULL,
    threshold_value VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tc_tier
        FOREIGN KEY(tier_id)
        REFERENCES tiers(id)
);