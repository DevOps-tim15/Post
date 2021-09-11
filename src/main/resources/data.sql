CREATE TABLE IF NOT EXISTS authorities
(
`id` INTEGER AUTO_INCREMENT,
`user_type` VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_t
(
`id` INTEGER AUTO_INCREMENT,
`first_name` VARCHAR(255),
`last_name` VARCHAR(255),
`email` VARCHAR(255),
`username` VARCHAR(255),
`password` VARCHAR(255),
`phone` VARCHAR(255),
`website_url` VARCHAR(255),
`sex` VARCHAR(255),
`birth_date` VARCHAR(255),
`biography` VARCHAR(255),
`verified` BIT(1),
`canBeTagged` BIT(1),
`isPrivate` BIT(1),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_authority
(
`user_id` INTEGER,
`authority_id` INTEGER,
FOREIGN KEY (user_id) REFERENCES user_t(id),
FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

CREATE TABLE IF NOT EXISTS verification_tokens
(
`id` INTEGER AUTO_INCREMENT,
`token` VARCHAR(255),
`user_id` INTEGER,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

CREATE TABLE IF NOT EXISTS post (
`id` INTEGER AUTO_INCREMENT,
`decscription` VARCHAR(255),
`picture` LONGBLOB,
`user_id` INTEGER,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

CREATE TABLE IF NOT EXISTS post_tagged_user
(
`post_id` INTEGER,
`user_id` INTEGER,
FOREIGN KEY (post_id) REFERENCES post(id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

CREATE TABLE IF NOT EXISTS post_liked_by
(
`post_id` INTEGER,
`user_id` INTEGER,
FOREIGN KEY (post_id) REFERENCES post(id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

CREATE TABLE IF NOT EXISTS post_disliked_by
(
`post_id` INTEGER,
`user_id` INTEGER,
FOREIGN KEY (post_id) REFERENCES post(id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

CREATE TABLE IF NOT EXISTS comment
(
`id` INTEGER AUTO_INCREMENT,
`text` VARCHAR(255),
`post_id` INTEGER,
`user_id` INTEGER,
FOREIGN KEY (post_id) REFERENCES post(id),
FOREIGN KEY (user_id) REFERENCES user_t(id)
);

INSERT INTO `authorities` (`user_type`) VALUES ('ROLE_REGISTERED_USER');
INSERT INTO `authorities` (`user_type`) VALUES ('ROLE_AGENT');
INSERT INTO `authorities` (`user_type`) VALUES ('ROLE_ADMIN');

INSERT INTO `user_t` (`first_name`, `last_name`, `email`, `username`, `password`, `phone`, `website_url`, `sex`, `birth_date`, `biography`, `verified`, `canBeTagged`, `isPrivate`) 
VALUES ('Jova', 'Jovic', 'jova.jovic@gmail.com', 'jova', '$2a$12$ix4Ep6eG2ajt5yjWpAlRHusH1srR8GXdh0FvrgRWnVv2hZVRWEhoC', '0601234567', 'somesite.com', 'male', '01.01.01.', 'bio', 1, 1, 0),
('Marko', 'Maric', 'marko@gmail.com', 'marko', '$2a$12$ix4Ep6eG2ajt5yjWpAlRHusH1srR8GXdh0FvrgRWnVv2hZVRWEhoC', '0601234567', 'somesite.com', 'male', '01.01.01.', 'bio', 1, 1, 0);

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1), (2,1);

INSERT INTO verification_tokens (`token`, `user_id`) VALUES ('joca-token', 1), ('marko-token',2);
INSERT INTO `post`(`decscription`, `picture`, `user_id`) VALUES ('description', RAWTOHEX('Test'), '1');
INSERT INTO `post_liked_by` (`post_id`,`user_id`) VALUES ('1', '1');


