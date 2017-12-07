
CREATE TABLE IF NOT EXISTS public.courses
(
  id INTEGER PRIMARY KEY,
  name VARCHAR(150) UNIQUE NOT NULL,
  alias VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS public.students
(
  id INTEGER PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  email VARCHAR(40) UNIQUE NOT NULL,
  course1_id INTEGER REFERENCES courses(id),
  course2_id INTEGER REFERENCES courses(id),
  course3_id INTEGER REFERENCES courses(id),
  course4_id INTEGER REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS public.professors
(
  name VARCHAR(40) PRIMARY KEY,
  email VARCHAR(40) UNIQUE NOT NULL,
  alias VARCHAR(10) UNIQUE,
  office VARCHAR(10) UNIQUE,
  course_id INTEGER REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS public.bookings
(
  id SERIAL PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  professor_alias VARCHAR(10) NOT NULL REFERENCES professors(alias),
  book_status VARCHAR(20) DEFAULT 'available',
  student_id INTEGER DEFAULT NULL REFERENCES students(id),
  UNIQUE (professor_alias, start_time)
);

CREATE TABLE IF NOT EXISTS public.users
(
  username VARCHAR(40) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
  id SERIAL PRIMARY KEY,
  username VARCHAR(40) NOT NULL REFERENCES users(username),
  role VARCHAR(20) NOT NULL,
  UNIQUE (username, role)
)