DROP TABLE IF EXISTS public.students;
DROP TABLE IF EXISTS public.professors;
DROP TABLE IF EXISTS public.bookings;
DROP TABLE IF EXISTS public.courses;

CREATE TABLE IF NOT EXISTS public.students
(
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL,
  course1_id TEXT,
  course2_id TEXT,
  course3_id TEXT,
  course4_id TEXT
);

CREATE TABLE IF NOT EXISTS public.professors
(
  name TEXT PRIMARY KEY,
  alias TEXT UNIQUE,
  office TEXT UNIQUE,
  course_id TEXT
);

CREATE TABLE IF NOT EXISTS public.courses
(
  id TEXT PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.bookings
(
  id SERIAL PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  professor_name TEXT NOT NULL,
  booked BOOLEAN DEFAULT FALSE,
  student_id INTEGER DEFAULT NULL
);