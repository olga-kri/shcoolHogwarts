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

    @DeleteMapping
    public void deleteFaculty (@RequestParam Long id){

        facultyService.removeFaculty(id);
    }

    @GetMapping("/find")
    public List<Faculty> findFacultyByNameOrColor(@RequestParam (required = false)String name,
                                                  @RequestParam (required = false)String color){
        return facultyService.findFacultyByNameOrColor(name,color);
    }

    @GetMapping("/students")
    public Collection <Student>getStudentsByFacultyId (@RequestParam Long id){
        return facultyService.getStudentsOnFaculty(id);
    }

    @GetMapping("/faculty-with-longest-name")
    public String facultyWithLongestName(){
        return facultyService.getFacultyWithLongestName();
    }


}
