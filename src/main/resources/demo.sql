CREATE TABLE IF NOT EXISTS cpe (
    cpe_name_id UUID NOT NULL,
    cpe_name VARCHAR(255) NOT NULL,
    deprecated BOOLEAN NOT NULL,
    created TIMESTAMPTZ NOT NULL,
    last_modified TIMESTAMPTZ NOT NULL,
    title JSONB,
    reference JSONB,
    deprecated_by JSONB,
    deprecates JSONB,
    PRIMARY KEY (cpe_name_id)
    );