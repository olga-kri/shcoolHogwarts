package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public Student createStudent (@RequestBody Student student){
        return studentService.addStudent(student);
    }
    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentById (@PathVariable long studentId){
        Student student = studentService.getStudent(studentId);
        if (student == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent (@RequestBody Student student){
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> deleteStudent (@PathVariable long studentId){
        return ResponseEntity.ok(studentService.removeStudent(studentId));
    }
    @GetMapping("get/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge (@PathVariable int age){
        return ResponseEntity.ok(studentService.studentsByAge(age));
    }
}
