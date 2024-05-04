package ru.hogwarts.school.StudentControllerTests;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student = new Student();
    private  JSONObject studentObject = new JSONObject();

    @BeforeEach
    void setData() throws Exception {
        final Long studentId = 1L;
        final String name = "Harry Potter";
        final Integer age = 8;

        studentObject.put("id", studentId);
        studentObject.put("name", name);
        studentObject.put("age", age);

        student.setId(studentId);
        student.setName(name);
        student.setAge(age);
    }

       @Test
       void createStudentTest() throws Exception{
            when(studentRepository.save(any(Student.class))).thenReturn(student);
            mockMvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(studentObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(student.getId()))
                    .andExpect(jsonPath("$.name").value(student.getName()))
                    .andExpect(jsonPath("$.age").value(student.getAge()));
        }
        @Test
    void getStudentTest() throws Exception{
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/student/{id}", student.getId())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(student.getId()))
                    .andExpect(jsonPath("$.name").value(student.getName()))
                    .andExpect(jsonPath("$.age").value(student.getAge()));
        }
        @Test
    void updateStudentTest() throws Exception{
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/student/{id}", student.getId())
                    .content(studentObject.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }
     @Test
    void deleteStudentTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/student/{id}", student.getId()))
                .andExpect(status().isOk());

    }
    @Test
    void getStudentByAgeTest() throws Exception {
        Student student = new Student(1L, "Harry", 8);
        Student student2 = new Student(2L, "Ron", 8);
        List<Student> studentsByAge = List.of(student, student2);

        when(studentRepository.findAllByAge(8)).thenReturn(studentsByAge);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age_filter?age=8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentsByAge.size()));
    }
    @Test
    void getStudentBetweenAgeTest() throws Exception {
        Student student = new Student(1L, "Harry", 8);
        Student student2 = new Student(2L, "Ron", 8);
        List<Student> studentsByAge = List.of(student, student2);

        when(studentRepository.findByAgeBetween(7,9)).thenReturn(studentsByAge);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter_between_age?minAge=7&maxAge=9")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(studentsByAge.size()));
    }
    @Test
    void getFacultyForStudentTest() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setId(1L);
        faculty.setStudents(List.of(student));
        student.setFaculty(faculty);
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "Griffindor");

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()));

    }


    }


