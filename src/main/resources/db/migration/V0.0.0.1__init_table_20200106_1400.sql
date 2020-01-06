CREATE TABLE `tbl_user` (
                            `user_id` varchar(36) NOT NULL,
                            `account` varchar(25) NOT NULL,
                            `password` varchar(100) NOT NULL,
                            `first_name` varchar(100) NOT NULL,
                            `last_name` varchar(100) NOT NULL,
                            `email` varchar(75) NOT NULL,
                            `created_by` varchar(36) NOT NULL,
                            `created_date` datetime NOT NULL,
                            `updated_by` varchar(36) DEFAULT NULL,
                            `updated_date` datetime DEFAULT NULL,
                            `deleted_by` varchar(36) DEFAULT NULL,
                            `deleted_date` datetime DEFAULT NULL,
                            PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User information';

CREATE TABLE `tbl_event_decision_status` (
                                            `event_decision_status_code` int(1) NOT NULL,
                                            `event_decision_status_name` varchar(20) NOT NULL,
                                            PRIMARY KEY (`event_decision_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Decision status of event';

CREATE TABLE `tbl_event_status` (
                                    `event_status_code` int(1) NOT NULL,
                                    `event_status_name` varchar(20) NOT NULL,
                                    PRIMARY KEY (`event_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type of event status';

CREATE TABLE `tbl_country` (
                               `country_code` varchar(2) NOT NULL,
                               `country_name` varchar(100) DEFAULT NULL,
                               PRIMARY KEY (`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of countries of users';

CREATE TABLE `tbl_event_type` (
                                  `event_type_code` int(1) NOT NULL,
                                  `event_type_name` varchar(20) NOT NULL,
                                  PRIMARY KEY (`event_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type of event';

CREATE TABLE `tbl_language` (
                                `language_code` varchar(2) NOT NULL,
                                `language_name` varchar(100) NOT NULL,
                                PRIMARY KEY (`language_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of languages support for system';

CREATE TABLE `tbl_message_status` (
                                      `message_status_code` varchar(15) NOT NULL,
                                      `message_status_name` varchar(100) NOT NULL,
                                      PRIMARY KEY (`message_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Status of message when display on chatting frame';

CREATE TABLE `tbl_permission` (
                                  `permission_id` varchar(36) NOT NULL,
                                  `permission_name` varchar(100) NOT NULL,
                                  `permission_details` varchar(400) NOT NULL,
                                  `created_by` varchar(36) NOT NULL,
                                  `created_date` datetime NOT NULL,
                                  `updated_by` varchar(36) DEFAULT NULL,
                                  `updated_date` datetime DEFAULT NULL,
                                  `deleted_by` varchar(36) DEFAULT NULL,
                                  `deleted_date` datetime DEFAULT NULL,
                                  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='List of permissions for sercurity';

CREATE TABLE `tbl_role` (
                            `role_code` varchar(36) NOT NULL,
                            `role_name` varchar(100) NOT NULL,
                            PRIMARY KEY (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Define roles of system';

CREATE TABLE `tbl_schedule_repeat_type` (
                                            `schedule_repeat_code` int(1) NOT NULL,
                                            `schedule_repeat_name` varchar(100) NOT NULL,
                                            PRIMARY KEY (`schedule_repeat_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type repeat of schedule';

CREATE TABLE `tbl_schedule_status` (
                                       `schedule_status_code` int(1) NOT NULL,
                                       `schedule_status_name` varchar(20) NOT NULL,
                                       PRIMARY KEY (`schedule_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type of event status';

CREATE TABLE `tbl_todo_status` (
                                   `todo_status_code` int(1) NOT NULL,
                                   `todo_status_name` varchar(20) NOT NULL,
                                   PRIMARY KEY (`todo_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type of todo status';

CREATE TABLE `tbl_department` (
                                  `department_id` int(11) NOT NULL,
                                  `department_code` varchar(15) NOT NULL,
                                  `department_name` varchar(200) NOT NULL,
                                  `created_by` varchar(36) NOT NULL,
                                  `created_date` datetime NOT NULL,
                                  `updated_by` varchar(36) DEFAULT NULL,
                                  `updated_date` datetime DEFAULT NULL,
                                  `deleted_by` varchar(36) DEFAULT NULL,
                                  `deleted_date` datetime DEFAULT NULL,
                                  PRIMARY KEY (`department_id`),
                                  UNIQUE KEY `TBL_DEPARTMENT_department_code_uindex` (`department_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Department of company';

CREATE TABLE `tbl_chatting` (
                                `chatting_id` varchar(36) NOT NULL,
                                `user_id` varchar(36) NOT NULL,
                                `group_name` varchar(100) DEFAULT NULL,
                                PRIMARY KEY (`chatting_id`),
                                KEY `tbl_chatting_user_id_fk` (`user_id`),
                                CONSTRAINT `tbl_chatting_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Chat information';

CREATE TABLE `tbl_event` (
                             `event_id` varchar(36) NOT NULL,
                             `event_name` varchar(200) NOT NULL,
                             `event_status` int(1) NOT NULL,
                             `event_type` int(1) NOT NULL,
                             `author` int(11) NOT NULL,
                             `start_datetime` int(11) NOT NULL,
                             `end_datetime` int(11) NOT NULL,
                             `description` text NOT NULL,
                             `event_decision_status` int(1) NOT NULL,
                             `num_of_participant` int(5) NOT NULL,
                             `cancel_reason` varchar(400) DEFAULT NULL,
                             `created_by` varchar(36) NOT NULL,
                             `created_date` datetime NOT NULL,
                             `updated_by` varchar(36) DEFAULT NULL,
                             `updated_date` datetime DEFAULT NULL,
                             `deleted_by` varchar(36) DEFAULT NULL,
                             `deleted_date` datetime DEFAULT NULL,
                             PRIMARY KEY (`event_id`),
                             KEY `tbl_event_decision_status_code_fk` (`event_decision_status`),
                             KEY `tbl_event_tbl_event_status_event_status_code_fk` (`event_status`),
                             KEY `tbl_event_tbl_event_type_event_type_code_fk` (`event_type`),
                             CONSTRAINT `tbl_event_decision_status_code_fk` FOREIGN KEY (`event_decision_status`) REFERENCES `tbl_event_decision_status` (`event_decision_status_code`),
                             CONSTRAINT `tbl_event_tbl_event_status_event_status_code_fk` FOREIGN KEY (`event_status`) REFERENCES `tbl_event_status` (`event_status_code`),
                             CONSTRAINT `tbl_event_tbl_event_type_event_type_code_fk` FOREIGN KEY (`event_type`) REFERENCES `tbl_event_type` (`event_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Event information';

CREATE TABLE `tbl_message` (
                               `message_id` varchar(36) NOT NULL,
                               `chatting_id` varchar(36) NOT NULL,
                               `user_id` varchar(36) NOT NULL,
                               `message` text NOT NULL,
                               `message_status` varchar(15) NOT NULL,
                               `created_date` datetime NOT NULL,
                               PRIMARY KEY (`message_id`),
                               KEY `tbl_message_message_status__fk` (`message_status`),
                               KEY `tbl_message_tbl_chatting_chatting_id_fk` (`chatting_id`),
                               CONSTRAINT `tbl_message_message_status__fk` FOREIGN KEY (`message_status`) REFERENCES `tbl_message_status` (`message_status_code`),
                               CONSTRAINT `tbl_message_tbl_chatting_chatting_id_fk` FOREIGN KEY (`chatting_id`) REFERENCES `tbl_chatting` (`chatting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Chat message';

CREATE TABLE `tbl_role_permission` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `role_code` varchar(36) NOT NULL,
                                       `permission_id` varchar(36) NOT NULL,
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `TBL_ROLE_PERMISSION_permission_id_uindex` (`permission_id`),
                                       UNIQUE KEY `TBL_ROLE_PERMISSION_role_code_uindex` (`role_code`),
                                       CONSTRAINT `tbl_role_permission_permission_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `tbl_permission` (`permission_id`),
                                       CONSTRAINT `tbl_role_permission_role_code_fk` FOREIGN KEY (`role_code`) REFERENCES `tbl_role` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Defining permissions base on the role';

CREATE TABLE `tbl_schedule` (
                                `schedule_id` varchar(36) NOT NULL,
                                `user_id` varchar(36) NOT NULL,
                                `event_id` varchar(36) DEFAULT NULL,
                                `schedule_name` varchar(200) NOT NULL,
                                `schedule_repeat` int(1) NOT NULL,
                                `schedule_status` int(1) NOT NULL,
                                `description` text,
                                `display_type` tinyint(1) NOT NULL,
                                `start_datetime` datetime DEFAULT NULL,
                                `end_datetime` datetime DEFAULT NULL,
                                `created_by` varchar(36) NOT NULL,
                                `created_date` datetime NOT NULL,
                                `updated_by` varchar(36) DEFAULT NULL,
                                `updated_date` datetime DEFAULT NULL,
                                `deleted_by` varchar(36) DEFAULT NULL,
                                `deleted_date` datetime DEFAULT NULL,
                                PRIMARY KEY (`schedule_id`),
                                KEY `tbl_schedule_tbl_schedule_repeat_type_schedule_repeat_code_fk` (`schedule_repeat`),
                                KEY `tbl_schedule_tbl_user_user_id_fk` (`user_id`),
                                KEY `tbl_schedule_tbl_event_event_id_fk` (`event_id`),
                                KEY `tbl_schedule_tbl_schedule_status_schedule_status_code_fk` (`schedule_status`),
                                CONSTRAINT `tbl_schedule_tbl_event_event_id_fk` FOREIGN KEY (`event_id`) REFERENCES `tbl_event` (`event_id`) ON DELETE SET NULL ON UPDATE SET NULL,
                                CONSTRAINT `tbl_schedule_tbl_schedule_repeat_type_schedule_repeat_code_fk` FOREIGN KEY (`schedule_repeat`) REFERENCES `tbl_schedule_repeat_type` (`schedule_repeat_code`),
                                CONSTRAINT `tbl_schedule_tbl_schedule_status_schedule_status_code_fk` FOREIGN KEY (`schedule_status`) REFERENCES `tbl_schedule_status` (`schedule_status_code`),
                                CONSTRAINT `tbl_schedule_tbl_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Schedule information';

CREATE TABLE `tbl_team` (
                            `team_id` varchar(36) NOT NULL,
                            `team_code` varchar(15) NOT NULL,
                            `team_name` varchar(200) NOT NULL,
                            `department_code` varchar(15) NOT NULL,
                            `created_by` varchar(36) NOT NULL,
                            `created_date` datetime NOT NULL,
                            `updated_by` varchar(36) DEFAULT NULL,
                            `updated_date` datetime DEFAULT NULL,
                            `deleted_by` varchar(36) DEFAULT NULL,
                            `deleted_date` datetime DEFAULT NULL,
                            PRIMARY KEY (`team_id`),
                            UNIQUE KEY `tbl_team_team_code_uindex` (`team_code`),
                            KEY `tbl_team_department_code_fk` (`department_code`),
                            CONSTRAINT `tbl_team_department_code_fk` FOREIGN KEY (`department_code`) REFERENCES `tbl_department` (`department_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Team base on department information';

CREATE TABLE `tbl_user_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `role_code` varchar(36) NOT NULL,
                                 `user_id` varchar(36) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `TBL_USER_ROLE_role_code_uindex` (`role_code`),
                                 UNIQUE KEY `TBL_USER_ROLE_user_id_uindex` (`user_id`),
                                 CONSTRAINT `tbl_user_role___fk` FOREIGN KEY (`role_code`) REFERENCES `tbl_role` (`role_code`),
                                 CONSTRAINT `tbl_user_role__user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='User combination with roles in the system';

CREATE TABLE `tbl_user_setting` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `user_id` varchar(36) NOT NULL,
                                    `language_code` varchar(2) NOT NULL,
                                    `country_code` varchar(2) NOT NULL,
                                    `mobile` varchar(15) DEFAULT NULL,
                                    `address` varchar(400) DEFAULT NULL,
                                    `avatar` blob,
                                    `team_id` varchar(36) NOT NULL,
                                    `created_by` varchar(36) NOT NULL,
                                    `created_date` datetime NOT NULL,
                                    `updated_by` varchar(36) DEFAULT NULL,
                                    `updated_date` datetime DEFAULT NULL,
                                    `active` tinyint(1) NOT NULL,
                                    `deleted_by` varchar(36) DEFAULT NULL,
                                    `deleted_date` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `tbl_user_setting_user_id_uindex` (`user_id`),
                                    KEY `tbl_user_setting_team_id_fk` (`team_id`),
                                    KEY `tbl_user_setting_tbl_language_language_code_fk` (`language_code`),
                                    KEY `tbl_user_setting_tbl_country_country_code_fk` (`country_code`),
                                    CONSTRAINT `tbl_user_setting_tbl_country_country_code_fk` FOREIGN KEY (`country_code`) REFERENCES `tbl_country` (`country_code`),
                                    CONSTRAINT `tbl_user_setting_tbl_language_language_code_fk` FOREIGN KEY (`language_code`) REFERENCES `tbl_language` (`language_code`),
                                    CONSTRAINT `tbl_user_setting_team_id_fk` FOREIGN KEY (`team_id`) REFERENCES `tbl_team` (`team_id`),
                                    CONSTRAINT `tbl_user_setting_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='More user information setting';

CREATE TABLE `tbl_todo` (
                            `todo_id` varchar(36) NOT NULL,
                            `user_id` varchar(36) NOT NULL,
                            `todo_name` varchar(200) NOT NULL,
                            `todo_status` int(1) NOT NULL,
                            `assignee` varchar(36) DEFAULT NULL,
                            `start_datetime` datetime NOT NULL,
                            `due_datetime` datetime NOT NULL,
                            `description` text NOT NULL,
                            `created_date` datetime NOT NULL,
                            `updated_by` varchar(36) DEFAULT NULL,
                            `updated_date` datetime DEFAULT NULL,
                            `created_by` varchar(36) NOT NULL,
                            `deleted_by` varchar(36) DEFAULT NULL,
                            `deleted_date` datetime DEFAULT NULL,
                            PRIMARY KEY (`todo_id`),
                            KEY `tbl_todo_user_id_fk` (`user_id`),
                            KEY `tbl_todo_tbl_todo_status_todo_status_code_fk` (`todo_status`),
                            CONSTRAINT `tbl_todo_tbl_todo_status_todo_status_code_fk` FOREIGN KEY (`todo_status`) REFERENCES `tbl_todo_status` (`todo_status_code`),
                            CONSTRAINT `tbl_todo_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Todo information';
