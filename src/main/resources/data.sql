INSERT INTO tb_patient VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');
INSERT INTO tb_patient VALUES (2, 'Paula', 'Martins', 'paula@email.com');
INSERT INTO tb_patient VALUES (3, 'Hugo', 'Silva', 'hugo@email.com');
INSERT INTO tb_patient VALUES (4, 'Ana', 'Martins', 'ana@email.com');
INSERT INTO tb_patient VALUES (5, 'Plínio', 'Ferreira', 'plinio@email.com');
INSERT INTO tb_patient VALUES (6, 'Beatriz', 'Silva', 'beatriz@email.com');
INSERT INTO tb_patient VALUES (7, 'Bianca', 'Cardoso', 'bianca@email.com');
INSERT INTO tb_patient VALUES (8, 'Antonio', 'Nunes', 'antonio@email.com');
INSERT INTO tb_patient VALUES (9, 'Verônica', 'Lima', 'veronica@email.com');
INSERT INTO tb_patient VALUES (10, 'José', 'Santos', 'jose@email.com');
INSERT INTO tb_patient VALUES (11, 'Ana', 'Tavares', 'ana@email.com');
INSERT INTO tb_patient VALUES (12, 'Pedro', 'Siqueira', 'pedro.s@email.com');

INSERT INTO tb_physician VALUES (1, 'Marcela', 'Cavalcante', 'GENERAL_PRACTITIONER');
INSERT INTO tb_physician VALUES (2, 'João', 'Brito', 'DERMATOLOGIST');
INSERT INTO tb_physician VALUES (3, 'Marília', 'Dantas', 'CARDIOLOGIST');
INSERT INTO tb_physician VALUES (4, 'Osvaldo', 'Pereira', 'CARDIOLOGIST');
INSERT INTO tb_physician VALUES (5, 'Marcela', 'Barros', 'ORTHOPEDIST');
INSERT INTO tb_physician VALUES (6, 'Carlos', 'Meneses', 'PSYCHIATRIST');
INSERT INTO tb_physician VALUES (7, 'Paula', 'Meireles', 'PSYCHIATRIST');
INSERT INTO tb_physician VALUES (8, 'Timóteo', 'Borges', 'NEUROLOGIST');
INSERT INTO tb_physician VALUES (9, 'Osvaldo', 'Santos', 'ENDOCRINOLOGIST');
INSERT INTO tb_physician VALUES (10, 'Zélia', 'Silva', 'GENERAL_PRACTITIONER');

INSERT INTO tb_appointment VALUES (1, 1, 2, 'PAYMENT_PENDING');
INSERT INTO tb_appointment VALUES (2, 3, 2, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (3, 4, 1, 'FINISHED');
INSERT INTO tb_appointment VALUES (4, 5, 5, 'CANCELLED');
INSERT INTO tb_appointment VALUES (5, 9, 4, 'CANCELLED');
INSERT INTO tb_appointment VALUES (6, 3, 9, 'PAYMENT_PENDING');
INSERT INTO tb_appointment VALUES (7, 2, 3, 'CANCELLED');
INSERT INTO tb_appointment VALUES (8, 7, 7, 'FINISHED');
INSERT INTO tb_appointment VALUES (9, 10, 8, 'FINISHED');
INSERT INTO tb_appointment VALUES (10, 12, 10, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (11, 11, 6, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (12, 3, 7, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (13, 4, 8, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (14, 5, 6, 'FINISHED');
INSERT INTO tb_appointment VALUES (15, 9, 5, 'FINISHED');
INSERT INTO tb_appointment VALUES (16, 7, 9, 'CANCELLED');
INSERT INTO tb_appointment VALUES (17, 2, 3, 'PAYMENT_PENDING');
INSERT INTO tb_appointment VALUES (18, 7, 7, 'PAYMENT_PENDING');
INSERT INTO tb_appointment VALUES (19, 6, 1, 'CONFIRMED');
INSERT INTO tb_appointment VALUES (20, 8, 2, 'CONFIRMED');

INSERT INTO tb_patient_appointment VALUES (1, 1);
INSERT INTO tb_patient_appointment VALUES (3, 2);
INSERT INTO tb_patient_appointment VALUES (4, 3);
INSERT INTO tb_patient_appointment VALUES (5, 4);
INSERT INTO tb_patient_appointment VALUES (9, 5);
INSERT INTO tb_patient_appointment VALUES (3, 6);
INSERT INTO tb_patient_appointment VALUES (2, 7);
INSERT INTO tb_patient_appointment VALUES (7, 8);
INSERT INTO tb_patient_appointment VALUES (10, 9);
INSERT INTO tb_patient_appointment VALUES (12, 10);
INSERT INTO tb_patient_appointment VALUES (11, 11);
INSERT INTO tb_patient_appointment VALUES (3, 12);
INSERT INTO tb_patient_appointment VALUES (4, 13);
INSERT INTO tb_patient_appointment VALUES (5, 14);
INSERT INTO tb_patient_appointment VALUES (9, 15);
INSERT INTO tb_patient_appointment VALUES (7, 16);
INSERT INTO tb_patient_appointment VALUES (2, 17);
INSERT INTO tb_patient_appointment VALUES (7, 18);
INSERT INTO tb_patient_appointment VALUES (6, 19);
INSERT INTO tb_patient_appointment VALUES (8, 20);

INSERT INTO tb_physician_appointment VALUES (2, 1);
INSERT INTO tb_physician_appointment VALUES (2, 2);
INSERT INTO tb_physician_appointment VALUES (1, 3);
INSERT INTO tb_physician_appointment VALUES (5, 4);
INSERT INTO tb_physician_appointment VALUES (4, 5);
INSERT INTO tb_physician_appointment VALUES (9, 6);
INSERT INTO tb_physician_appointment VALUES (3, 7);
INSERT INTO tb_physician_appointment VALUES (7, 8);
INSERT INTO tb_physician_appointment VALUES (8, 9);
INSERT INTO tb_physician_appointment VALUES (10, 10);
INSERT INTO tb_physician_appointment VALUES (6, 11);
INSERT INTO tb_physician_appointment VALUES (7, 12);
INSERT INTO tb_physician_appointment VALUES (8, 13);
INSERT INTO tb_physician_appointment VALUES (6, 14);
INSERT INTO tb_physician_appointment VALUES (5, 15);
INSERT INTO tb_physician_appointment VALUES (9, 16);
INSERT INTO tb_physician_appointment VALUES (3, 17);
INSERT INTO tb_physician_appointment VALUES (7, 18);
INSERT INTO tb_physician_appointment VALUES (1, 19);
INSERT INTO tb_physician_appointment VALUES (2, 20);


















