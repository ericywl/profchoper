INSERT INTO courses (id, name, alias) VALUES
  (50001, 'Introduction to Information System & Programming', 'InfoSys'),
  (50002, 'Computation Structures', 'CompStruct'),
  (50004, 'Introduction to Algorithms', 'Algo'),
  (02125, 'Normalcy and Deviance: Philosophical Approaches to Sexuality', 'NormalcyDeviance'),
  (02113, 'The Laboratory of the Mind', 'LabOfMind')
ON CONFLICT (id)
  DO NOTHING;

INSERT INTO students (id, name, email, course1_id, course2_id, course3_id, course4_id) VALUES
  (1001111, 'Eric', 'eric@mymail.sutd.edu.sg', 50001, 50002, 50004, 02125),
  (1002222, 'Thanh', 'thanh@mymail.sutd.edu.sg', 50001, 50002, 50004, 02125),
  (1003333, 'Kok', 'kok@mymail.sutd.edu.sg', 50001, 50002, 50004, 02113),
  (1002423, 'Wen Tat', 'wentat@mymail.sutd.edu.sg', 50001, 50002, 50004, 02125),
  (1002523, 'Roshni', 'roshni@mymail.sutd.edu.sg', 50001, 50002, 50004, 02113),
  (1002859, 'Ragini', 'ragini@mymail.sutd.edu.sg', 50001, 50002, 50004, 02113)
ON CONFLICT (id)
  DO
  UPDATE SET
    course1_id = EXCLUDED.course1_id,
    course2_id = EXCLUDED.course2_id,
    course3_id = EXCLUDED.course3_id,
    course4_id = EXCLUDED.course4_id;

INSERT INTO professors (name, alias, email, office, course_id) VALUES
  ('Oka Kurniawan', 'oka', 'oka_kurniawan@sutd.edu.sg', '1.502-27', 50002),
  ('Zhang Yue', 'zy', 'yue_zhang@sutd.edu.sg', '1.702-34', 50002),
  ('Yuen Chau', 'yc', 'yuenchau@sutd.edu.sg', '3.301-07', 50002),
  ('Zhou Jianying', 'jy', 'jianying_zhou@sutd.edu.sg', '1.302-03', 50002),
  ('Norman Lee', 'norman', 'norman_lee@sutd.edu.sg', '1.502-25', 50001),
  ('Jit Biswas', 'jit', 'jit_biswas@sutd.edu.sg', '1.602-28', 50001),
  ('Ngai-Man Cheung', 'man', 'ngaiman_cheung@sutd.edu.sg', '1.502-17', 50001),
  ('Subhajit Datta', 'datta', 'subhajit_datta@sutd.edu.sg', '1.702-32', 50004),
  ('Simon Lui', 'simon', 'simon_lui@sutd.edu.sg', '1.502-18', 50004),
  ('Gemma Roig', 'gemma', 'gemma_roig@sutd.edu.sg', '1.702-33', 50004)
ON CONFLICT (name)
  DO
  UPDATE SET
    alias     = EXCLUDED.alias,
    office    = EXCLUDED.office,
    course_id = EXCLUDED.course_id;

INSERT INTO bookings (professor_alias, start_time) VALUES
  ('zy', make_timestamp(2017, 12, 6, 11, 0, 0)),
  ('zy', make_timestamp(2017, 12, 6, 11, 30, 0)),

  ('jy', make_timestamp(2017, 12, 6, 15, 30, 0)),
  ('jy', make_timestamp(2017, 12, 6, 16, 00, 0)),
  ('jy', make_timestamp(2017, 12, 6, 16, 30, 0)),

  ('yc', make_timestamp(2017, 12, 6, 13, 00, 0)),
  ('yc', make_timestamp(2017, 12, 6, 13, 30, 0)),
  ('yc', make_timestamp(2017, 12, 6, 14, 00, 0)),
  ('yc', make_timestamp(2017, 12, 6, 14, 30, 0)),

  ('oka', make_timestamp(2017, 12, 6, 15, 00, 0)),
  ('oka', make_timestamp(2017, 12, 6, 15, 30, 0)),
  ('oka', make_timestamp(2017, 12, 6, 16, 00, 0)),
  ('oka', make_timestamp(2017, 12, 8, 9, 00, 0)),
  ('oka', make_timestamp(2017, 12, 8, 9, 30, 0)),
  ('oka', make_timestamp(2017, 12, 8, 10, 00, 0)),
  ('oka', make_timestamp(2017, 12, 8, 10, 30, 0)),

  ('man', make_timestamp(2017, 12, 6, 14, 0, 0)),
  ('man', make_timestamp(2017, 12, 6, 14, 30, 0)),
  ('man', make_timestamp(2017, 12, 6, 15, 0, 0)),
  ('man', make_timestamp(2017, 12, 6, 15, 30, 0)),
  ('man', make_timestamp(2017, 12, 6, 16, 00, 0)),
  ('man', make_timestamp(2017, 12, 6, 16, 30, 0)),

  ('norman', make_timestamp(2017, 12, 7, 15, 0, 0)),
  ('norman', make_timestamp(2017, 12, 7, 15, 30, 0)),
  ('norman', make_timestamp(2017, 12, 7, 16, 00, 0)),
  ('norman', make_timestamp(2017, 12, 7, 16, 30, 0))
ON CONFLICT (professor_alias, start_time)
  DO NOTHING;

INSERT INTO users (username, password, enabled) VALUES
  ('eric@mymail.sutd.edu.sg', 'password', TRUE),
  ('wentat@mymail.sutd.edu.sg', 'password', TRUE),
  ('kok@mymail.sutd.edu.sg', 'password', TRUE),
  ('ngaiman_cheung@sutd.edu.sg', 'prof_password', TRUE),
  ('jit_biswas@sutd.edu.sg', 'prof_password', TRUE)
ON CONFLICT (username)
  DO
  UPDATE SET
    password = EXCLUDED.password,
    enabled  = EXCLUDED.enabled;

INSERT INTO user_roles (username, role) VALUES
  ('eric@mymail.sutd.edu.sg', 'ROLE_STUDENT'),
  ('wentat@mymail.sutd.edu.sg', 'ROLE_STUDENT'),
  ('kok@mymail.sutd.edu.sg', 'ROLE_STUDENT'),
  ('ngaiman_cheung@sutd.edu.sg', 'ROLE_PROFESSOR'),
  ('jit_biswas@sutd.edu.sg', 'ROLE_PROFESSOR')
ON CONFLICT (username, role)
  DO NOTHING;


