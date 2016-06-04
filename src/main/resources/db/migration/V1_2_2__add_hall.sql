--    com.cinema.model.Hall
CREATE TABLE hall (
  id       INTEGER AUTO_INCREMENT,
  name     VARCHAR(64) NOT NULL,
  seatrows INTEGER,
  seatcols INTEGER,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  CHARACTER SET = UTF8;