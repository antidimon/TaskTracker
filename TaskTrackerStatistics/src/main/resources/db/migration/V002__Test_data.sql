INSERT INTO users (id, username, email, created_at) VALUES (1, 'admin', 'ivasenkodiman@mail.ru', CURRENT_TIMESTAMP);
INSERT INTO users (id, username, email, created_at) VALUES (2, 'dima', 'ivasenkodim@gmail.com', CURRENT_TIMESTAMP);
INSERT INTO users (id, username, email, created_at) VALUES (3, 'vova', 'antidimon1930@gmail.com', CURRENT_TIMESTAMP);
INSERT INTO users (id, username, email, created_at) VALUES (4, 'vanya', 'task_tracker_emails@mail.ru', CURRENT_TIMESTAMP);


INSERT INTO user_updates (user_id, action_name, action_time, action_msg) VALUES (1, 'CREATE_ACCOUNT', CURRENT_TIMESTAMP, 'created account admin');
INSERT INTO user_updates (user_id, action_name, action_time, action_msg) VALUES (2, 'CREATE_ACCOUNT', CURRENT_TIMESTAMP, 'created account dima');
INSERT INTO user_updates (user_id, action_name, action_time, action_msg) VALUES (3, 'CREATE_ACCOUNT', CURRENT_TIMESTAMP, 'created account vova');
INSERT INTO user_updates (user_id, action_name, action_time, action_msg) VALUES (4, 'CREATE_ACCOUNT', CURRENT_TIMESTAMP, 'created account vanya');


