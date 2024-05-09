package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudent(Long id);
    Student updateStudent(Student student, Long id);
    void removeStudent(Long id);
    List<Student> studentsByAge(Integer age);
    List<Student> findStudentByAgeBetweenMinAgeAndMaxAge(Integer minAge, Integer maxAge);
    Faculty findFacultyForStudent(Long id);


    Integer findCountOfStudent();

    Double findAveragAge();

    List<Student> findLastFiveStudents();
}
