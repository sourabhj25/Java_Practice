ALTER TABLE `client`.`client` 
ADD COLUMN `is_expiration_enabled` TINYINT(1) NULL DEFAULT '0' AFTER `created_dt`,
ADD COLUMN `expiration_timeout`  BIGINT(20) NULL AFTER `is_expiration_enabled`,
ADD COLUMN `timezone` VARCHAR(45) NULL AFTER `timeout`;

ALTER TABLE `client`.`client` 
CHANGE COLUMN `expiration_timeout` `expiration_timeout` BIGINT(20) NULL DEFAULT 0 ;

