package ru.hogwarts.school.FacultyControllerTests;

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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentService studentService;

    @MockBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private final List<Faculty> facultyList = new ArrayList<>();

    private JSONObject jsonObjectFaculty = new JSONObject();
    private Faculty faculty1 = new Faculty();

    @BeforeEach
    public void setUp() throws Exception {
        final Long facultyId = 1L;
        final String name = "Griffindor";
        final String color = "red";


        faculty1.setId(facultyId);
        faculty1.setName(name);
        faculty1.setColor(color);
        jsonObjectFaculty.put("facultyId", facultyId);
        jsonObjectFaculty.put("name", name);
        jsonObjectFaculty.put("color", color);
    }

    @Test
    public void postFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObjectFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", faculty1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    public void putFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty1));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/{id}", faculty1.getId())
                        .content(jsonObjectFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty1.getId()))
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findFacultybyNameOrColorTest() throws Exception {

        List<Faculty> findFaculties = new ArrayList<>(List.of(
                new Faculty(2L,"Slitherin","green"),
                new Faculty(3L,"Phuphendui","yellow")));

        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase("Slitherin", "yellow"))
                .thenReturn(findFaculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find?name=Slitherin&color=yellow")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(findFaculties.size()));
    }
    @Test
    void getAllStudentByFacultyTest() throws Exception {
        List<Student> list = new ArrayList<>(List.of(
                new Student(1L, "Harry", 7),
                new Student(2L, "Ron", 7),
                new Student(3L, "Germione", 7)
        ));

        faculty1.setStudents(list);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students?facultyId=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(faculty1.getStudents().size()));


    }
}

