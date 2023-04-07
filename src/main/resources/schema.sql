CREATE TABLE tb_patient (
    id bigint PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE tb_physician (
    id bigint PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialty VARCHAR NOT NULL
);

CREATE TABLE tb_appointment (
    id bigint PRIMARY KEY NOT NULL,
    patient_id bigint NOT NULL,
    physician_id bigint NOT NULL,
    status VARCHAR NOT NULL
);

CREATE TABLE tb_patient_appointment (
    patient_id bigint NOT NULL,
    appointment_id bigint NOT NULL
);

CREATE TABLE tb_physician_appointment (
    physician_id bigint NOT NULL,
    appointment_id bigint NOT NULL
);
