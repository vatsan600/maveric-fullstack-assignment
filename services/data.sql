INSERT INTO `maveric-bank`.account (`type`,customer_id,created_at,updated_at) VALUES
	 ('SAVINGS',12,'2024-01-27 00:00:00','2024-01-27 00:00:00'),
	 ('SAVINGS',12,'2024-01-27 12:36:12','2024-01-27 12:36:12');
INSERT INTO `maveric-bank`.`user` (first_name,middle_name,last_name,email,phone_number,address,date_of_birth,created_at,updated_at,password,`role`,gender) VALUES
	 ('Srivatsan',NULL,'Venkateswaran','vatsan600@gmail.com','1238921342','random address',NULL,NULL,NULL,'this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan601@gmail.com','1238921342','random address',NULL,NULL,NULL,'this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan602@gmail.com','1238921342','random address',NULL,NULL,NULL,'this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan603@gmail.com','1238921342','2mdupdated random address',NULL,'2024-01-26 00:00:00','2024-01-26 00:00:00','this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan60s@gmail.com','1238921342','2mdupdated random address',NULL,'2024-01-26 00:00:00','2024-01-26 00:00:00','this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan605@gmail.com','1238921342','random address',NULL,NULL,'2024-01-26 00:00:00','this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan606@gmail.com','1238921342','random address',NULL,'2024-01-26 00:00:00','2024-01-26 00:00:00','this is not a password',NULL,NULL),
	 ('Srivatsan',NULL,'Venkateswaran','vatsan607@gmail.com','1238921342','random address',NULL,'2024-01-27 00:00:00','2024-01-27 00:00:00','this is not a password',NULL,NULL);
INSERT INTO `maveric-bank`.balance (account_id,amount,currency,created_at,updated_at) VALUES
	 (6,500.34000,'EURO','2024-01-27 17:25:08','2024-01-27 17:25:08'),
	 (6,500.34000,'EURO','2024-01-27 17:31:34','2024-01-27 17:31:34'),
	 (6,50340.34000,'INR','2024-01-27 17:38:56','2024-01-27 17:38:56'),
	 (6,500.34000,'EURO','2024-01-27 17:31:37','2024-01-27 17:31:37'),
	 (6,500.34000,'EURO','2024-01-27 17:31:37','2024-01-27 17:31:37'),
	 (6,500.34000,'EURO','2024-01-27 17:31:38','2024-01-27 17:31:38'),
	 (6,500.34000,'EURO','2024-01-27 17:31:49','2024-01-27 17:31:49'),
	 (6,500.34000,'EURO','2024-01-27 17:31:50','2024-01-27 17:31:50'),
	 (6,500.34000,'EURO','2024-01-27 17:31:51','2024-01-27 17:31:51'),
	 (6,500.34000,'EURO','2024-01-27 17:31:51','2024-01-27 17:31:51');
INSERT INTO `maveric-bank`.balance (account_id,amount,currency,created_at,updated_at) VALUES
	 (6,500.34000,'EURO','2024-01-27 17:37:11','2024-01-27 17:37:11');
INSERT INTO `maveric-bank`.`transaction` (`type`,amount,account_id,created_at) VALUES
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:07'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:09'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:10'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:11'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:11'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:12'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:13'),
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:14'),
	 ('DEBIT',534500.34000,6,'2024-01-27 18:56:18'),
	 ('CREDIT',500.22113,6,'2024-01-27 18:49:24');
INSERT INTO `maveric-bank`.`transaction` (`type`,amount,account_id,created_at) VALUES
	 ('CREDIT',500.34000,6,'2024-01-27 18:49:30'),
	 ('DEBIT',500.34000,6,'2024-01-27 18:49:35'),
	 ('DEBIT',500.34000,6,'2024-01-27 18:49:36'),
	 ('DEBIT',500.34000,6,'2024-01-27 18:49:37');
