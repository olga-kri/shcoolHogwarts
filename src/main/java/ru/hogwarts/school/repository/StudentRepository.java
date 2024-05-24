package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(Integer age);

    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT (*) FROM Student", nativeQuery = true)
    Integer countOfStudents();

    @Query(value="SELECT AVG (age) FROM Student", nativeQuery = true)
    Double findAverageAge();

    @Query(value="SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFive();

}
