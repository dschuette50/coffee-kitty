ALTER TABLE users ADD COLUMN personal_balance_cents bigint NOT NULL DEFAULT 0;
ALTER TABLE users ADD COLUMN deactivated boolean NOT NULL DEFAULT false;

CREATE TABLE kitty_cash_balance (
    id uuid NOT NULL PRIMARY KEY CHECK (id = '00000000-0000-0000-0000-000000000001'),
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    balance_cents bigint NOT NULL DEFAULT 0 CHECK (balance_cents >= 0)
);

CREATE TABLE cash_deposits (
    id uuid NOT NULL PRIMARY KEY,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    amount_cents bigint NOT NULL CHECK (amount_cents BETWEEN 1 AND 100000),
    recipient_id uuid NOT NULL REFERENCES users(id),
    recorded_by_id uuid NOT NULL REFERENCES users(id)
);
