DROP TABLE public.students;

CREATE TABLE IF NOT EXISTS public.students
(
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL,
  course1 TEXT,
  course2 TEXT,
  course3 TEXT,
  course4 TEXT
);