package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private long facultyId = 1L;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(facultyId);
        faculties.put(facultyId, faculty);
        facultyId++;
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())){
            return null;
        }
            faculties.put(faculty.getId(),faculty);
        return faculty;
    }

    @Override
    public Faculty removeFaculty(long id) {
            return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
