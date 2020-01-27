CREATE SCHEMA IF NOT EXISTS `tvj_internal_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `tvj_internal_db`;


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_user`
(
    `user_id`                            VARCHAR(36)  NOT NULL,
    `username`                           VARCHAR(25)  NULL DEFAULT NULL,
    `password`                           VARCHAR(100) NULL DEFAULT NULL,
    `first_name`                         VARCHAR(100) NULL DEFAULT NULL,
    `last_name`                          VARCHAR(100) NULL DEFAULT NULL,
    `email`                              VARCHAR(75)  NOT NULL,
    `active`                             TINYINT(1)   NOT NULL,
    `login_fail_count`                   TINYINT(1)   NOT NULL,
    `forgot_password_token`              VARCHAR(200) NULL DEFAULT NULL,
    `forgot_password_token_expired_date` DATETIME     NULL DEFAULT NULL,
    `created_by`                         VARCHAR(36)  NOT NULL,
    `created_date`                       DATETIME     NOT NULL,
    `updated_by`                         VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`                       DATETIME     NULL DEFAULT NULL,
    `deleted_by`                         VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`                       DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'User information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_chatting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_chatting`
(
    `chatting_id`  VARCHAR(36)  NOT NULL,
    `group_name`   VARCHAR(100) NULL DEFAULT NULL,
    `deleted_date` DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`chatting_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Chat information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_chatting_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_chatting_user`
(
    `chatting_user_id` VARCHAR(36) NOT NULL,
    `chatting_id`      VARCHAR(36) NOT NULL,
    `user_id`          VARCHAR(36) NOT NULL,
    `mute_flag`        TINYINT     NOT NULL,
    PRIMARY KEY (`chatting_user_id`),
    UNIQUE INDEX `tbl_chatting_user_chatting_id_user_id_uindex` (`chatting_id` ASC, `user_id` ASC) VISIBLE,
    CONSTRAINT `tbl_chatting_user_chatting_id_fk`
        FOREIGN KEY (`chatting_id`)
            REFERENCES `tvj_internal_db`.`tbl_chatting` (`chatting_id`),
    CONSTRAINT `tbl_chatting_user_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `tvj_internal_db`.`tbl_user` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Chat group information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_message`
(
    `message_id`       VARCHAR(36) NOT NULL,
    `chatting_user_id` VARCHAR(36) NOT NULL,
    `message`          TEXT        NOT NULL,
    `message_status`   INT(1)      NOT NULL,
    `created_date`     DATETIME    NOT NULL,
    `deleted_date`     DATETIME    NULL DEFAULT NULL,
    PRIMARY KEY (`message_id`),
    CONSTRAINT `tbl_message_chatting_user_id_fk`
        FOREIGN KEY (`chatting_user_id`)
            REFERENCES `tvj_internal_db`.`tbl_chatting_user` (`chatting_user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Chat message';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_chatting_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_chatting_file`
(
    `chatting_file_id` VARCHAR(36)  NOT NULL,
    `chatting_user_id` VARCHAR(36)  NOT NULL,
    `file_link`        VARCHAR(200) NOT NULL,
    `description`      VARCHAR(400) NULL DEFAULT NULL,
    PRIMARY KEY (`chatting_file_id`),
    CONSTRAINT `tbl_chatting_file_chatting_user_id_fk`
        FOREIGN KEY (`chatting_user_id`)
            REFERENCES `tvj_internal_db`.`tbl_chatting_user` (`chatting_user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Chat file';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_department`
(
    `department_id`   INT(11)      NOT NULL,
    `department_code` VARCHAR(15)  NULL DEFAULT NULL,
    `department_name` VARCHAR(200) NULL DEFAULT NULL,
    `created_by`      VARCHAR(36)  NOT NULL,
    `created_date`    DATETIME     NOT NULL,
    `updated_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`    DATETIME     NULL DEFAULT NULL,
    `deleted_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`    DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`department_id`),
    UNIQUE INDEX `TBL_DEPARTMENT_department_code_uindex` (`department_code` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Department of company';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_event`
(
    `event_id`              VARCHAR(36)  NOT NULL,
    `event_name`            VARCHAR(200) NULL DEFAULT NULL,
    `event_status`          INT(1)       NOT NULL,
    `event_type`            INT(1)       NOT NULL,
    `author`                INT(11)      NOT NULL,
    `start_datetime`        INT(11)      NOT NULL,
    `end_datetime`          INT(11)      NOT NULL,
    `description`           TEXT         NULL DEFAULT NULL,
    `event_decision_status` INT(1)       NOT NULL,
    `num_of_participant`    INT(5)       NOT NULL,
    `cancel_reason`         VARCHAR(400) NULL DEFAULT NULL,
    `created_by`            VARCHAR(36)  NOT NULL,
    `created_date`          DATETIME     NOT NULL,
    `updated_by`            VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`          DATETIME     NULL DEFAULT NULL,
    `deleted_by`            VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`          DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`event_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Event information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_permission`
(
    `permission_id`      VARCHAR(36)  NOT NULL,
    `permission_name`    VARCHAR(100) NULL DEFAULT NULL,
    `permission_details` VARCHAR(400) NULL DEFAULT NULL,
    `created_by`         VARCHAR(36)  NOT NULL,
    `created_date`       DATETIME     NOT NULL,
    `updated_by`         VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`       DATETIME     NULL DEFAULT NULL,
    `deleted_by`         VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`       DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`permission_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'List of permissions for security';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_role`
(
    `role_code` VARCHAR(36)  NOT NULL,
    `role_name` VARCHAR(100) NULL DEFAULT NULL,
    PRIMARY KEY (`role_code`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Define roles of system';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_role_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_role_permission`
(
    `id`            INT(11)     NOT NULL AUTO_INCREMENT,
    `role_code`     VARCHAR(36) NOT NULL,
    `permission_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `TBL_ROLE_PERMISSION_permission_id_uindex` (`permission_id` ASC) VISIBLE,
    UNIQUE INDEX `TBL_ROLE_PERMISSION_role_code_uindex` (`role_code` ASC) VISIBLE,
    CONSTRAINT `tbl_role_permission_permission_id_fk`
        FOREIGN KEY (`permission_id`)
            REFERENCES `tvj_internal_db`.`tbl_permission` (`permission_id`),
    CONSTRAINT `tbl_role_permission_role_code_fk`
        FOREIGN KEY (`role_code`)
            REFERENCES `tvj_internal_db`.`tbl_role` (`role_code`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Defining permissions base on the role';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_schedule`
(
    `schedule_id`     VARCHAR(36)  NOT NULL,
    `user_id`         VARCHAR(36)  NOT NULL,
    `event_id`        VARCHAR(36)  NULL DEFAULT NULL,
    `schedule_name`   VARCHAR(200) NULL DEFAULT NULL,
    `schedule_repeat` INT(1)       NOT NULL,
    `schedule_status` INT(1)       NOT NULL,
    `description`     TEXT         NULL DEFAULT NULL,
    `display_type`    TINYINT(1)   NOT NULL,
    `start_datetime`  DATETIME     NULL DEFAULT NULL,
    `end_datetime`    DATETIME     NULL DEFAULT NULL,
    `created_by`      VARCHAR(36)  NOT NULL,
    `created_date`    DATETIME     NOT NULL,
    `updated_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`    DATETIME     NULL DEFAULT NULL,
    `deleted_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`    DATETIME     NULL DEFAULT NULL,
    `schedule_type`   INT(1)       NULL DEFAULT NULL,
    `assignee`        TEXT         NULL DEFAULT NULL,
    PRIMARY KEY (`schedule_id`),
    INDEX `tbl_schedule_tbl_user_user_id_fk` (`user_id` ASC) VISIBLE,
    INDEX `tbl_schedule_tbl_event_event_id_fk` (`event_id` ASC) VISIBLE,
    CONSTRAINT `tbl_schedule_tbl_event_event_id_fk`
        FOREIGN KEY (`event_id`)
            REFERENCES `tvj_internal_db`.`tbl_event` (`event_id`)
            ON DELETE SET NULL
            ON UPDATE SET NULL,
    CONSTRAINT `tbl_schedule_tbl_user_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `tvj_internal_db`.`tbl_user` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Schedule information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_team`
(
    `team_id`         VARCHAR(36)  NOT NULL,
    `team_code`       VARCHAR(15)  NOT NULL,
    `team_name`       VARCHAR(200) NULL DEFAULT NULL,
    `department_code` VARCHAR(15)  NULL DEFAULT NULL,
    `created_by`      VARCHAR(36)  NOT NULL,
    `created_date`    DATETIME     NOT NULL,
    `updated_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`    DATETIME     NULL DEFAULT NULL,
    `deleted_by`      VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`    DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`team_id`),
    UNIQUE INDEX `tbl_team_team_code_uindex` (`team_code` ASC) VISIBLE,
    INDEX `tbl_team_department_code_fk` (`department_code` ASC) VISIBLE,
    CONSTRAINT `tbl_team_department_code_fk`
        FOREIGN KEY (`department_code`)
            REFERENCES `tvj_internal_db`.`tbl_department` (`department_code`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Team base on department information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_todo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_todo`
(
    `todo_id`        VARCHAR(36)  NOT NULL,
    `user_id`        VARCHAR(36)  NOT NULL,
    `todo_name`      VARCHAR(200) NULL DEFAULT NULL,
    `todo_status`    INT(1)       NOT NULL,
    `assignee`       VARCHAR(200) NULL DEFAULT NULL,
    `start_datetime` DATETIME     NOT NULL,
    `due_datetime`   DATETIME     NOT NULL,
    `description`    TEXT         NULL DEFAULT NULL,
    `created_date`   DATETIME     NOT NULL,
    `updated_by`     VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`   DATETIME     NULL DEFAULT NULL,
    `created_by`     VARCHAR(36)  NOT NULL,
    `deleted_by`     VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`   DATETIME     NULL DEFAULT NULL,
    `priority_type`  INT(1)       NULL DEFAULT NULL,
    PRIMARY KEY (`todo_id`),
    INDEX `tbl_todo_user_id_fk` (`user_id` ASC) VISIBLE,
    CONSTRAINT `tbl_todo_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `tvj_internal_db`.`tbl_user` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'Todo information';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_user_role`
(
    `id`        INT(11)     NOT NULL AUTO_INCREMENT,
    `role_code` VARCHAR(36) NOT NULL,
    `user_id`   VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `TBL_USER_ROLE_role_code_uindex` (`role_code` ASC) VISIBLE,
    UNIQUE INDEX `TBL_USER_ROLE_user_id_uindex` (`user_id` ASC) VISIBLE,
    CONSTRAINT `tbl_user_role___fk`
        FOREIGN KEY (`role_code`)
            REFERENCES `tvj_internal_db`.`tbl_role` (`role_code`),
    CONSTRAINT `tbl_user_role__user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `tvj_internal_db`.`tbl_user` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'User combination with roles in the system';


-- -----------------------------------------------------
-- Table `tvj_internal_db`.`tbl_user_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tvj_internal_db`.`tbl_user_setting`
(
    `id`            INT(11)      NOT NULL AUTO_INCREMENT,
    `user_id`       VARCHAR(36)  NOT NULL,
    `language_code` VARCHAR(2)   NOT NULL,
    `country_code`  VARCHAR(2)   NOT NULL,
    `phone`         VARCHAR(15)  NULL DEFAULT NULL,
    `address`       VARCHAR(400) NULL DEFAULT NULL,
    `avatar`        VARCHAR(400) NULL DEFAULT NULL,
    `team_id`       VARCHAR(36)  NOT NULL,
    `title`         VARCHAR(36)  NULL DEFAULT NULL,
    `created_by`    VARCHAR(36)  NOT NULL,
    `created_date`  DATETIME     NOT NULL,
    `updated_by`    VARCHAR(36)  NULL DEFAULT NULL,
    `updated_date`  DATETIME     NULL DEFAULT NULL,
    `status`        TINYINT(1)   NOT NULL,
    `deleted_by`    VARCHAR(36)  NULL DEFAULT NULL,
    `deleted_date`  DATETIME     NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `tbl_user_setting_user_id_uindex` (`user_id` ASC) VISIBLE,
    INDEX `tbl_user_setting_team_id_fk` (`team_id` ASC) VISIBLE,
    CONSTRAINT `tbl_user_setting_team_id_fk`
        FOREIGN KEY (`team_id`)
            REFERENCES `tvj_internal_db`.`tbl_team` (`team_id`),
    CONSTRAINT `tbl_user_setting_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `tvj_internal_db`.`tbl_user` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COMMENT = 'More user information setting';
