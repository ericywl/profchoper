INSERT INTO students (id, name, course1, course2, course3, course4) VALUES
  (1001111, 'Eric', '50.001', '50.002', '50.004', NULL),
  (1002222, 'Edmund', '50.001', '50.002', '50.004', NULL),
  (1003333, 'Kok', '50.001', '50.002', '50.004', NULL)
  ON CONFLICT (id) DO NOTHING;

INSERT INTO professors (name, office, course) VALUES
  ('Oka Kurniawan', '1.307', '50.002'),
  ('Zhang Yue', '1.703', '50.002'),
  ('Norman Lee', '1.407', '50.001')
  ON CONFLICT (name) DO
  UPDATE SET
      office = EXCLUDED.office,
      course = EXCLUDED.course;