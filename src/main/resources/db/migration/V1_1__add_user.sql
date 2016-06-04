--    com.cinema.model.User
CREATE TABLE user (
  id        INTEGER AUTO_INCREMENT,
  login     VARCHAR(64) NOT NULL,
  password  VARCHAR(64) NOT NULL,
  firstname VARCHAR(64),
  lastname  VARCHAR(64),
  birthdate DATE,
  role      INTEGER,
  email     VARCHAR(64),
  PRIMARY KEY (id),
  CONSTRAINT login_uq UNIQUE (login)
)
  ENGINE = InnoDB
  CHARACTER SET = UTF8;