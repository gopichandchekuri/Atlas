CREATE TABLE inventory (
                           id                  BIGSERIAL PRIMARY KEY,
                           product_id          BIGINT    NOT NULL REFERENCES products(id),
                           warehouse_id        BIGINT    NOT NULL REFERENCES warehouses(id),
                           available_quantity  INTEGER   NOT NULL DEFAULT 0 CHECK (available_quantity >= 0),
                           reserved_quantity   INTEGER   NOT NULL DEFAULT 0 CHECK (reserved_quantity >= 0),
                           created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT uq_inventory_product_warehouse UNIQUE (product_id, warehouse_id)
);

CREATE INDEX idx_inventory_warehouse_id ON inventory(warehouse_id);