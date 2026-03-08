create table if not exists user_follow
(
    follower_id  varchar                  REFERENCES "user"(id),
    following_id varchar                  REFERENCES "user"(id),

    is_following boolean                  not null default true,

    created_at   timestamp with time zone not null default now(),

    -- Composite primary key
    constraint user_follow_pk
        primary key (follower_id, following_id),

    -- Ensure the combination is unique (redundant but explicit)
    constraint user_follow_unique_pair
        unique (follower_id, following_id),

    -- Foreign keys
    constraint user_follow_follower_fk
        foreign key (follower_id)
            references "user" (id)
            on delete cascade,

    constraint user_follow_following_fk
        foreign key (following_id)
            references "user" (id)
            on delete cascade,

    -- Prevent self-follow
    constraint user_follow_no_self_follow
        check (follower_id <> following_id)
);