package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentDontFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method - addStudent");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method - getStudent");
        Optional<Student> studentById = studentRepository.findById(id);
        if (studentById.isEmpty()) {
            logger.error("Student by id={} not found",id);
            throw new StudentDontFoundException("Student not found");
        }
        logger.debug("Student by id={} was founded",id);
        return studentById.get();
    }
    @Override
    public Student updateStudent(Student student, Long id) {
        logger.info("Was invoked method - updateStudent");
        Optional<Student> studentById = studentRepository.findById(id);
        if (studentById.isEmpty()) {
            logger.error("Student by id={} not found",id);
            throw new StudentDontFoundException("Student not found");
        }
        logger.debug("In method updateStudent find student:{}, by studentId:{}", student, id);
        return studentRepository.save(student);
    }
    @Override
    public void removeStudent(Long id) {
        logger.info("Was invoked method - removeStudent");
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> studentsByAge(Integer age) {
        logger.info("Was invoked method - studentsByAge");
        return studentRepository.findAllByAge(age);
    }

    @Override
    public List<Student> findStudentByAgeBetweenMinAgeAndMaxAge(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method - findStudentByAgeBetweenMinAgeAndMaxAge");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty findFacultyForStudent(Long id) {
        logger.info("Was invoked method - findFacultyForStudent");
        Optional<Student> studentById = studentRepository.findById(id);
        if (studentById.isEmpty()) {
            logger.error("Student by id={} not found",id);
            throw new StudentDontFoundException("Student not found");
        }
        logger.debug("In method findFacultyForStudent find student:{}, by studentId:{}", studentById, id);
        return studentById.get().getFaculty();
    }

    @Override
    public Integer findCountOfStudent() {
        logger.info("Was invoked method - findCountOfStudent");
        return studentRepository.countOfStudents();
    }

    @Override
    public Double findAveragAge() {
        logger.info("Was invoked method - findAveragAge");
        return studentRepository.findAverageAge();
    }

    @Override
    public List<Student> findLastFiveStudents() {
        logger.info("Was invoked method - findLastFiveStudents");
        return studentRepository.findLastFive();
    }


}
