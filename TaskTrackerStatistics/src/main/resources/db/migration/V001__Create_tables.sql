CREATE TABLE users (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    username varchar(255) UNIQUE NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE user_updates (
    id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id bigint,
    action_name varchar(15),
    action_time TIMESTAMP NOT NULL,
    action_msg text NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);