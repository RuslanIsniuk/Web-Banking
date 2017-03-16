-- MySQL Script generated by MySQL Workbench
-- 02/19/17 13:42:13
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema MySQLBankDemo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MySQLBankDemo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MySQLBankDemo` DEFAULT CHARACTER SET utf8 ;
USE `MySQLBankDemo` ;

-- -----------------------------------------------------
-- Table `MySQLBankDemo`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MySQLBankDemo`.`clients` (
  `client_id` INT NOT NULL AUTO_INCREMENT,
  `client_login` VARCHAR(25) NOT NULL,
  `client_password` VARCHAR(25) NOT NULL,
  `client_full_name` VARCHAR(45) NOT NULL,
  `admin_flag` TINYINT(1) NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE INDEX `clients_id_UNIQUE` (`client_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MySQLBankDemo`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MySQLBankDemo`.`accounts` (
  `client_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `balance` DECIMAL(12,3) NULL,
  `status` VARCHAR(20) NOT NULL,
  `data_open` DATE NOT NULL,
  PRIMARY KEY (`account_id`, `client_id`),
  UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC),
  CONSTRAINT `fk_accounts_clients1`
    FOREIGN KEY (`client_id`)
    REFERENCES `MySQLBankDemo`.`clients` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MySQLBankDemo`.`cards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MySQLBankDemo`.`cards` (
  `account_id` INT NOT NULL,
  `card_id` BIGINT(16) NOT NULL,
  `card_pin` VARCHAR(6) NOT NULL,
  `card_valid_data` DATE NOT NULL,
  `card_status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`card_id`, `account_id`),
  UNIQUE INDEX `card_id_UNIQUE` (`card_id` ASC),
  CONSTRAINT `fk_cards_accounts1`
    FOREIGN KEY (`account_id`)
    REFERENCES `MySQLBankDemo`.`accounts` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MySQLBankDemo`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MySQLBankDemo`.`payment` (
  `payments_id` INT NOT NULL AUTO_INCREMENT,
  `card_id` BIGINT(16) NOT NULL,
  `payments_type` VARCHAR(45) NOT NULL,
  `payments_date` DATE NOT NULL,
  `payments_amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`payments_id`, `card_id`),
  UNIQUE INDEX `payments_id_UNIQUE` (`payments_id` ASC),
  INDEX `fk_payments_cards1_idx` (`card_id` ASC),
  CONSTRAINT `fk_payments_cards1`
    FOREIGN KEY (`card_id`)
    REFERENCES `MySQLBankDemo`.`cards` (`card_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `MySQLBankDemo`.`clients`
-- -----------------------------------------------------
START TRANSACTION;
USE `MySQLBankDemo`;
INSERT INTO `MySQLBankDemo`.`clients` (`client_id`, `client_login`, `client_password`, `client_full_name`, `admin_flag`) VALUES (1, 'Rodriegez_P', 'warface', 'Paulus Rodriegez', 0);
INSERT INTO `MySQLBankDemo`.`clients` (`client_id`, `client_login`, `client_password`, `client_full_name`, `admin_flag`) VALUES (2, 'Jenifer_M', 'dev232', 'Molly Jenifer', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MySQLBankDemo`.`accounts`
-- -----------------------------------------------------
START TRANSACTION;
USE `MySQLBankDemo`;
INSERT INTO `MySQLBankDemo`.`accounts` (`client_id`, `account_id`, `balance`, `status`, `data_open`) VALUES (1, 2647584, 12000, 'active', '2018-10-15');
INSERT INTO `MySQLBankDemo`.`accounts` (`client_id`, `account_id`, `balance`, `status`, `data_open`) VALUES (2, 3462153, 600, 'active', '2015-8-5');
INSERT INTO `MySQLBankDemo`.`accounts` (`client_id`, `account_id`, `balance`, `status`, `data_open`) VALUES (1, 5154625, 900, 'active', '2016-9-12');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MySQLBankDemo`.`cards`
-- -----------------------------------------------------
START TRANSACTION;
USE `MySQLBankDemo`;
INSERT INTO `MySQLBankDemo`.`cards` (`account_id`, `card_id`, `card_pin`, `card_valid_data`, `card_status`) VALUES (2647584, 5147342368252463, '1609', '2019-10-23', 'active');
INSERT INTO `MySQLBankDemo`.`cards` (`account_id`, `card_id`, `card_pin`, `card_valid_data`, `card_status`) VALUES (5154625, 6541351458581111, '2451', '2018-7-13', 'active');
INSERT INTO `MySQLBankDemo`.`cards` (`account_id`, `card_id`, `card_pin`, `card_valid_data`, `card_status`) VALUES (3462153, 2341483627541213, '1607', '2016-08-12', 'active');

COMMIT;

