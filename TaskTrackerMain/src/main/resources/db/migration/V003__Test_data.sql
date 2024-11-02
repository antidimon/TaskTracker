-- admin projects
    -- test
INSERT INTO projects (user_id, project_name, is_active, created_at) values (1, 'test', true, CURRENT_TIMESTAMP);

INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test1', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test2', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test3', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test4', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test5', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test6', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test7', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (1, 'test8', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');

INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (1, 1, 'Hello test1', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (2, 1, 'Hello admin', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (2, 2, 'Hello test2', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (4, 3, 'Hello test3', CURRENT_TIMESTAMP);

    --test2
INSERT INTO projects (user_id, project_name, is_active, created_at) values (1, 'test2', true, CURRENT_TIMESTAMP);

INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo1', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo2', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo3', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo4', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo5', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo6', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo7', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (2, 'demo8', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'COMPLETED');

INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (1, 9, 'Hello demo1', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (1, 10, 'Hello demo2', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (2, 11, 'Hello demo3', CURRENT_TIMESTAMP);



--dima projects
    --dimaTest
INSERT INTO projects (user_id, project_name, is_active, created_at) values (2, 'dimaTest', true, CURRENT_TIMESTAMP);

INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima1', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59','TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima2', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima3', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima4', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima5', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima6', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima7', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (3, 'dima8', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');


INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (2, 17, 'Hello dima1', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (3, 18, 'Hello dima2', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (3, 19, 'Hello dima3', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (4, 20, 'Hello dima4', CURRENT_TIMESTAMP);



--vova projects
    --vovaTest
INSERT INTO projects (user_id, project_name, is_active, created_at) values (3, 'vovaTest', true, CURRENT_TIMESTAMP);

INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova1', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova2', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova3', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova4', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova5', 2, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova6', 1, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova7', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'TODO');
INSERT INTO tasks (project_id, task_name, priority, created_at, deadline, task_status) values (4, 'vova8', 3, CURRENT_TIMESTAMP, '2024-12-31 23:59:59', 'IN_PROGRESS');


INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (3, 25, 'Hello vova1', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (2, 25, 'Hello voa', CURRENT_TIMESTAMP);
INSERT INTO comments (user_id, task_id, comment, created_at) VALUES (3, 25, 'Hello noob', CURRENT_TIMESTAMP);



--developers
INSERT INTO developers (project_id, user_id) VALUES (1, 2), (1, 4), (3, 3), (3, 4), (4, 2);
