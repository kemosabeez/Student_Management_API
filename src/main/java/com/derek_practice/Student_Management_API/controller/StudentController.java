package com.derek_practice.Student_Management_API.controller;


import com.derek_practice.Student_Management_API.dto.StudentRequest;
import com.derek_practice.Student_Management_API.dto.StudentResponse;
import com.derek_practice.Student_Management_API.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public List<StudentResponse> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable int id) {
        return studentService.getById(id);
    }

    @PostMapping
    public StudentResponse addStudent(@RequestBody StudentRequest request) {
        return studentService.addStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable int id,
            @RequestBody StudentRequest request
    ) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok("Delete Successful");
    }
}
