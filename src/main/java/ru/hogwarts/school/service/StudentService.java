package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudent(long id);
    Student updateStudent(Student student, long id);
    void removeStudent(long id);

    List<Student> studentsByAge(int age);
}
