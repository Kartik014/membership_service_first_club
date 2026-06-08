CREATE TABLE tier_benefit (
    tier_id BIGINT NOT NULL,
    benefit_id BIGINT NOT NULL,
    PRIMARY KEY(tier_id, benefit_id),
    CONSTRAINT fk_tb_tier
        FOREIGN KEY(tier_id)
        REFERENCES tiers(id),
    CONSTRAINT fk_tb_benefit
        FOREIGN KEY(benefit_id)
        REFERENCES benefits(id)
);