package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentDontFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.Data.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiseImplTest {
    private final StudentRepository repositoryMock = mock(StudentRepository.class);
    @InjectMocks
    private StudentServiceImpl out;


    @Test
    void addStudentAfterSave() {
        when(repositoryMock.save(new Student(1L, "Harry Potter", 7))).thenReturn(student1);
        Assertions.assertEquals(student1, out.addStudent(new Student(1L, "Harry Potter", 7)));
    }

    @Test
    public void getStudentById() {
        when(repositoryMock.findById(1L)).thenReturn(Optional.ofNullable(student1));
        Assertions.assertEquals(student1, out.getStudent(1L));
    }
    @Test
    public void getStudentByIdIfHeIsAbsence() {
        when(repositoryMock.findById(4L)).thenThrow(StudentDontFoundException.class);
        Assertions.assertThrows(StudentDontFoundException.class,()-> out.getStudent(4L));
    }
    @Test
    public void editStudentIfHeIsPresent() {
        when(repositoryMock.findById(1L)).thenReturn(Optional.ofNullable(student1));
        when(repositoryMock.save(new Student(1L, "Harry Potter", 8))).thenReturn(updateStudent);
        Assertions.assertEquals(updateStudent, out.updateStudent(new Student(1L, "Harry Potter", 8),1L));
    }
    @Test
    public void editStudentByIdIfHeIsAbsence() {
        when(repositoryMock.save(new Student(5L, "Ron Uizly", 7))).thenThrow(StudentDontFoundException.class);
        Assertions.assertThrows(StudentDontFoundException.class,()-> out.updateStudent(new Student(5L, "Ron Uizly", 7),  5L));
    }
    @Test
    public void findStudentsWithTheSameAge(){
        when(repositoryMock.findAllByAge(7)).thenReturn(studentWithSameAge);
        Assertions.assertEquals(studentWithSameAge, out.studentsByAge(7));
    }
    @Test
    public void findStudentsWithAgeBetween(){
        when(repositoryMock.findByAgeBetween(6,8)).thenReturn(studentWithSameAge);
        Assertions.assertEquals(studentWithSameAge, out.findStudentByAgeBetweenMinAgeAndMaxAge(6,8));

    }
}