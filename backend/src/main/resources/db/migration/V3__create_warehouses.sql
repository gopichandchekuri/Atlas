CREATE TABLE warehouses (
                            id          BIGSERIAL PRIMARY KEY,
                            code        VARCHAR(30)  NOT NULL UNIQUE,
                            name        VARCHAR(150) NOT NULL,
                            location    VARCHAR(255) NOT NULL,
                            status      VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
                            created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);