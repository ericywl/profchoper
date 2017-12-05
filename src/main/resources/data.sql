INSERT INTO students (id, name, course1_id, course2_id, course3_id, course4_id) VALUES
  (1001111, 'Eric', '50.001', '50.002', '50.004', NULL),
  (1002222, 'Edmund', '50.001', '50.002', '50.004', NULL),
  (1003333, 'Kok', '50.001', '50.002', '50.004', NULL)
  ON CONFLICT (id) DO
  UPDATE SET
      course1_id = EXCLUDED.course1_id,
      course2_id = EXCLUDED.course2_id,
      course3_id = EXCLUDED.course3_id,
      course4_id = EXCLUDED.course4_id;

INSERT INTO professors (name, office, course_id) VALUES
  ('Oka Kurniawan', '1.307', '50.002'),
  ('Zhang Yue', '1.703', '50.002'),
  ('Norman Lee', '1.407', '50.001')
  ON CONFLICT (name) DO
  UPDATE SET
      office = EXCLUDED.office,
      course_id = EXCLUDED.course_id;

INSERT INTO courses (id, name) VALUES
  ('50.001', 'Introduction to Information System & Programming'),
  ('50.002', 'Computation Structures'),
  ('50.004', 'Introduction to Algorithms')
  ON CONFLICT (id) DO NOTHING;