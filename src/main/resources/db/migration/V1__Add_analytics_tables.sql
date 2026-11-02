-- Create match_analytics table
CREATE TABLE match_analytics (
    match_id VARCHAR(255) PRIMARY KEY,
    total_views BIGINT NOT NULL
);

-- Create daily_user_stats table
CREATE TABLE daily_user_stats (
    date DATE PRIMARY KEY,
    active_users BIGINT NOT NULL
);