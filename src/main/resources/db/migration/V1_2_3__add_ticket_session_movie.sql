--    com.cinema.model.Ticket
CREATE TABLE ticket (
  id                INTEGER AUTO_INCREMENT NOT NULL,
  session_id        INTEGER                NOT NULL REFERENCES session (id)
    ON DELETE CASCADE,
  booked_by_user_id INTEGER                NOT NULL REFERENCES `user` (id)
    ON DELETE CASCADE,
  row               INTEGER                NOT NULL,
  seat              INTEGER                NOT NULL,
  updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT UNIQUE session_place (session_id, row, seat)
)
  ENGINE = InnoDB
  CHARACTER SET = UTF8;

-- com.cinema.model.Movie
CREATE TABLE movie (
  id          INTEGER   AUTO_INCREMENT,
  title       VARCHAR(512),
  description VARCHAR(2048),
  duration    INTEGER,
  updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_general_ci;

-- com.cinema.model.Session
CREATE TABLE session (
  id              INTEGER   AUTO_INCREMENT,
  sessiondatetime DATETIME,
  hall_id         INTEGER REFERENCES hall (id)
    ON DELETE CASCADE,
  movie_id        INTEGER REFERENCES movie (id)
    ON DELETE CASCADE,
  updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  CHARACTER SET = UTF8;