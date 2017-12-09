DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS professors;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

CREATE TABLE IF NOT EXISTS public.courses
(
  id    VARCHAR(10) PRIMARY KEY,
  name  VARCHAR(150) NOT NULL,
  alias VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS public.students
(
  id         INTEGER PRIMARY KEY,
  name       VARCHAR(40)        NOT NULL,
  email      VARCHAR(40) UNIQUE NOT NULL,
  course1_id VARCHAR(10) REFERENCES courses (id),
  course2_id VARCHAR(10) REFERENCES courses (id),
  course3_id VARCHAR(10) REFERENCES courses (id),
  course4_id VARCHAR(10) REFERENCES courses (id)
);

CREATE TABLE IF NOT EXISTS public.professors
(
  name      VARCHAR(40) PRIMARY KEY,
  email     VARCHAR(40) UNIQUE NOT NULL,
  alias     VARCHAR(10) UNIQUE,
  office    VARCHAR(10) UNIQUE,
  course_id VARCHAR(10) REFERENCES courses (id)
);

CREATE TABLE IF NOT EXISTS public.bookings
(
  id              SERIAL PRIMARY KEY,
  start_time      TIMESTAMP   NOT NULL,
  professor_alias VARCHAR(10) NOT NULL REFERENCES professors (alias),
  book_status     VARCHAR(20) DEFAULT 'AVAILABLE',
  student_id      INTEGER     DEFAULT NULL REFERENCES students (id),
  UNIQUE (professor_alias, start_time)
);

CREATE TABLE IF NOT EXISTS public.users
(
  username VARCHAR(40) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  enabled  BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
  id       SERIAL PRIMARY KEY,
  username VARCHAR(40) NOT NULL REFERENCES users (username),
  role     VARCHAR(20) NOT NULL,
  UNIQUE (username, role)
);