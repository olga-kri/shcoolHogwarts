package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    Map<Long, Student> students = new HashMap<>();
    long studentId = 1L;
    @Override
    public Student addStudent(Student student) {
        students.put(studentId,student);
        studentId++;
        return student;
    }
    @Override
    public Student getStudent(long id) {
        Student findStudent = students.get(id);
        return findStudent;
    }
    @Override
    public Student updateStudent(long id, Student student) {
        students.put(id, student);
        return student;
    }

    @Override
    public Student removeStudent(long id) {
        if (students.containsValue(id)){
            Student removeStudent = students.get(id);
            students.remove(id);
            return removeStudent;
        } else throw new RuntimeException("Данный студент не числится в школе");
    }

    @Override
    public Map<Integer, List<Student>> studentsByAge(int age) {
        return students.values()
                .stream()
                .collect(Collectors.groupingBy(Student::getAge));
    }
}
