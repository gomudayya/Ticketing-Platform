CREATE TABLE orders (
                        id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                        created_time DATETIME(6) NOT NULL,
                        updated_time DATETIME(6) NOT NULL,
                        show_id      BIGINT      NULL,
                        user_id      BIGINT      NULL,
                        order_status ENUM('CANCEL', 'PENDING', 'REFUND', 'SUCCESS')  NULL
);

CREATE TABLE payment (
                         id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_time   DATETIME(6) NOT NULL,
                         updated_time   DATETIME(6) NOT NULL,
                         amount         INT         NULL,
                         payment_status ENUM('SUCCESS', 'FAIL') NOT NULL,
                         order_id       BIGINT      NULL,
                         CONSTRAINT FK_payment_order_id
                             FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE ticket (
                        id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                        created_time  DATETIME(6) NOT NULL,
                        updated_time  DATETIME(6) NOT NULL,
                        user_id       BIGINT      NULL,
                        price         INT         NULL,
                        code          VARCHAR(255) NULL,
                        is_deleted    BIT         NOT NULL DEFAULT 0,
                        ticket_status ENUM('AVAILABLE', 'SELECTED', 'SOLD') NULL
);

CREATE TABLE order_ticket (
                              id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                              created_time DATETIME(6) NOT NULL,
                              updated_time DATETIME(6) NOT NULL,
                              order_id     BIGINT      NULL,
                              ticket_id    BIGINT      NULL,
                              CONSTRAINT FK_order_ticket_order_id
                                  FOREIGN KEY (order_id) REFERENCES orders(id),
                              CONSTRAINT FK_order_ticket_ticket_id
                                  FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);
