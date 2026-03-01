-- 1. Rename old enum
ALTER TYPE user_role RENAME TO user_role_old;

-- 2. Create new enum
CREATE TYPE user_role AS ENUM ('USER', 'MODERATOR', 'ADMIN');

-- 3. Alter column to use new enum
ALTER TABLE "user"
    ALTER COLUMN role DROP DEFAULT,
    ALTER COLUMN role TYPE user_role
        USING (
        CASE role::text
            WHEN 'COMMON' THEN 'USER'
            WHEN 'MANAGER' THEN 'ADMIN'
            ELSE 'USER'
            END
        )::user_role,
    ALTER COLUMN role SET DEFAULT 'USER';

-- 4. Drop old enum
DROP TYPE user_role_old;