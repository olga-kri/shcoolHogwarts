package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Map;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty updateFaculty(long id, Faculty faculty);
    Faculty removeFaculty(long id);

    Map<String, List<Faculty>> getFacultyByColor(String color);
}
