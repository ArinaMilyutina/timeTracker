CREATE TABLE projects
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    start_date  DATE,
    end_date    DATE,
    admin_id    BIGINT,
    FOREIGN KEY (admin_id) REFERENCES users (id) ON DELETE SET NULL
);