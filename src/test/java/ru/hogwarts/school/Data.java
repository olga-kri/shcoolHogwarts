package ru.hogwarts.school;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static final Faculty faculty1 = new Faculty(1L, "Гриффиндор", "красный");
    public static final Faculty faculty2 = new Faculty(2L, "Ппуфедуй", "красный");
    public static final Faculty updateFaculty = new Faculty(1L, "Гриффиндор", "золотой");
    public static final Student student1 = new Student(1L, "Harry Potter", 7);
    public static final Student student2 = new Student(2L, "Hermiona Greindger", 7);
    public static final Student student3 = new Student(3L, "Fred Uizly", 9);
    public static final Student updateStudent = new Student(1L, "Harry Potter", 10);

    public static final List<Student> studentsOnFaculty = new ArrayList<>(List.of(student1, student2, student3));
    public static final List<Student> studentWithSameAge = new ArrayList<>(List.of(student1, student2));
    public static final List<Faculty> facultyWithSameColor = new ArrayList<>(List.of(faculty1, faculty2));
    public static final List<Faculty> facultyWithSameName = new ArrayList<>(List.of(faculty1, updateFaculty));

}
