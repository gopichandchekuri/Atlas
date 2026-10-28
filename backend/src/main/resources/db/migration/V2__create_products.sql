CREATE TABLE products (
                          id          BIGSERIAL PRIMARY KEY,
                          sku         VARCHAR(64)  NOT NULL UNIQUE,
                          name        VARCHAR(150) NOT NULL,
                          description TEXT,
                          category_id BIGINT       NOT NULL REFERENCES categories(id),
                          price       NUMERIC(12,2) NOT NULL CHECK (price >= 0),
                          status      VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
                          created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_status ON products(status);