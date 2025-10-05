ALTER TABLE doctors
    ADD is_active BOOLEAN;

UPDATE doctors
SET is_active = '1'
WHERE is_active IS NULL;
ALTER TABLE doctors
    ALTER COLUMN is_active SET NOT NULL;