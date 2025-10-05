ALTER TABLE doctors
    ADD phone_number VARCHAR(255);

ALTER TABLE doctors
    ADD CONSTRAINT uc_doctors_email UNIQUE (email);