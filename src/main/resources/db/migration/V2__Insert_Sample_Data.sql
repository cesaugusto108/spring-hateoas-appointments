INSERT INTO `appointments_tracker_dev`.`seq_generator` (`next_val`) VALUES (1000);

INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (2, 'Paula', 'Martins', 'paula@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (3, 'Hugo', 'Silva', 'hugo@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (4, 'Ana', 'Martins', 'ana@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (5, 'Plínio', 'Ferreira', 'plinio@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (6, 'Beatriz', 'Silva', 'beatriz@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (7, 'Bianca', 'Cardoso', 'bianca@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (8, 'Antonio', 'Nunes', 'antonio@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (9, 'Verônica', 'Lima', 'veronica@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (10, 'José', 'Santos', 'jose@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (11, 'Ana', 'Tavares', 'ana@email.com');
INSERT INTO `appointments_tracker_dev`.`tb_patient` (`id`, `first_name`, `last_name`, `email`)
    VALUES (12, 'Pedro', 'Siqueira', 'pedro.s@email.com');

INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (1, 'Marcela', 'Cavalcante', 'GENERAL_PRACTITIONER');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (2, 'João', 'Brito', 'DERMATOLOGIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (3, 'Marília', 'Dantas', 'CARDIOLOGIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (4, 'Osvaldo', 'Pereira', 'CARDIOLOGIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (5, 'Marcela', 'Barros', 'ORTHOPEDIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (6, 'Carlos', 'Meneses', 'PSYCHIATRIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (7, 'Paula', 'Meireles', 'PSYCHIATRIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (8, 'Timóteo', 'Borges', 'NEUROLOGIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (9, 'Osvaldo', 'Santos', 'ENDOCRINOLOGIST');
INSERT INTO `appointments_tracker_dev`.`tb_physician` (`id`, `first_name`, `last_name`, `specialty`)
    VALUES (10, 'Zélia', 'Silva', 'GENERAL_PRACTITIONER');

INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (1, 1, 2, 'PAYMENT_PENDING');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (2, 3, 2, 'CONFIRMED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (3, 4, 1, 'FINISHED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (4, 5, 5, 'CANCELLED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (5, 9, 4, 'CANCELLED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (6, 3, 9, 'PAYMENT_PENDING');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (7, 2, 3, 'CANCELLED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (8, 7, 7, 'FINISHED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (9, 10, 8, 'FINISHED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (10, 12, 10, 'CONFIRMED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (11, 9, 1, 'PAYMENT_PENDING');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (12, 3, 7, 'CONFIRMED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (13, 4, 8, 'CONFIRMED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (14, 5, 6, 'FINISHED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (15, 9, 5, 'FINISHED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (16, 7, 9, 'CANCELLED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (17, 2, 3, 'PAYMENT_PENDING');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (18, 7, 7, 'PAYMENT_PENDING');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (19, 6, 1, 'CONFIRMED');
INSERT INTO `appointments_tracker_dev`.`tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)
    VALUES (20, 8, 2, 'CONFIRMED');

INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (1, 1);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (3, 2);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (4, 3);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (5, 4);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (9, 5);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (3, 6);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (2, 7);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (7, 8);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (10, 9);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (12, 10);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (9, 11);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (3, 12);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (4, 13);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (5, 14);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (9, 15);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (7, 16);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (2, 17);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (7, 18);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (6, 19);
INSERT INTO `appointments_tracker_dev`.`tb_patient_appointment` (`patient_id`, `appointment_id`)
    VALUES (8, 20);

INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (2, 1);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (2, 2);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (1, 3);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (5, 4);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (4, 5);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (9, 6);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (3, 7);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (7, 8);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (8, 9);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (10, 10);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (1, 11);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (7, 12);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (2, 13);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (6, 14);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (5, 15);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (9, 16);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (3, 17);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (7, 18);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (1, 19);
INSERT INTO `appointments_tracker_dev`.`tb_physician_appointment` (`physician_id`, `appointment_id`)
    VALUES (2, 20);

INSERT INTO `appointments_tracker_dev`.`sec_seq_generator` (`next_val`) VALUES (2000);

INSERT INTO `appointments_tracker_dev`.`employee` (`id`, `active`, `password`, `username`)
    VALUES (9000, 1, '$2a$10$ylWTacvFAxgYzXQKFwUTFOL56jTdfvIjL/DNqet/kCx//sp7/1rli', 'monteiro');
INSERT INTO `appointments_tracker_dev`.`employee` (`id`, `active`, `password`, `username`)
    VALUES (9001, 1, '$2a$10$ylWTacvFAxgYzXQKFwUTFOL56jTdfvIjL/DNqet/kCx//sp7/1rli', 'almeida');
INSERT INTO `appointments_tracker_dev`.`employee` (`id`, `active`, `password`, `username`)
    VALUES (9002, 1, '$2a$10$ylWTacvFAxgYzXQKFwUTFOL56jTdfvIjL/DNqet/kCx//sp7/1rli', 'santos');
INSERT INTO `appointments_tracker_dev`.`employee` (`id`, `active`, `password`, `username`)
    VALUES (9003, 1, '$2a$10$ylWTacvFAxgYzXQKFwUTFOL56jTdfvIjL/DNqet/kCx//sp7/1rli', 'araujo');

INSERT INTO `appointments_tracker_dev`.`user_role` (`id`, `role`)
    VALUES (10000, 'ROLE_EMPLOYEE');
INSERT INTO `appointments_tracker_dev`.`user_role` (`id`, `role`)
    VALUES (10001, 'ROLE_MANAGER');
INSERT INTO `appointments_tracker_dev`.`user_role` (`id`, `role`)
    VALUES (10002, 'ROLE_ADMIN');
INSERT INTO `appointments_tracker_dev`.`user_role` (`id`, `role`)
    VALUES (10003, 'ROLE_TRAINEE');

INSERT INTO `appointments_tracker_dev`.`employees_roles` (`employee_id`, `role_id`)
    VALUES (9000, 10000);
INSERT INTO `appointments_tracker_dev`.`employees_roles` (`employee_id`, `role_id`)
    VALUES (9001, 10001);
INSERT INTO `appointments_tracker_dev`.`employees_roles` (`employee_id`, `role_id`)
    VALUES (9002, 10002);
INSERT INTO `appointments_tracker_dev`.`employees_roles` (`employee_id`, `role_id`)
    VALUES (9003, 10003);