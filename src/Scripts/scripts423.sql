SELECT "Student".name, "Student".age, "Faculty".name
FROM "Student" LEFT JOIN "Faculty" on "Student".faculty_id = "Faculty".id;

SELECT s.name FROM "Student" as s INNER JOIN "avatar" as a on s.id = a.student_id;