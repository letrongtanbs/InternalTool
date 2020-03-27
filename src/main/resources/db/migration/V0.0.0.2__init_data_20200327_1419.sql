INSERT INTO `tbl_user_status` (`status_id`, `status_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                           `deleted_by`, `deleted_date`)
VALUES ('1', 'Available', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'Away', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('3', 'Busy', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('4', 'Do not disturb', 'System', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);

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
       ('11', 'URL11', '/user/remove-avatar', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('12', 'URL12', '/title/list', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('13', 'URL13', '/member-management/search', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('14', 'URL14', '/user-status/list', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('15', 'URL15', '/user/save-last-logout', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('16', 'URL16', '/member-management/add-member', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('17', 'URL17', '/member-management/update-member', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('18', 'URL18', '/member-management/view-member', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('19', 'URL19', '/member-management/update-member-activate-status', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('20', 'URL20', '/member-management/delete-member', 'D', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_role` (`role_id`, `role_name`, `created_by`, `created_date`, `updated_by`, `updated_date`,
                        `deleted_by`, `deleted_date`)
VALUES ('1', 'ADMIN', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL),
       ('2', 'USER', 'Dexx', '2020-01-01 00:00:00', NULL, NULL, NULL, NULL);


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
       ('2', '11'),
       ('1', '12'),
       ('2', '12'),
       ('1', '13'),
       ('1', '14'),
       ('2', '14'),
       ('1', '15'),
       ('2', '15'),
       ('1', '16'),
       ('1', '17'),
       ('1', '18'),
       ('1', '19'),
       ('1', '20');


INSERT INTO `tbl_team` (`team_id`, `team_code`, `team_name`, `department_id`, `created_by`, `created_date`,
                        `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'TEAM001', 'Team 1', '1', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'TEAM002', 'Team 2', '2', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('3', 'TEAM003', 'Team 3', '2', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_title` (`title_id`, `title_code`, `title_name`, `created_by`, `created_date`,
                         `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1', 'DEV001', 'Developer level 1', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('2', 'DEV002', 'Developer level 2', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('3', 'DEV003', 'Developer level 3', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL),
       ('4', 'DEV004', 'Developer level 4', 'Dexx', '2020-02-06 10:10:10', NULL, NULL, NULL, NULL);


INSERT INTO `tbl_user_setting` (`user_setting_id`, `team_id`, `address`, `phone`, `country_id`, `language_id`, `status_id`, `avatar`, `created_by`, `created_date`, `updated_by`, `updated_date`, `deleted_by`, `deleted_date`) 
VALUES ('1','1','homie','1234567890','1','1','1',NULL,'Dexx','2020-02-06 10:10:10',NULL,NULL,NULL,NULL),
        ('2','1','homie','0987654321','1','1','1',NULL,'Dexx','2020-02-06 10:10:10',NULL,NULL,NULL,NULL),
        ('3','1','homie','121142345678','1','1','1',NULL,'Dexx','2020-02-06 10:10:10',NULL,NULL,NULL,NULL),
        ('51976349-da8e-49a4-adc0-7d5708906774',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:35:11',NULL,NULL,NULL,NULL),
        ('6420245d-17fa-47be-8694-eddd74ed57fd',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:42:28',NULL,NULL,NULL,NULL),
        ('6c04daea-d3e8-47bb-a5ba-c6c0f6c00f7b',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:34:13',NULL,NULL,NULL,NULL),
        ('ac6b57d8-7fcf-4edf-b78c-e1acd5b47840','1',NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:40:00','hunghx10','2020-03-26 18:08:24',NULL,NULL),
        ('b00bee54-84d3-49a4-90b7-9fa38842c93a',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:31:19',NULL,NULL,NULL,NULL),
        ('c12951c3-0e33-4bb8-832e-8f15170214cb',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:43:25',NULL,NULL,NULL,NULL),
        ('da6437e9-2274-453a-8a9b-09f5adf6b374',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:33:19',NULL,NULL,NULL,NULL),
        ('e664b4b0-a525-42bd-8c2d-9b47235daad0',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:46:41',NULL,NULL,NULL,NULL),
        ('ed70107a-eef6-40e6-9d9d-2fcbd1a34e6d',NULL,NULL,NULL,'1','1','1',NULL,'hunghx','2020-03-26 16:41:41',NULL,NULL,NULL,NULL);


INSERT INTO `tbl_user` (`user_id`, `user_setting_id`, `role_id`, `username`, `password`, `first_name`, `last_name`, `title_id`, `email`, `is_activated`, `is_first_time_login`, `login_fail_count`, `created_by`, `created_date`, `updated_by`, `updated_date`, `deleted_by`, `deleted_date`, `last_login`, `last_logout`) 
VALUES ('0dc389d9-f20c-4bdd-977e-447cb43b44f6','da6437e9-2274-453a-8a9b-09f5adf6b374','2','hunghx2','$2a$10$iZREDegvQqzix4zbHhyh4uGW2ubmbvqzzAXemdLFLx4rzdplMEFCW','Hung','Hoang','2','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:33:19',NULL,NULL,NULL,NULL,NULL,NULL),
        ('1','1','1','ngocdc','$2y$12$ipT2RRvG/HaRxeuaNRGm0eVfg0wdXUFot5CM4/IIstD8u.Qn4vkju','Dinh','Ngoc','1','ngocdc@tinhvan.com',1,1,0,'Dexx','2020-01-01 00:00:00',NULL,NULL,NULL,NULL,'2020-03-27 14:18:28',NULL),
        ('18470db3-8fdf-4b17-9fdf-702c09389abd','b00bee54-84d3-49a4-90b7-9fa38842c93a','2','hunghx1','$2a$10$GeZ1oiPhUTNah31VJWWSvuHQhShqXUcB6x8QEX.YXmzxHMcayxZMq','Hung','Hoang','1','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:31:19','hunghx','2020-03-26 18:20:33',NULL,NULL,NULL,NULL),
        ('2','2','1','hunghx','$2y$12$ipT2RRvG/HaRxeuaNRGm0eVfg0wdXUFot5CM4/IIstD8u.Qn4vkju','Hoang','Hung','2','hunghx@tinhvan.com',1,0,0,'Dexx','2020-01-01 00:00:00','hunghx','2020-03-26 17:56:09',NULL,NULL,'2020-03-27 12:31:22','2020-03-27 14:20:00'),
        ('3','3','2','tamdt','$2y$12$ipT2RRvG/HaRxeuaNRGm0eVfg0wdXUFot5CM4/IIstD8u.Qn4vkju','Do','Tam','3','tamdt@tinhvan.com',1,0,0,'Dexx','2020-01-01 00:00:00','ngocdc','2020-03-26 14:27:30',NULL,NULL,'2020-03-26 14:21:43','2020-03-26 15:25:00'),
        ('49967869-d485-4de0-9037-9e067f793f69','e664b4b0-a525-42bd-8c2d-9b47235daad0','2','hunghx20','$2a$10$jTGoAj580DGLt3whBCBlg.oqLusEGN1qyVMGADQ.6vO0hP7JvYe66','A','B','2','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:46:41',NULL,NULL,NULL,NULL,NULL,NULL),
        ('56c5062d-0438-4a5b-b44f-dc9e239ba6f4','51976349-da8e-49a4-adc0-7d5708906774','2','hunghx5','$2a$10$s1pqtrZCQh9Pbc19EX1BPOBWiUlWfJfCeVfs1tNSFRYDb1oYGQniu','H','B','1','hunghx@tinhvan.com',1,1,0,'hunghx','2020-03-26 16:35:11',NULL,NULL,NULL,NULL,NULL,NULL),
        ('a09be337-d292-495c-904b-519b14d9759d','6420245d-17fa-47be-8694-eddd74ed57fd','2','hunghx13','$2a$10$1TEF/ZbQUxhVYLS8Mil0AuBhkzHOeHfVamQgGvXX2KQgSERbW65Vm','A','B','4','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:42:28',NULL,NULL,NULL,NULL,NULL,NULL),
        ('ab7a00eb-8ddc-44a9-9ba6-2f4dfab454c0','c12951c3-0e33-4bb8-832e-8f15170214cb','2','hunghx14','$2a$10$bT1VbrT3s6mwe59w25dwDOeZbROkUxks22zVWMQUH3xcw.1RJyH1O','A','B','3','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:43:25',NULL,NULL,NULL,NULL,NULL,NULL),
        ('b0516d29-fbe2-4ade-bf37-e02374ea2dde','ac6b57d8-7fcf-4edf-b78c-e1acd5b47840','2','hunghx10','$2a$10$w5rio9FhBTTSC.KqD8ehJ.XxSR6i6shx0yMiWEOQxbKTsvSAGBXcq','AAAAA','B','4','hunghx@tinhvan.com',1,1,0,'hunghx','2020-03-26 16:40:00','hunghx','2020-03-26 17:35:10',NULL,NULL,'2020-03-26 18:02:53','2020-03-26 18:11:41'),
        ('c23d9ce7-12ce-4ed7-828e-5ec08b14a14d','ed70107a-eef6-40e6-9d9d-2fcbd1a34e6d','2','hunghx12','$2a$10$PnIk0U1RMly4J7MmODtogO7n0nGCQLIIWaGasrpC9BEWV/kncoht6','A','B','3','hunghx@tinhvan.com.vn',1,1,0,'hunghx','2020-03-26 16:41:41',NULL,NULL,NULL,NULL,NULL,NULL),
        ('f743b114-108f-4f03-9077-bd1c0173ce2e','6c04daea-d3e8-47bb-a5ba-c6c0f6c00f7b','2','hunghx3','$2a$10$oL8OVvjuE/pGYln7WViEeuK8l9QrDamqx390hVWhPww9OU3OXWp5y','HA','BC','3','hunghx@tinhvan.com',1,1,0,'hunghx','2020-03-26 16:34:13','hunghx','2020-03-26 17:33:32',NULL,NULL,NULL,NULL);

