
CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    this_value DOUBLE,
    this_unit VARCHAR(100),
    this_measurement_type VARCHAR(100),

    that_value DOUBLE,
    that_unit VARCHAR(100),
    that_measurement_type VARCHAR(100),

    operation VARCHAR(50) NOT NULL,

    result_value DOUBLE,
    result_unit VARCHAR(100),
    result_measurement_type VARCHAR(100),

    result_string VARCHAR(500),

    is_error BOOLEAN NOT NULL DEFAULT FALSE,
    error_message VARCHAR(1000),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    measurement_id BIGINT NOT NULL,
    operation VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_measurement_history
        FOREIGN KEY (measurement_id)
        REFERENCES quantity_measurement_entity(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_measurement_operation
    ON quantity_measurement_entity(operation);

CREATE INDEX idx_measurement_type
    ON quantity_measurement_entity(this_measurement_type);

CREATE INDEX idx_history_measurement
    ON quantity_measurement_history(measurement_id);
