INSERT INTO `tvj_internal_db`.`tbl_role` (`role_id`, `role_name`, `created_by`, `created_date`, `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1','ADMIN','Dexx','2020-01-01 00:00:00',NULL,NULL,NULL,NULL);

INSERT INTO `tvj_internal_db`.`tbl_user` (`user_id`, `role_id`, `username`, `password`, `first_name`, `last_name`, `email`, `active`, `login_fail_count`, `forgot_password_token`, `forgot_password_token_expired_date`, `created_by`, `created_date`, `updated_by`, `updated_date`, `deleted_by`, `deleted_date`)
VALUES ('1','1','admin','$2a$10$CxA/SQQbIogEd.Q/xkdjpu1VwOwxpCGrnVq7H3tFyqrcbu2SSRuli','AA','BBB','1@1.1',1,0,NULL,NULL,'Dexx','2020-01-01 00:00:00',NULL,NULL,NULL,NULL);
