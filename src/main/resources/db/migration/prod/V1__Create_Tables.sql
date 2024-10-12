CREATE TABLE `sec_seq_generator`
(
	`next_val` bigint DEFAULT NULL
);

CREATE TABLE `employee`
(
	`id`       bigint      NOT NULL,
	`active`   bit(1)      NOT NULL,
	`password` varchar(60) NOT NULL,
	`username` varchar(50) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `UK_im8flsuftl52etbhgnr62d6wh` (`username`)
);

CREATE TABLE `user_role`
(
	`id`   bigint      NOT NULL,
	`role` varchar(50) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `UK_s21d8k5lywjjc7inw14brj6ro` (`role`)
);

CREATE TABLE `employees_roles`
(
	`employee_id` bigint NOT NULL,
	`role_id`     bigint NOT NULL,
	KEY           `FK4kr7n7cof8pttnc3ek5vkbxnw` (`role_id`),
	KEY           `FK8kbib04ourjps7lcae95gqdj5` (`employee_id`),
	CONSTRAINT `FK4kr7n7cof8pttnc3ek5vkbxnw` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`),
	CONSTRAINT `FK8kbib04ourjps7lcae95gqdj5` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
);

CREATE TABLE `seq_generator`
(
	`next_val` bigint DEFAULT NULL
);

CREATE TABLE `tb_physician`
(
	`id`         bigint      NOT NULL,
	`first_name` varchar(50) NOT NULL,
	`last_name`  varchar(50) NOT NULL,
	`specialty`  varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `tb_patient`
(
	`id`         bigint      NOT NULL,
	`first_name` varchar(50) NOT NULL,
	`last_name`  varchar(50) NOT NULL,
	`email`      varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `tb_appointment`
(
	`id`           bigint NOT NULL,
	`status`       varchar(255) DEFAULT NULL,
	`patient_id`   bigint       DEFAULT NULL,
	`physician_id` bigint       DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY            `FKo2mcr811mn2fl6l72ufultl55` (`patient_id`),
	KEY            `FK5cl7ikqdqck7sognp6xngrhec` (`physician_id`),
	CONSTRAINT `FK5cl7ikqdqck7sognp6xngrhec` FOREIGN KEY (`physician_id`) REFERENCES `tb_physician` (`id`),
	CONSTRAINT `FKo2mcr811mn2fl6l72ufultl55` FOREIGN KEY (`patient_id`) REFERENCES `tb_patient` (`id`)
);

CREATE TABLE `tb_patient_appointment`
(
	`patient_id`     bigint NOT NULL,
	`appointment_id` bigint NOT NULL,
	PRIMARY KEY (`patient_id`, `appointment_id`),
	UNIQUE KEY `UK_mb92quyn5omanm6b0vyly8epo` (`appointment_id`),
	CONSTRAINT `FK21xjmdrqn4ct0opqrpxtaa793` FOREIGN KEY (`patient_id`) REFERENCES `tb_patient` (`id`),
	CONSTRAINT `FKm3h65ac95aucw2cjtnvhc0wg9` FOREIGN KEY (`appointment_id`) REFERENCES `tb_appointment` (`id`)
);

CREATE TABLE `tb_physician_appointment`
(
	`physician_id`   bigint NOT NULL,
	`appointment_id` bigint NOT NULL,
	PRIMARY KEY (`physician_id`, `appointment_id`),
	UNIQUE KEY `UK_8qnwewxy5qtxllce0usk8moih` (`appointment_id`),
	CONSTRAINT `FK6phe8n5tewj80ytbtmhosb7fq` FOREIGN KEY (`physician_id`) REFERENCES `tb_physician` (`id`),
	CONSTRAINT `FKj3fn5b6mtldm4d1swdimljtv5` FOREIGN KEY (`appointment_id`) REFERENCES `tb_appointment` (`id`)
);
