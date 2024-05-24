package ru.hogwarts.school.StudentControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;


import  org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    List<Student> studentsInDatabase;

    @BeforeEach
    void setData() {
        studentRepository.deleteAll();
        Student student1 = new Student(null, "Harry Potter", 8);
        Student student2 = new Student(null, "Ron Uizly", 8);
        List<Student> students = List.of(student1,student2);
        studentsInDatabase = studentRepository.saveAll(students);
    }
    @Test
    public void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void getStudent() throws Exception {

       Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/", Student.class))
                .isNotNull();
    }
    @Test
    public void getStudentByIdTest() throws Exception {
        Long studentId = studentsInDatabase.get(0).getId();
        String expected = objectMapper.writeValueAsString(studentsInDatabase.get(0));

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+port+"/student/"+studentId, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getBody()).isEqualTo(expected);
    }
    @Test
    public void createStudentTest()  {
        Student addStudent = new Student(null, "Germione Greindger", 8);

        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:"+port+"/student",addStudent, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getBody()).contains("Germione Greindger");
    }
    @Test
    public void getStudentsTest() throws Exception {
        ResponseEntity<List<Student>> response = restTemplate
                .exchange("http://localhost:" +port+ "/student/age_filter?age=8",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Student>>() {} );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
                Assertions.assertThat(response.getBody()).isEqualTo(studentsInDatabase);
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long studentId = studentsInDatabase.get(0).getId();
        Student updateStudent = new Student(studentId, "Tom Reddl", 9);
        HttpEntity<Student> request = new HttpEntity<>(updateStudent);
        String expected = objectMapper.writeValueAsString(updateStudent);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/student/"+studentId, HttpMethod.PUT, request, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long studentId = studentsInDatabase.get(0).getId();

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/student/"+studentId, HttpMethod.DELETE, null, String.class );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void getFacultyTest() throws Exception {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        Faculty faculty = new Faculty(null, "Griffindor", "red");
        Faculty savedFaculty = facultyRepository.save(faculty);
        Student student1 = new Student(null, "Harry Potter", 8);
        Student student2 = new Student(null, "Ron Uizly", 8);
        student1.setFaculty(savedFaculty);
        student2.setFaculty(savedFaculty);
        List<Student> students = List.of(student1,student2);
        List<Student> savedStudent = studentRepository.saveAll(students);
        Long studentId = savedStudent.get(0).getId();

        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:" +port+ "/student/faculty/"+studentId, HttpMethod.GET,
                        null, String.class );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getBody()).contains("Griffindor");
    }


}
