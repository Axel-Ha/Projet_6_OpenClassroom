drop table if exists comments;
drop table if exists posts;
drop table if exists subscriptions;
drop table if exists topics;
drop table if exists users;

CREATE TABLE `topics` (
    `topic_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50),
    `description` VARCHAR(2000)
);

CREATE TABLE `posts` (
    `post_id` INT PRIMARY KEY AUTO_INCREMENT,
    `topic_id` INT,
    `title` VARCHAR(50),
    `author_id` INT,
    `content` VARCHAR(10000),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `comments` (
    `comment_id` INT PRIMARY KEY AUTO_INCREMENT,
    `author_id` INT,
    `post_id` INT,
    `message` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `users` (
    `user_id` INT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(40),
    `email` VARCHAR(100),
    `password` VARCHAR(255),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `subscriptions` (
    `user_id` INT,
    `topic_id` INT
);

INSERT INTO `TOPICS` (`name`, `description`) 
VALUES 
('Java', 'Tout ce que touche autours de java.'),
('Angular', 'Tout ce que touche autours de Angular.'),
('JavaScript', 'Tout ce que touche autours de JavaScript.');
 
ALTER TABLE `POSTS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`topic_id`);
ALTER TABLE `POSTS` ADD FOREIGN KEY (`author_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`author_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`post_id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`topic_id`);