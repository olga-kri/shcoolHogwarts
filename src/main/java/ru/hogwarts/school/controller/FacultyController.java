package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public Faculty getFacultyById (@PathVariable long id){
        return facultyService.getFaculty(id);

    }
    @PutMapping("/{id}")
    public Faculty updateFaculty (@RequestBody Faculty faculty, @PathVariable Long id){
        return facultyService.updateFaculty(faculty,id);
    }
    @DeleteMapping("{facultyId}")
    public void deleteFaculty (@RequestParam long facultyId){
          facultyService.removeFaculty(facultyId);
    }
    @GetMapping("/filter/{color}")
    public List<Faculty>getAllFacultyByColor (@PathVariable String color){
        return facultyService.getFacultyByColor(color);
    }

}
