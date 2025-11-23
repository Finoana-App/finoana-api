CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

do
$$
    begin
        if not exists(select from pg_type where typname = 'user_role') then
            create type user_role as enum ('COMMON', 'MANAGER');
        end if;
        if not exists(select from pg_type where typname = 'user_status') then
            create type user_status as enum ('ACTIVATED', 'INACTIVATED', 'DISABLED');
        end if;
    end
$$;

create table if not exists "user"
(
    id                      varchar
        constraint user_pk primary key                        default uuid_generate_v4(),
    email                   varchar                  not null
        constraint user_email_unique unique,
    first_name              varchar                  not null,
    last_name               varchar                  not null,
    display_name            varchar                  not null,
    bio                     text                              default '',
    avatar_url              varchar,
    role                    user_role                not null,
    status                  user_status              not null default 'ACTIVATED',
    is_anonymous_by_default boolean                           default false,
    email_verified          boolean                           default false,
    created_at              timestamp with time zone not null,
    updated_at              timestamp with time zone not null,
    last_login              timestamp with time zone
);
