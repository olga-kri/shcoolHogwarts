package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty updateFaculty(Faculty faculty);
    Faculty removeFaculty(long id);

    Collection<Faculty> getFacultyByColor(String color);
}
