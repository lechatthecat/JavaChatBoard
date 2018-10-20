CREATE TABLE roles (
	id BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	is_admin TINYINT(4) NOT NULL, 
	power_level int(11) unsigned NOT NULL,
	name VARCHAR(20) NOT NULL,
	is_deleted TINYINT(4) NOT NULL, 
	PRIMARY KEY (id)
);

INSERT INTO roles (is_admin, power_level, name)
VALUES (1, 1, "ROLE_ADMIN");

INSERT INTO roles (is_admin, power_level, name)
VALUES (0, 0, "ROLE_USER");

CREATE TABLE users (
	id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL,  
	`password` CHAR(128),
	role_id BIGINT(20) unsigned, 
	email TEXT,
	birth DATE,
	is_deleted TINYINT(4) NOT NULL, 
	updated DATETIME,
	created DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE user_images (
	id BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	user_id bigint(20) unsigned NOT NULL,	
	name VARCHAR(20) NOT NULL,
	`path` TEXT,
        detail TEXT,
	is_deleted TINYINT(4) NOT NULL, 
	updated DATETIME,
	created DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE boards (
	`id` BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
	user_id BIGINT(20) unsigned NOT NULL,
	name VARCHAR(20) NOT NULL,  
	detail text, 
	is_deleted TINYINT(4) NOT NULL, 
	updated DATETIME,
	created DATETIME,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);
