DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_type
            WHERE typname = 'post_visibility'
        ) THEN
            CREATE TYPE post_visibility AS ENUM ('PUBLIC', 'PRIVATE', 'FOLLOWERS_ONLY');
        END IF;
    END
$$;

CREATE TABLE if not exists post
(
    id                  VARCHAR(255) PRIMARY KEY,
    author_id           VARCHAR(255) REFERENCES "user"(id),
    content             TEXT,
    visibility          post_visibility,
    anonymous           BOOLEAN                  DEFAULT FALSE,
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP WITH TIME ZONE
);
