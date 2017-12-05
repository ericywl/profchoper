INSERT INTO students (id, name, course1, course2, course3, course4) VALUES
  (1001111, 'Eric', '50.001', '50.002', '50.004', NULL),
  (1002222, 'Edmund', '50.001', '50.002', '50.004', NULL),
  (1003333, 'Kok', '50.001', '50.002', '50.004', NULL)
  ON CONFLICT (id) DO NOTHING ;