package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<Student> createStudent (@RequestBody Student student){
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }
    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentById (@PathVariable long studentId){
        Student student = studentService.getStudent(studentId);
        if (student == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @PutMapping
    public ResponseEntity<Student> updateStudent (@RequestBody Student student){
        Student updatedStudent = studentService.updateStudent(student.getId(),student);
        if (updatedStudent == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> deleteStudent (@PathVariable long studentId){
        Student deletedStudent = studentService.removeStudent(studentId);
        return ResponseEntity.ok(deletedStudent);
    }
    @GetMapping("get/{age}")
    public ResponseEntity<Map<Integer, List<Student>>> getStudentsByAge (@PathVariable int age){
        return ResponseEntity.ok(studentService.studentsByAge(age));
    }
}
