do
$$
    begin
        if not exists(select from pg_type where typname = 'role') then
            create type "role" as enum ('COMMON', 'MANAGER');
        end if;
        if not exists(select from pg_type where typname = 'sex') then
            create type sex as enum ('M', 'F');
        end if;
    end
$$;

create table if not exists "user"
(
    id                varchar
        constraint user_pk primary key default uuid_generate_v4(),
    first_name        varchar                  not null,
    last_name         varchar                  not null,
    email             varchar                  not null
        constraint user_email_unique unique,
    sex               sex                      not null,
    birth_date        date                     not null,
    role              role                     not null
);
