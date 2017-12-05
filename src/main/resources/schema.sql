CREATE TABLE IF NOT EXISTS public.courses
(
  id TEXT PRIMARY KEY,
  name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.students
(
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT UNIQUE NOT NULL,
  course1_id TEXT REFERENCES courses(id),
  course2_id TEXT REFERENCES courses(id),
  course3_id TEXT REFERENCES courses(id),
  course4_id TEXT REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS public.professors
(
  name TEXT PRIMARY KEY,
  email TEXT UNIQUE NOT NULL,
  alias TEXT UNIQUE,
  office TEXT UNIQUE,
  course_id TEXT REFERENCES courses(id)
);

DROP TABLE IF EXISTS public.bookings;

CREATE TABLE IF NOT EXISTS public.bookings
(
  id SERIAL PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  professor_alias TEXT NOT NULL REFERENCES professors(alias),
  book_status TEXT DEFAULT 'AVAILABLE',
  student_id INTEGER DEFAULT NULL REFERENCES students(id),
  UNIQUE (professor_alias, start_time)
);