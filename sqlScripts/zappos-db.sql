create database zappos;

use database zappos;

delimiter $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSubscriptions`(IN item_id BIGINT)
BEGIN
	DECLARE done bool;
	DECLARE batch_id BIGINT DEFAULT 0 ;
	DECLARE subscription_id BIGINT;
	DECLARE subscriptions cursor for 
			SELECT s.subscriptionId FROM subscriptions s where s.itemId = item_id;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = true;

	select batchId into batch_id from email_batch where status = 'INPROGRESS';
	SET done = false;
	OPEN subscriptions;
	REPEAT
		FETCH subscriptions INTO subscription_id;
		IF NOT done THEN
			INSERT INTO notification_log(batchId, subscriptionId, status) values(batch_id, subscription_id,0);
		END IF;
	UNTIL done END REPEAT;
	CLOSE subscriptions;
END$$


delimiter $$
CREATE TABLE `category` (
  `categoryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(75) NOT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `categoryId_UNIQUE` (`categoryId`),
  UNIQUE KEY `categoryName_UNIQUE` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8$$

delimiter $$
CREATE TABLE `email_batch` (
  `batchId` bigint(20) NOT NULL AUTO_INCREMENT,
  `lastUpdatedOn` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(45) NOT NULL DEFAULT 'INPROGRESS',
  PRIMARY KEY (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8$$

delimiter $$
CREATE TABLE `notification_log` (
  `batchId` bigint(20) NOT NULL,
  `logId` bigint(20) NOT NULL AUTO_INCREMENT,
  `subscriptionId` bigint(20) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8$$

delimiter $$
CREATE TABLE `product` (
  `itemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `styleId` bigint(20) DEFAULT NULL,
  `categoryId` bigint(20) NOT NULL,
  `originalPrice` float NOT NULL,
  `percentOff` int(11) NOT NULL DEFAULT '0',
  `price` float NOT NULL,
  `imageUrl` varchar(400) DEFAULT NULL,
  `productName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`itemId`),
  UNIQUE KEY `itemId_UNIQUE` (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8$$

CREATE
DEFINER=`root`@`localhost`
TRIGGER `zappos`.`notifySubscribers`
AFTER UPDATE ON `zappos`.`product`
FOR EACH ROW
BEGIN
	if ((old.percentOff < new.percentOff) and (new.percentOff >= 25)) then
       CALL addSubscriptions(old.`itemId`);
	end if;
END
$$

delimiter $$
CREATE TABLE `subscriptions` (
  `subscriptionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `itemId` bigint(20) NOT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`subscriptionId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8$$


delimiter $$
CREATE TABLE `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(145) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  UNIQUE KEY `userEmail_UNIQUE` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8$$