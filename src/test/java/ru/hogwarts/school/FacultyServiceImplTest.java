package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentDontFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {
    @Mock
    FacultyRepository repositoryMock;
    private FacultyServiceImpl out;
    @BeforeEach
    public void initOut(){
        out = new FacultyServiceImpl(repositoryMock);
    }
    Faculty faculty1 = new Faculty(1L, "Гриффиндор", "красный");
    Faculty faculty2 = new Faculty(2L, "Ппуфедуй", "красный");
    Faculty faculty3 = new Faculty(3L, "Слизерин", "зеленый");
    Faculty updateFaculty = new Faculty(1L, "Гриффиндор", "золотой");

    List<Faculty> facultyWithSameColor = new ArrayList<>(List.of(faculty1, faculty2));

    @Test
    void addFacultyAfterSave() {
        when(repositoryMock.save(new Faculty(1L, "Гриффиндор", "красный"))).thenReturn(faculty1);
        Assertions.assertEquals(faculty1, out.addFaculty(new Faculty(1L, "Гриффиндор", "красный")));
    }

    @Test
    public void getFacultyById() {
        when(repositoryMock.findById(1L)).thenReturn(Optional.ofNullable(faculty1));
        Assertions.assertEquals(faculty1, out.getFaculty(1L));
    }
    @Test
    public void getFacultyByIdIfHeIsAbsence() {
        when(repositoryMock.findById(4L)).thenThrow(FacultyNotFoundException.class);
        Assertions.assertThrows(FacultyNotFoundException.class,()-> out.getFaculty(4L));
    }
    @Test
    public void editFacultyIfHeIsPresent() {
        when(repositoryMock.findById(1L)).thenReturn(Optional.ofNullable(faculty1));
        when(repositoryMock.save(new Faculty(1L, "Гриффиндор", "золотой"))).thenReturn(updateFaculty);
        Assertions.assertEquals(updateFaculty, out.updateFaculty(new Faculty(1L, "Гриффиндор", "золотой"),1L));
    }
    @Test
    public void editFacultyByIdIfHeIsAbsence() {
        when(repositoryMock.findById(5L)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(FacultyNotFoundException.class,()-> out.updateFaculty(new Faculty(5L, "Когтевран", "синий"),  5L));
    }
    @Test
    public void findFacultiesWithTheSameColor(){
        when(repositoryMock.findAllByColor("красный")).thenReturn(facultyWithSameColor);
        Assertions.assertEquals(facultyWithSameColor, out.getFacultyByColor("красный"));
    }
}