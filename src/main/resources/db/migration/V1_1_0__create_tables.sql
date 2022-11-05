CREATE TABLE IF NOT EXISTS `city` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `city_name` varchar(20) NOT NULL,
    `created_by` varchar(50),
    `created_on` timestamp NOT NULL,
    `updated_by` varchar(50),
    `updated_on` timestamp NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `theatre` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `theatre_name` varchar(20),
    `created_by` varchar(50),
    `created_on` timestamp NOT NULL,
    `updated_by` varchar(50),
    `updated_on` timestamp NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `city_theatre` (
    `city_id` INTEGER NOT NULL,
    `theatre_id` INTEGER NOT NULL,
    PRIMARY KEY(`city_id`, `theatre_id`),
    CONSTRAINT fk_city1 FOREIGN KEY(city_id)
    REFERENCES city(id),
    CONSTRAINT fk_theatre1 FOREIGN KEY(theatre_id)
    REFERENCES theatre(id)
);

CREATE TABLE IF NOT EXISTS `movie` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `movie_name` varchar(20) UNIQUE,
    `created_by` varchar(50),
    `created_on` timestamp,
    `updated_by` varchar(50),
    `updated_on` timestamp,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `auditorium` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `movie_id` INTEGER,
    `theatre_id` INTEGER,
    `auditorium_name` varchar(20),
    `created_by` varchar(50),
    `created_on` timestamp,
    `updated_by` varchar(50),
    `updated_on` timestamp,
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_movie` FOREIGN KEY(`movie_id`)
    REFERENCES `movie`(`id`),
    CONSTRAINT `fk_theatre2` FOREIGN KEY(`theatre_id`)
    REFERENCES `theatre`(`id`),
    CONSTRAINT uk_movie_theatre UNIQUE(`movie_id`, `theatre_id`)
);

CREATE TABLE IF NOT EXISTS `show` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `show_name` varchar(20) NOT NULL,
    `show_time` varchar(20) NOT NULL,
    `created_by` varchar(50),
    `created_on` timestamp,
    `updated_by` varchar(50),
    `updated_on` timestamp,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `user_name` varchar(20),
    `user_id` varchar(20),
    `user_email` varchar(50),
    `mobile_number` varchar(13),
    `created_by` varchar(50),
    `created_on` timestamp,
    `updated_by` varchar(50),
    `updated_on` timestamp,
    PRIMARY KEY(`id`),
    CONSTRAINT uk_user_name UNIQUE(`user_name`),
    CONSTRAINT uk_user_id UNIQUE(`user_id`)
);

CREATE TABLE IF NOT EXISTS `ticket` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `user_id` INTEGER,
    `ticket_status` varchar(20),
    `ticket_id` varchar(50),
    `ticket_amount` decimal(8, 2),
    `created_by` varchar(50),
    `created_on` timestamp NOT NULL,
    `updated_by` varchar(50),
    `updated_on` timestamp NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT fk_user FOREIGN KEY(`user_id`)
    REFERENCES `user`(`id`)
);

CREATE TABLE IF NOT EXISTS `seat` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `auditorium_id` INTEGER,
    `seat_name` varchar(20),
    `seat_type` varchar(20),
    `created_by` varchar(50),
    `created_on` timestamp NOT NULL,
    `updated_by` varchar(50),
    `updated_on` timestamp NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_auditorium1` FOREIGN KEY(`auditorium_id`)
    REFERENCES `auditorium`(`id`)
);

CREATE TABLE IF NOT EXISTS `book_show_seat` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `show_id` INTEGER,
    `seat_id` INTEGER,
    `ticket_id` INTEGER,
    `seat_status` varchar(20) NOT NULL,
    `seat_book_time` timestamp,
    `show_day` varchar(12) NOT NULL,
    `version` INTEGER,
    `created_by` varchar(50),
    `created_on` timestamp NOT NULL,
    `updated_by` varchar(50),
    `updated_on` timestamp NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT fk_show1 FOREIGN KEY(show_id)
    REFERENCES show(id),
    CONSTRAINT fk_seat1 FOREIGN KEY(seat_id)
    REFERENCES seat(id),
    CONSTRAINT fk_ticket1 FOREIGN KEY(ticket_id)
    REFERENCES ticket(id),
    CONSTRAINT uk_show_seat_date UNIQUE(`show_id`, `seat_id`, `show_day`)
);

CREATE TABLE IF NOT EXISTS `auditorium_show` (
    `auditorium_id` INTEGER NOT NULL,
    `show_id` INTEGER NOT NULL,
    PRIMARY KEY(`auditorium_id`, `show_id`),
    CONSTRAINT fk_auditorium2 FOREIGN KEY(auditorium_id)
    REFERENCES auditorium(id),
    CONSTRAINT fk_show2 FOREIGN KEY(show_id)
    REFERENCES show(id)
);