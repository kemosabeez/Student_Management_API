package com.derek_practice.Student_Management_API.service;

import com.derek_practice.Student_Management_API.dto.StudentRequest;
import com.derek_practice.Student_Management_API.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    List<StudentResponse> getAll();

    StudentResponse getById(int id);

    StudentResponse addStudent(StudentRequest request);

    StudentResponse updateStudent(int id, StudentRequest request);

    Boolean deleteStudent(int id);


}
