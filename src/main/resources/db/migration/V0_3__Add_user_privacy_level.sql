-- 1. Create enum only if it does not exist
DO $$
BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_type
            WHERE typname = 'privacy_level'
        ) THEN
    CREATE TYPE privacy_level AS ENUM ('PUBLIC', 'PRIVATE', 'ANONYMOUS');
    END IF;
END
$$;

-- 2. Add column only if it does not exist
ALTER TABLE "user"
    ADD COLUMN IF NOT EXISTS privacy_level privacy_level
    NOT NULL DEFAULT 'PUBLIC';