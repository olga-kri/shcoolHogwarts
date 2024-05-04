package ru.hogwarts.school.FacultyControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    List<Faculty> facultyInDatabase = new ArrayList<>();

    @BeforeEach
    void setUp() {
        facultyRepository.deleteAll();
        Faculty faculty = new Faculty(1L, "Griffindor", "red");
        Faculty faculty2 = new Faculty(2L, "Slitherin", "green");
        List<Faculty> faculties = List.of(faculty, faculty2);
        facultyInDatabase = facultyRepository.saveAll(faculties);
    }

    @Test
    public void contextLoads() {}

    @Test
    public void createFacultyTest() {
        Faculty addFaculty = new Faculty(null, "Kogtevran", "blue");

        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:"+port+"/faculty", addFaculty,String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        Assertions.assertThat(response.getBody()).contains("Kogtevran");
    }

    @Test
    public void getFacultyTest() throws Exception {
        Long id = facultyInDatabase.get(0).getId();
        String control = objectMapper.writeValueAsString(facultyInDatabase.get(0));

        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:"+port+"/faculty/"+id,HttpMethod.GET,null,String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        Assertions.assertThat(response.getBody()).isEqualTo(control);
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Long id = facultyInDatabase.get(0).getId();
        Faculty updateFaculty = new Faculty(null, "Griffindor", "gold");
        HttpEntity request = new HttpEntity<>(updateFaculty);

        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:"+port+"/faculty/"+id,HttpMethod.PUT, request, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        Assertions.assertThat(response.getBody()).contains("gold");
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = facultyInDatabase.get(0).getId();
        Assertions
                .assertThat(this.restTemplate.exchange(
                        "http://localhost:"+port+"/faculty?id="+ id, HttpMethod.DELETE, null, String.class)
                        .getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void getFacultyByNameTest() throws Exception {
        ResponseEntity<String> response = restTemplate
                .exchange("http://localhost:"+port+"/faculty/find?color=red", HttpMethod.GET, null, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        Assertions.assertThat(response.getBody()).contains("red");
    }
    @Test                        // Ошибка в 133 строке - возвращает пустой список
    public void getStudentsByFacultyTest() throws Exception {
        Long id = facultyInDatabase.get(0).getId();
        Faculty faculty = facultyInDatabase.get(0);
        studentRepository.deleteAll();
        List<Student> students = List.of(new Student(null, "Harry Potter", 8),new Student(null, "Ron Uizly", 8));
        List<Student> studentsInDatabase = studentRepository.saveAll(students);
        faculty.setStudents(studentsInDatabase);
        facultyRepository.save(faculty);

        ResponseEntity<List<Student>> response = restTemplate
                        .exchange("http://localhost:" +port+ "/faculty/students?facultyId="+ id,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Student>>() {} );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getBody()).isEqualTo(studentsInDatabase);
    }
}
