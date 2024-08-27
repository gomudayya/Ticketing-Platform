CREATE TABLE genre (
                       id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_time DATETIME(6) NOT NULL,
                       updated_time DATETIME(6) NOT NULL,
                       genre_name   VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE venue (
                       id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_time     DATETIME(6) NOT NULL,
                       updated_time     DATETIME(6) NOT NULL,
                       venue_name       VARCHAR(100) NOT NULL UNIQUE,
                       address          VARCHAR(255) NOT NULL,
                       seat_count       INT          NULL,
                       seat_layout_data VARCHAR(255) NULL
);

CREATE TABLE seat (
                      id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                      venue_id     BIGINT       NOT NULL,
                      number       INT          NULL,
                      section      VARCHAR(100) NULL,
                      CONSTRAINT FK_seat_venue_id
                          FOREIGN KEY (venue_id) REFERENCES venue(id)
);

CREATE TABLE shows (
                       id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_time   DATETIME(6) NOT NULL,
                       updated_time   DATETIME(6) NOT NULL,
                       title          VARCHAR(255) NULL,
                       performer      VARCHAR(255) NULL,
                       genre_id       BIGINT       NOT NULL,
                       venue_id       BIGINT       NOT NULL,
                       duration       INT          NULL,
                       start_time     DATETIME(6)  NULL,
                       ticketing_time DATETIME(6)  NULL,
                       is_deleted     BIT          NOT NULL DEFAULT 0,
                       title_image    VARCHAR(255) NULL,
                       CONSTRAINT FK_shows_genre_id
                           FOREIGN KEY (genre_id) REFERENCES genre(id),
                       CONSTRAINT FK_shows_venue_id
                           FOREIGN KEY (venue_id) REFERENCES venue(id)
);

CREATE TABLE show_seat (
                           id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                           created_time DATETIME(6) NOT NULL,
                           updated_time DATETIME(6) NOT NULL,
                           show_id      BIGINT       NOT NULL,
                           price        INT          NULL,
                           stock        INT          NULL,
                           seat_section VARCHAR(100) NULL,
                           CONSTRAINT FK_show_seat_show_id
                               FOREIGN KEY (show_id) REFERENCES shows(id)
);