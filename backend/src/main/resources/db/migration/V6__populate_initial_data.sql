-- Populate initial data for testing

-- Insert sample plans
INSERT INTO plans (plan_id, name, price, included_gb, overage_price, active, created_at)
VALUES
    (1, 'Basic Plan', 9.99, 5, 2.00, true, CURRENT_DATE),
    (2, 'Standard Plan', 19.99, 15, 1.50, true, CURRENT_DATE),
    (3, 'Premium Plan', 29.99, 30, 1.00, true, CURRENT_DATE),
    (4, 'Business Plan', 49.99, 100, 0.50, true, CURRENT_DATE),
    (5, 'Legacy Plan', 14.99, 10, 2.50, false, CURRENT_DATE);

-- Update sequences to continue from the last inserted ID
SELECT setval('plan_id_seq', (SELECT MAX(plan_id) FROM plans) + 1);
SELECT setval('user_id_seq', (SELECT MAX(user_id) FROM users) + 1);
SELECT setval('subscription_id_seq', (SELECT MAX(subscription_id) FROM subscriptions) + 1);
SELECT setval('records_id_seq', (SELECT MAX(record_id) FROM usage_records) + 1);
SELECT setval('invoice_id_seq', (SELECT MAX(invoice_id) FROM invoices) + 1);

