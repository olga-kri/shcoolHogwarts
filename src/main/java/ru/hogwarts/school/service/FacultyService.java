package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty updateFaculty(Faculty faculty, Long id);
    void removeFaculty(long id);

    List<Faculty> getFacultyByColor(String color);
}
