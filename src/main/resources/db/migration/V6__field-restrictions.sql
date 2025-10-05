ALTER TABLE patient
    ADD CONSTRAINT uc_patient_email UNIQUE (email);

ALTER TABLE doctors
    ALTER COLUMN crm SET NOT NULL;

ALTER TABLE doctors
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE doctors
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE doctors
    ALTER COLUMN phone_number SET NOT NULL;