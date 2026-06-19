package com.derek_practice.Student_Management_API.service.impl;

import com.derek_practice.Student_Management_API.dto.StudentRequest;
import com.derek_practice.Student_Management_API.dto.StudentResponse;
import com.derek_practice.Student_Management_API.exceptions.DuplicateStudentException;
import com.derek_practice.Student_Management_API.exceptions.StudentNotFoundException;
import com.derek_practice.Student_Management_API.model.Student;
import com.derek_practice.Student_Management_API.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(
            StudentServiceImpl.class
    );

    private final List<Student> students = new ArrayList<>();

    @Override
    public List<StudentResponse> getAll() {
        logger.info("getAll triggered");
        List<StudentResponse> responses = new ArrayList<>();

        for (Student student : students) {
            StudentResponse response = mapToResponse(student);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public StudentResponse getById(int id) {
        logger.info("getById triggered");


        for (Student student : students) {

            if (student.getId() == id) {
                return mapToResponse(student);
            }

        }
        logger.error("StudentNotFound in getByID triggered");
        throw new StudentNotFoundException(
                HttpStatus.NOT_FOUND.value(),
                "Student does not exist",
                new HashMap<>()
        );
    }

    @Override
    public StudentResponse addStudent(StudentRequest request) {
        logger.info("addStudent triggered");

        for (Student student : students) {
            if (student.getId() == request.getId()) {
                logger.error("DuplicateStudent in addStudent triggered");
                throw new DuplicateStudentException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Duplicate Student",
                        new HashMap<>()
                );
            }
        }

        Student student = mapToStudent(request);
        students.add(student);
        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(int id, StudentRequest request) {
        logger.info("updateStudent triggered");


        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getId() == id) {

                Student student = mapToStudent(request);
                student.setId(id);
                students.set(i, student);
                return mapToResponse(student);
            }

        }
        logger.error("StudentNotFound in updateStudent triggered");
        throw new StudentNotFoundException(
                HttpStatus.NOT_FOUND.value(),
                "Student does not exist",
                new HashMap<>()
        );

    }

    @Override
    public Boolean deleteStudent(int id) {
        logger.info("deleteStudent triggered");
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i);
                return true;
            }
        }
        logger.error("StudentNotFound in deleteStudent triggered");
        throw new StudentNotFoundException(
                HttpStatus.NOT_FOUND.value(),
                "Student does not exist",
                new HashMap<>()
        );
    }

    private StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setCourse(student.getCourse());
        response.setYearLevel(student.getYearLevel());
        return response;
    }

    private Student mapToStudent(StudentRequest request) {
        Student student = new Student();
        student.setId(request.getId());
        student.setName(request.getName());
        student.setCourse(request.getCourse());
        student.setYearLevel(request.getYearLevel());
        return student;
    }

}
