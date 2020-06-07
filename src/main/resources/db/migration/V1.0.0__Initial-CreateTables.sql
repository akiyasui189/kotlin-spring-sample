CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `status` VARCHAR(16) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `email_verified_at` DATETIME NULL,
  `created_at` DATETIME NOT NULL,
  `created_by` VARCHAR(32) NOT NULL,
  `updated_at` DATETIME NOT NULL,
  `updated_by` VARCHAR(32) NOT NULL,
  `version` INT UNSIGNED NOT NULL,
  `deleted` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `unique_index_username` (`username` ASC),
  UNIQUE INDEX `unique_index_email` (`email` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `users` AUTO_INCREMENT = 10000001;
