CREATE TABLE roles (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	`is_admin` TINYINT(4) NOT NULL, 
	`power_level` int(11) unsigned NOT NULL,
	`name` VARCHAR(20) NOT NULL,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	PRIMARY KEY (id)
);

INSERT INTO roles (is_admin, power_level, name)
VALUES (1, 1, "ROLE_ADMIN");

INSERT INTO roles (is_admin, power_level, name)
VALUES (0, 0, "ROLE_USER");

CREATE TABLE users (
	`id` bigint(20) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL UNIQUE,  
	`password` CHAR(128),
	`role_id` BIGINT(20) unsigned, 
	`email` VARCHAR(50) UNIQUE,
	`birth` DATE,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	`updated` DATETIME,
	`created` DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE user_images (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` bigint(20) unsigned NOT NULL,	
	`name` VARCHAR(20) NOT NULL,
	`path` TEXT,
    `detail` TEXT,
	`is_main` TINYINT(4) NOT NULL DEFAULT 0, 
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	`updated` DATETIME,
	`created` DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE users ADD FOREIGN KEY (user_image_id) REFERENCES user_images(id);
ALTER TABLE users ADD FOREIGN KEY (user_main_image_id) REFERENCES user_images(id);

CREATE TABLE boards (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` BIGINT(20) unsigned NOT NULL,
	`name` VARCHAR(20) NOT NULL,  
	`detail` text, 
	`is_private` TINYINT(4) NOT NULL DEFAULT 0,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	`updated` DATETIME,
	`created` DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE board_users (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` BIGINT(20) unsigned NOT NULL,
	`board_id` BIGINT(20) unsigned NOT NULL,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	`updated` DATETIME,
	`created` DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (board_id) REFERENCES boards(id)
);

CREATE TABLE board_responses (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	`response` TEXT,
	`type` ENUM('CHAT','JOIN', 'LEAVE'),
	`board_id` BIGINT(20) unsigned NOT NULL,
	`user_id` BIGINT(20) unsigned NOT NULL,
	`is_seen` TINYINT(4) NOT NULL DEFAULT 0,
	`is_deleted` TINYINT(4) NOT NULL DEFAULT 0, 
	`updated` DATETIME,
	`created` DATETIME,
	PRIMARY KEY (id, board_id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (board_id) REFERENCES boards(id)
);

-- For testing
INSERT INTO users (name, password, role_id, email, birth, updated, created)
VALUES ('test', '$2a$10$Fonnn4/zL3wGBvcyYbhIu.bz6q8CKuSyCQyIYqrpNdeVfO1J.Gn0S', 
2, 'test@test.com', '2018-10-10', NOW(), NOW());
INSERT INTO users (name, password, role_id, email, birth, updated, created)
VALUES ('test2', '$2a$10$Fonnn4/zL3wGBvcyYbhIu.bz6q8CKuSyCQyIYqrpNdeVfO1J.Gn0S', 
2, 'test2@test.com', '2018-10-10', NOW(), NOW());