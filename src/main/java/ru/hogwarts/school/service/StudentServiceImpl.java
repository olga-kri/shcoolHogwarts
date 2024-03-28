package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private long studentId = 1L;
    @Override
    public Student addStudent(Student student) {
        student.setId(studentId);
        students.put(studentId,student);
        studentId++;
        return student;
    }
    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }
    @Override
    public Student updateStudent(Student student) {
        if (!students.containsKey(student.getId())){
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student removeStudent(long id) {
        return students.remove(id);
    }

    @Override
    public Collection<Student> studentsByAge(int age) {
        return students.values()
                .stream()
                .filter(student -> student.getAge()==age).toList();
    }
}
