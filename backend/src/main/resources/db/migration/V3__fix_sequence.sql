-- ===== LINK SEQUENCES TO COLUMNS + DEFAULT nextval =====
alter sequence invoice_id_seq      owned by invoices.invoice_id;
alter table   invoices alter column invoice_id set default nextval('invoice_id_seq');

alter sequence plan_id_seq         owned by plans.plan_id;
alter table   plans   alter column plan_id    set default nextval('plan_id_seq');

alter sequence user_id_seq         owned by users.user_id;
alter table   users   alter column user_id    set default nextval('user_id_seq');

-- Notă: secvența se numește records_id_seq, iar coloana e record_id. E OK dacă pui DEFAULT corect:
alter sequence records_id_seq      owned by usage_records.record_id;
alter table   usage_records alter column record_id set default nextval('records_id_seq');

alter sequence subscription_id_seq owned by subscriptions.subscription_id;
alter table   subscriptions alter column subscription_id set default nextval('subscription_id_seq');

ALTER TABLE subscriptions
    DROP COLUMN created_at;

ALTER TABLE subscriptions
    ADD created_at date;