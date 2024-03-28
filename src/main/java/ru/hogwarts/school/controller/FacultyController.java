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
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty){
        Faculty newFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(newFaculty);
    }
    @GetMapping("{facultyID}")
    public ResponseEntity<Faculty> getFacultyById (@PathVariable long facultyID){
        Faculty faculty = facultyService.getFaculty(facultyID);
        if (faculty==null){
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(faculty);
    }
    @PutMapping()
    public ResponseEntity <Faculty> updateFaculty (@RequestBody Faculty faculty){
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }
    @DeleteMapping("{facultyId}")
    public ResponseEntity<Faculty> deleteFaculty (@PathVariable long facultyId){
        Faculty deletedFaculty = facultyService.removeFaculty(facultyId);
        return ResponseEntity.ok(deletedFaculty);
    }
    @GetMapping("get/{color}")
    public ResponseEntity<Collection<Faculty>> getAllFacultyByColor (@PathVariable String color){
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

}
