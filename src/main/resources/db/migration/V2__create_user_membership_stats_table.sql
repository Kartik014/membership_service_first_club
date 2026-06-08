CREATE TABLE user_membership_stats (
    user_id BIGINT PRIMARY KEY,
    total_orders INTEGER NOT NULL DEFAULT 0,
    monthly_orders INTEGER NOT NULL DEFAULT 0,
    monthly_spend NUMERIC(12,2) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP,
    CONSTRAINT fk_stats_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);