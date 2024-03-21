package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudent(long id);
    Student updateStudent(long id, Student student);
    Student removeStudent(long id);

    Map<Integer, List<Student>> studentsByAge(int age);
}
