

-- Change phone_number column type from integer to varchar
ALTER TABLE subscriptions
    ALTER COLUMN phone_number TYPE VARCHAR(255);

