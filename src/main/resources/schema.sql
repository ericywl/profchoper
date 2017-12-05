DROP TABLE IF EXISTS public.students;
DROP TABLE IF EXISTS public.professors;

CREATE TABLE IF NOT EXISTS public.students
(
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL,
  course1 TEXT,
  course2 TEXT,
  course3 TEXT,
  course4 TEXT
);

CREATE TABLE IF NOT EXISTS public.professors
(
  name TEXT PRIMARY KEY ,
  office TEXT NOT NULL,
  course TEXT
);