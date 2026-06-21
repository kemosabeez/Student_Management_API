package com.derek_practice.Student_Management_API.service.impl;

import com.derek_practice.Student_Management_API.dto.StudentRequest;
import com.derek_practice.Student_Management_API.dto.StudentResponse;
import com.derek_practice.Student_Management_API.exceptions.DuplicateStudentException;
import com.derek_practice.Student_Management_API.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceImplTest {

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
    }

    private StudentRequest createRequest(int id, String name, String course, int yearLevel) {
        StudentRequest request = new StudentRequest();
        request.setId(id);
        request.setName(name);
        request.setCourse(course);
        request.setYearLevel(yearLevel);
        return request;
    }

    @Test
    void addStudent_success() {
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );

        StudentResponse response = studentService.addStudent(request);

        assertEquals(1, response.getId());
        assertEquals("Derek", response.getName());
        assertEquals(4, response.getYearLevel());
        assertEquals("BS ECE", response.getCourse());

    }

    @Test
    void addStudent_duplicate() {
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        StudentRequest duplicate = createRequest(
                1, "Derek",
                "BS ECE", 4
        );

        studentService.addStudent(request);

        DuplicateStudentException exception = assertThrows(DuplicateStudentException.class,
                () -> {
                    studentService.addStudent(duplicate);
                });


        assertEquals(HttpStatus.BAD_REQUEST.value(),
                exception.getErrorId());
        assertEquals("Duplicate Student",
                exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }

    @Test
    void getByStudentId_success() {
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        studentService.addStudent(request);

        StudentResponse response = studentService.getById(1);

        assertEquals("Derek", response.getName());
        assertEquals("BS ECE", response.getCourse());
    }

    @Test
    void getByStudentId_notFound() {

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class,
                () -> {
                    studentService.getById(999);
                });

        assertEquals(HttpStatus.NOT_FOUND.value(),
                exception.getErrorId());
        assertEquals("Student does not exist",
                exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }

    @Test
    void getAll_empty() {
        List<StudentResponse> response = studentService.getAll();

        assertTrue(response.isEmpty());
    }

    @Test
    void getAll_notEmpty() {
        StudentRequest request1 = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        StudentRequest request2 = createRequest(
                2, "Althea",
                "BS CE", 3
        );

        studentService.addStudent(request1);
        studentService.addStudent(request2);

        List<StudentResponse> response = studentService.getAll();

        assertEquals(2, response.size());
    }

    @Test
    void updateStudent_success(){
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        studentService.addStudent(request);
        StudentRequest update = createRequest(
                3, "Althea",
                "BS CE", 3);

        StudentResponse response =  studentService.updateStudent(1, update);

        assertEquals(1,response.getId());
        assertEquals("Althea", response.getName());

    }

    @Test
    void updateStudent_notFound(){
        StudentRequest update = createRequest(
                3, "Althea",
                "BS CE", 3);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class,
                () -> {
                    studentService.updateStudent(1, update);
                });

        assertEquals(HttpStatus.NOT_FOUND.value(),
                exception.getErrorId());
        assertEquals("Student does not exist",
                exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }

    @Test
    void deleteStudent_success(){
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        studentService.addStudent(request);
        Boolean state = studentService.deleteStudent(1);

        assertTrue(state);
        assertTrue(studentService.getAll().isEmpty());
    }

    @Test
    void deleteStudent_notFound(){

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class,
                () -> {
                    studentService.deleteStudent(999);
                });

        assertEquals(HttpStatus.NOT_FOUND.value(),
                exception.getErrorId());
        assertEquals("Student does not exist",
                exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }
}
