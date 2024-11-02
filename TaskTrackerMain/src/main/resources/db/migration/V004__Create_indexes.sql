CREATE UNIQUE INDEX email_idx ON users (email);
CREATE UNIQUE INDEX username_idx ON users (username);
CREATE UNIQUE INDEX task_find_idx ON tasks (project_id, task_name);
CREATE INDEX comment_idx ON comments (task_id);
CREATE INDEX developers_idx ON developers (project_id, user_id);
