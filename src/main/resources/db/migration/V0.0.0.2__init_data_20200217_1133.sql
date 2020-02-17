INSERT INTO `tbl_country` (`country_id`, `country_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                           `deleted_by`, `deleted_date`)
VALUES ('1', 'Viet Nam', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'Japan', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_language` (`language_id`, `language_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                            `deleted_by`, `deleted_date`)
VALUES ('1', 'English', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'Japanese', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_department` (`department_id`, `department_code`, `department_name`, `created_by`, `created_date`,
                              `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'TVJ', 'Tinh Van Japan', 'System', '2020-02-06 11:09:00', NULL, NULL, NULL, NULL),
       ('2', 'TVO', 'Tinh Van Outsourcing', 'System', '2020-02-06 11:09:00', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_permission` (`permission_id`, `permission_name`, `permission_url`, `created_by`, `created_date`,
                              `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'URL1', '/user/{id}/list/{id}', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', 'URL2', '/user/{id}/list2/{id}', 'DD', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('3', 'URL3', '/user/user-setting-get-info', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('4', 'URL4', '/user/user-setting-update-info', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('5', 'URL5', '/user/user-setting-update-password', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('6', 'URL6', '/department/list', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('7', 'URL7', '/team/list-by-department', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('8', 'URL8', '/country/list', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('9', 'URL9', '/language/list', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('10', 'URL10', '/user/upload-avatar', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('11', 'URL11', '/user/remove-avatar', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_role` (`role_id`, `role_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                        `deleted_by`, `deleted_date`)
VALUES ('1', 'ROOT', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', 'ADMIN', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_role_permission` (`role_id`, `permission_id`)
VALUES ('1', '1'),
       ('2', '1'),
       ('1', '2'),
       ('1', '3'),
       ('2', '3'),
       ('1', '4'),
       ('2', '4'),
       ('1', '5'),
       ('2', '5'),
       ('1', '6'),
       ('2', '6'),
       ('1', '7'),
       ('2', '7'),
       ('1', '8'),
       ('2', '8'),
       ('1', '9'),
       ('2', '9'),
       ('1', '10'),
       ('2', '10'),
       ('1', '11'),
       ('2', '11');


INSERT INTO `tbl_team` (`team_id`, `team_code`, `team_name`, `department_id`, `created_by`, `created_date`,
                        `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'TEAM001', 'Team 1', '1', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'TEAM002', 'Team 2', '2', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('3', 'TEAM003', 'Team 3', '2', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_user_setting` (`user_setting_id`, `team_id`, `title`, `address`, `phone`, `country_id`,
                                `language_id`, `status`, `avatar`, `created_by`, `created_date`, `updated_by`,
                                `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', '1', 'Dev', 'homie', '121142342', '1', '1', 1, NULL, 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL,
        NULL),
       ('2', '1', 'Dev2', 'homie', '121142342', '1', '1', 1, NULL, 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL,
        NULL);


INSERT INTO `tbl_user` (`user_id`, `user_setting_id`, `role_id`, `username`, `password`, `first_name`, `last_name`,
                        `email`, `active`, `is_first_time_login`,
                        `login_fail_count`, `created_by`, `created_date`, `updated_by`, `updated_date`, `deleted_by`,
                        `deleted_date`)
VALUES ('1', '1', '1', 'root', '$2y$12$ipT2RRvG/HaRxeuaNRGm0eVfg0wdXUFot5CM4/IIstD8u.Qn4vkju', 'Dinh', 'Ngoc',
        'ngocdc@tinhvan.com', 1, 1, 0, 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', '2', '2', 'admin', '$2y$12$ipT2RRvG/HaRxeuaNRGm0eVfg0wdXUFot5CM4/IIstD8u.Qn4vkju', 'Hoang', 'Hung',
        'hunghx@tinhvan.com', 1, 0, 0, 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);

