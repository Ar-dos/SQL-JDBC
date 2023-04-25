create table users
(
    id       serial primary key,
    login    varchar not null,
    password varchar not null
);

create table chatrooms
(
    id       serial primary key,
    name     varchar not null,
    owner_id bigint  not null,
    constraint fk_owner_user_id foreign key (owner_id) references users (id)
);

create table messages
(
    id          serial primary key,
    author_id   bigint      not null,
    chatroom_id bigint      not null,
    text        varchar,
    date_time   timestamptz not null,
    constraint fk_author_user_id foreign key (author_id) references users (id),
    constraint fk_room_id foreign key (chatroom_id) references chatrooms (id)
);

create table users_chatrooms
(
    user_id bigint REFERENCES users (id) ON UPDATE CASCADE,
    room_id bigint REFERENCES chatrooms (id) ON UPDATE CASCADE,
    CONSTRAINT users_chatrooms_pkey PRIMARY KEY (user_id, room_id)
);