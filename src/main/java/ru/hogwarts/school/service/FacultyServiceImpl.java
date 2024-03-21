package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    Map<Long, Faculty> faculties = new HashMap<>();
    long facultyId = 1L;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculties.put(facultyId, faculty);
        facultyId++;
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        Faculty faculty = faculties.get(id);
        return faculty;
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty removeFaculty(long id) {
        if (faculties.containsKey(id)) {
            Faculty removeFaculty = faculties.get(id);
            faculties.remove(id);
            return removeFaculty;
        } else throw new RuntimeException("Факультет не может быть удален, так как его не существует");
    }

    @Override
    public Map<String, List<Faculty>> getFacultyByColor(String color) {
        return faculties.values()
                .stream()
                .collect(Collectors.groupingBy(Faculty::getColor));
    }
}
