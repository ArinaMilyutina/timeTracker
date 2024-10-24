CREATE table
tasks (
                                     id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                     title VARCHAR(100) NOT NULL,
    description TEXT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    project_id BIGINT,
    admin_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE SET NULL
    );