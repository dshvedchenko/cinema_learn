INSERT INTO movie (title, description, duration) VALUE
  ('Matrix 1',
   'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.',
   (2 * 60 + 16) * 60)
  , ('Zootopia',
     'In a city of anthropomorphic animals, a rookie bunny cop and a cynical con artist fox must work together to uncover a conspiracy.',
     60 * 108);

INSERT INTO session (
  sessiondatetime
  , hall_id
  , movie_id
)
  SELECT
    '20160508' AS sessiondatetime,
    h.id       AS hall_id,
    m.id       AS movie_id
  FROM movie m,
    hall h
  WHERE h.name = 'red'
        AND m.title = 'Matrix 1';


INSERT INTO session (
  sessiondatetime
  , hall_id
  , movie_id
)
  SELECT
    '20160508' AS sessiondatetime,
    h.id       AS hall_id,
    m.id       AS movie_id
  FROM movie m,
    hall h
  WHERE h.name = 'black'
        AND m.title = 'Zootopia';