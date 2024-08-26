CREATE TABLE user (
                          id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_time DATETIME(6)            NOT NULL,
                          updated_time DATETIME(6)            NOT NULL,
                          email        VARCHAR(100)           NOT NULL UNIQUE,
                          is_deleted   BIT                    NOT NULL DEFAULT 0,
                          name         VARCHAR(100)           NOT NULL,
                          password     VARCHAR(255)           NOT NULL,
                          phone_number VARCHAR(20)            NOT NULL,
                          user_role    ENUM('ADMIN', 'USER')  NOT NULL DEFAULT 'USER'
);

CREATE TABLE wishlist (
                          id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                          created_time DATETIME(6) NOT NULL,
                          updated_time DATETIME(6) NOT NULL,
                          is_deleted   BIT         NOT NULL DEFAULT 0,
                          show_id      BIGINT      NOT NULL,
                          user_id      BIGINT      NOT NULL,
                          CONSTRAINT FK_wishlist_user_id
                              FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
