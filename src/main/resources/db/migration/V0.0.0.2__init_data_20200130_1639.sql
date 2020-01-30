INSERT INTO `tbl_permission` (`permission_id`, `permission_name`, `permission_code`, `created_by`, `created_date`,
                              `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'URL1', 'SCR001', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', 'URL2', 'SCR002', 'DD', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);

INSERT INTO `tbl_role` (`role_id`, `role_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                        `deleted_by`, `deleted_date`)
VALUES ('1', 'ROOT', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', 'ADMIN', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);

INSERT INTO `tbl_role_permission` (`role_id`, `permission_id`)
VALUES ('1', '1'),
       ('2', '1'),
       ('1', '2');

INSERT INTO `tbl_user` (`user_id`, `role_id`, `username`, `password`, `first_name`, `last_name`, `email`, `active`,
                        `login_fail_count`, `forgot_password_token`, `forgot_password_token_expired_date`, `created_by`,
                        `created_date`, `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', '1', 'root', '$2y$12$i.2/5js7PdP6.O1ikIsl2e1soqZCwJYXg5xPQtgoOFCEtrZZ/8iGm', 'X1', 'X11', 'r@r.r', 1, 0,
        NULL, NULL, 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', '2', 'admin', '$2a$10$CxA/SQQbIogEd.Q/xkdjpu1VwOwxpCGrnVq7H3tFyqrcbu2SSRuli', 'Y1', 'Y11', 'a@a.a', 1, 0,
        NULL, NULL, 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);