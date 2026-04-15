CREATE TABLE notifications (
    id         UUID         NOT NULL PRIMARY KEY,
    recipient  VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    type       VARCHAR(50)  NOT NULL,
    status     VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL
);
