INSERT INTO user (login, password, firstname, lastname, birthdate, role, email)
  VALUE ('admin', 'admin', 'George', 'Luckas', '19520101', 1, 'admin@cinema.example.com'),
  ('johnd', '1qaz', 'Scott', 'Ivanov', '19720303', 0, 'john@example.com');

INSERT INTO hall (name, seatrows, seatcols) VALUE ('red', 2, 2), ('black', 3, 3);