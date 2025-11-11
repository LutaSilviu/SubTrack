ALTER SEQUENCE invoice_id_seq INCREMENT BY 1;

ALTER SEQUENCE plan_id_seq INCREMENT BY 1;

ALTER SEQUENCE records_id_seq INCREMENT BY 1;

ALTER SEQUENCE subscription_id_seq INCREMENT BY 1;

ALTER SEQUENCE user_id_seq INCREMENT BY 1;

ALTER TABLE plans
    ALTER COLUMN active DROP NOT NULL;

ALTER TABLE invoices
    DROP COLUMN status;

ALTER TABLE invoices
    ADD status VARCHAR(255);

ALTER TABLE subscriptions
    DROP COLUMN status;

ALTER TABLE subscriptions
    ADD status VARCHAR(255);