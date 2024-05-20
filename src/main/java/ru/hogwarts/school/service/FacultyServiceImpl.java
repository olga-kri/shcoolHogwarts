package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {

        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Method addFaculty was invoiked");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Method getFaculty was invoiked");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            logger.error("Faculty by id={} not found",id);
            throw new FacultyNotFoundException("Faculty not found");
        }
        logger.debug("Faculty by id={} was founded",id);
        return faculty.get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty, Long id) {
        logger.info("Method updateFaculty was invoiked");
        Optional<Faculty> facultyById = facultyRepository.findById(id);
        if (facultyById.isEmpty()) {
            logger.warn("Faculty by id={} not found",id);
            throw new FacultyNotFoundException("Faculty not found");
        }
        logger.debug("Faculty by id={} was founded",id);
        return facultyRepository.save(faculty);
    }

    @Override
    public void  removeFaculty(Long id) {
        logger.info("Method removeFaculty was invoiked");
        facultyRepository.deleteById(id);
    }


    @Override
    public List<Faculty> findFacultyByNameOrColor(String facultyName, String facultyColor) {
        logger.info("Method findFacultyByNameOrColor was invoiked");
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(facultyName, facultyColor);
    }

    @Override
    public Collection<Student> getStudentsOnFaculty(Long facultyId) {
        logger.info("Method getStudentsOnFaculty was invoiked");
        Faculty faculty = getFaculty(facultyId);
        logger.debug("In method getStudentsOnFaculty find faculty:{}, by facultyId:{}", faculty, facultyId);
        return faculty.getStudents();
    }

    @Override
    public String getFacultyWithLongestName() {
        logger.info("Method getFacultyWithLongestName was invoiked");
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("Unknown");
    }
}
