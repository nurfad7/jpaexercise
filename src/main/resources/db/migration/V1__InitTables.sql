-- Table: users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password TEXT NOT NULL,
    pin VARCHAR(4),
    is_onboarding_finished BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: user_details
CREATE TABLE user_details (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    name VARCHAR(50),
    profile_picture_url VARCHAR(100),
    quotes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: auth_providers
CREATE TABLE auth_providers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: user_auth_providers
CREATE TABLE user_auth_providers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    provider_id BIGINT REFERENCES auth_providers(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: transaction_types
CREATE TABLE transaction_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: wallets
CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    name VARCHAR(20) NOT NULL,
    amount NUMERIC(10, 2) DEFAULT 0 NOT NULL CHECK (amount >= 0),
    is_active BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: emojis
CREATE TABLE emojis (
    id BIGSERIAL PRIMARY KEY,
    emoji VARCHAR(1) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: pockets
CREATE TABLE pockets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    name VARCHAR(20) NOT NULL,
	amount NUMERIC(10, 2) DEFAULT 0 NOT NULL CHECK (amount >= 0),
    description VARCHAR(100),
    emoji_id BIGINT REFERENCES emojis(id),
    wallet_id BIGINT NOT NULL REFERENCES wallets(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: goals
CREATE TABLE goals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    amount NUMERIC(10, 2) DEFAULT 0 NOT NULL CHECK (amount >= 0),
    description VARCHAR(100),
    wallet_id BIGINT NOT NULL REFERENCES wallets(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Table: transactions
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    name VARCHAR(50) NOT NULL,
    type_id BIGINT NOT NULL REFERENCES transaction_types(id),
    pocket_id BIGINT REFERENCES pockets(id),
    goal_id BIGINT REFERENCES goals(id),
    wallet_id BIGINT NOT NULL REFERENCES wallets(id),
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    amount NUMERIC(10, 2) DEFAULT 0 NOT NULL CHECK (amount >= 0),
    description VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id INTEGER NOT NULL REFERENCES roles(id),
    assigned_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- Indexes
CREATE UNIQUE INDEX idx_users_email_unique ON users(email) WHERE deleted_at IS NULL;
CREATE INDEX idx_user_details_user_id ON user_details(user_id);
CREATE INDEX idx_user_auth_providers_user_id ON user_auth_providers(user_id);
CREATE INDEX idx_user_auth_providers_provider_id ON user_auth_providers(provider_id);
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_wallet_id ON transactions(wallet_id);
CREATE INDEX idx_transactions_date ON transactions(date);
CREATE UNIQUE INDEX idx_wallets_name_unique ON wallets(name) WHERE deleted_at IS NULL;
CREATE INDEX idx_wallets_is_active ON wallets(is_active);
CREATE UNIQUE INDEX idx_emojis_emoji ON emojis(emoji) WHERE deleted_at IS NULL;
CREATE INDEX idx_pockets_user_id ON pockets(user_id);
CREATE UNIQUE INDEX idx_pockets_name_unique ON pockets(name) WHERE deleted_at IS NULL;
CREATE INDEX idx_pockets_emoji_id ON pockets(emoji_id);
CREATE INDEX idx_pockets_wallet_id ON pockets(wallet_id);
CREATE INDEX idx_goals_wallet_id ON goals(wallet_id);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);