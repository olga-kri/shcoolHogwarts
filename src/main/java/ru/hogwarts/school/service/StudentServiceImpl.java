package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentDontFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    @Override
    public Student getStudent(long id) {
         return studentRepository.findById(id).orElseThrow(StudentDontFoundException::new);
    }
    @Override
    public Student updateStudent(Student student, long id) {
        studentRepository.findById(id).orElseThrow(StudentDontFoundException::new);
        return studentRepository.save(student);
    }
    @Override
    public void removeStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> studentsByAge(int age) {
        return studentRepository.findAllByAge(age);
    }
}
