package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("faculty")
public class FacultyController  {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {

        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById (@PathVariable Long id){
        return facultyService.getFaculty(id);
    }

    @PutMapping("/{id}")
    public Faculty updateFaculty (@RequestBody Faculty faculty, @PathVariable Long id){
        return facultyService.updateFaculty(faculty,id);
    }

    @DeleteMapping("/{facultyId}")
    public void deleteFaculty (@RequestParam Long facultyId){

        facultyService.removeFaculty(facultyId);
    }

    @GetMapping("/find")
    public List<Faculty> findFacultyByNameOrColor(@RequestParam (required = false)String facultyName,
                                                  @RequestParam (required = false)String facultyColor){
        return facultyService.findFacultyByNameOrColor(facultyName,facultyColor);
    }

    @GetMapping("/students")
    public Collection <Student>getStudentsByFacultyId (@RequestParam Long facultyId){
        return facultyService.getStudentsOnFaculty(facultyId);
    }


}
