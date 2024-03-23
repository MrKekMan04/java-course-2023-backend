--liquibase formatted sql

--changeset MrKekMan04:4
CREATE TABLE IF NOT EXISTS github_link
(
    id             BIGINT REFERENCES link (id) ON DELETE CASCADE,
    default_branch VARCHAR(64),
    forks_count    BIGINT,
    PRIMARY KEY (id)
);

--changeset MrKekMan04:5
CREATE TABLE IF NOT EXISTS stackoverflow_link
(
    id           BIGINT REFERENCES link (id) ON DELETE CASCADE,
    answer_count BIGINT,
    score        BIGINT,
    PRIMARY KEY (id)
);
