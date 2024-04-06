package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
       return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty, Long id) {
        facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        return facultyRepository.save(faculty);
    }

    @Override
    public void  removeFaculty(Long id) {
            facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getFacultyByColor(String color) {

        return facultyRepository.findAllByColor(color);
    }
}
