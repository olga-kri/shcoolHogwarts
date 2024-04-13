package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty getFaculty(Long id);
    Faculty updateFaculty(Faculty faculty, Long id);
    void removeFaculty(Long id);
    List<Faculty> findFacultyByNameOrColor(String facultyName, String facultyColor);
    Collection<Student> getStudentsOnFaculty (Long facultyId);
}
