package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
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
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable Long id) {
        return studentService.updateStudent(student, id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
    }

    @GetMapping("/age_filter")
    public List<Student> getStudentsByAge(@RequestParam Integer age) {
        return studentService.studentsByAge(age);

    }

    @GetMapping("/filter_between_age")
    public List<Student> getStudentsBetweenAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return studentService.findStudentByAgeBetweenMinAgeAndMaxAge(minAge, maxAge);
    }

    @GetMapping("/faculty/{id}")
    public Faculty getFacultyForStudent(@PathVariable Long id) {
        return studentService.findFacultyForStudent(id);
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> countOfStudents() {
        return ResponseEntity.ok(studentService.findCountOfStudent());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> averageAgeOfStudents() {
        return ResponseEntity.ok(studentService.findAveragAge());
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> lastFiveStudents() {
        return ResponseEntity.ok(studentService.findLastFiveStudents());
    }

    @GetMapping("/name-filter")
    public List<String> studentsWithNamesStartingWithA() {
        return studentService.getStudentsWithNameStartingWithA();
    }

    @GetMapping("/average-age-with-streams")
    public Double averageAgeOfStudentsWithStreams() {
        return studentService.getAverageAgeOfStudentsWithStreams();
    }
    @GetMapping("/GET/students/print-parallel")
    public void printStudentsParallel() throws InterruptedException {
        studentService.printParallel();
    }

    @GetMapping("GET/students/print-synchronized")
    public void printStudentsSynchronized() throws InterruptedException {
        studentService.printSynchronized();
    }
}
