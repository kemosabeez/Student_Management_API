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

    private StudentRequest createRequest(int id, String name,
                                         String course, int yearLevel) {
        StudentRequest request = new StudentRequest();
        request.setId(id);
        request.setName(name);
        request.setCourse(course);
        request.setYearLevel(yearLevel);
        return request;
    }

    @Test
    void addStudent_shouldReturnStudentResponse_whenRequestIsValid() {
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
    void addStudent_shouldThrowDuplicateStudentException_whenIdAlreadyExists() {
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        StudentRequest duplicate = createRequest(
                1, "Derek",
                "BS ECE", 4
        );

        studentService.addStudent(request);

        DuplicateStudentException exception = assertThrows(
                DuplicateStudentException.class,
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
    void getById_shouldReturnStudentResponse_whenStudentExists() {
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
    void getById_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
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
    void getAll_shouldReturnEmptyList_whenNoStudentsExist() {
        List<StudentResponse> response = studentService.getAll();

        assertTrue(response.isEmpty());
    }

    @Test
    void getAll_shouldReturnAllStudents_whenStudentsExist() {
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
    void updateStudent_shouldReturnUpdatedStudentResponse_whenStudentExists() {
        StudentRequest request = createRequest(
                1, "Derek",
                "BS ECE", 4
        );
        studentService.addStudent(request);
        StudentRequest update = createRequest(
                99, "Althea",
                "BS CE", 3);

        StudentResponse response = studentService.updateStudent(1, update);

        assertEquals(1, response.getId());
        assertEquals("Althea", response.getName());
        assertEquals("BS CE", response.getCourse());
        assertEquals(3, response.getYearLevel());

    }

    @Test
    void updateStudent_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {
        StudentRequest update = createRequest(
                3, "Althea",
                "BS CE", 3);

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
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
    void deleteStudent_shouldReturnTrue_whenStudentExists() {
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
    void deleteStudent_shouldThrowStudentNotFoundException_whenStudentDoesNotExist() {

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> {
                    studentService.deleteStudent(999);
                });

        assertEquals(HttpStatus.NOT_FOUND.value(),
                exception.getErrorId());
        assertEquals("Student does not exist",
                exception.getErrorMessage());
        assertTrue(exception.getErrorDetails().isEmpty());
    }

    @Test
    void deleteStudent_shouldOnlyRemoveTargetStudent_whenMultipleStudentsExist() {
        StudentRequest student1 = createRequest(1, "Derek",
                "BS ECE", 4);
        StudentRequest student2 = createRequest(2, "Althea",
                "BS CE", 3);

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        boolean result = studentService.deleteStudent(1);

        List<StudentResponse> students = studentService.getAll();

        assertTrue(result);
        assertEquals(1, students.size());
        assertEquals(2, students.get(0).getId());
        assertEquals("Althea", students.get(0).getName());
    }

    @Test
    void addStudent_shouldAllowSameId_whenPreviousStudentWasDeleted() {
        StudentRequest first = createRequest(1, "Derek",
                "BS ECE", 4);
        studentService.addStudent(first);
        studentService.deleteStudent(1);

        StudentRequest second = createRequest(1, "Althea",
                "BS CE", 3);

        StudentResponse response = studentService.addStudent(second);

        assertEquals(1, response.getId());
        assertEquals("Althea", response.getName());
        assertEquals("BS CE", response.getCourse());
        assertEquals(3, response.getYearLevel());
    }

    @Test
    void updateStudent_shouldUpdateStoredStudent_whenStudentExists() {

        StudentRequest original = createRequest(1, "Derek",
                "BS ECE", 4);
        studentService.addStudent(original);

        StudentRequest update = createRequest(1, "Derek Updated",
                "BS IT", 5);

        studentService.updateStudent(1, update);
        StudentResponse storedStudent = studentService.getById(1);

        assertEquals(1, storedStudent.getId());
        assertEquals("Derek Updated", storedStudent.getName());
        assertEquals("BS IT", storedStudent.getCourse());
        assertEquals(5, storedStudent.getYearLevel());
    }

    @Test
    void deleteStudent_shouldRemoveStoredStudent_whenStudentExists() {
        StudentRequest request = createRequest(1, "Derek", "BS ECE", 4);
        studentService.addStudent(request);

        studentService.deleteStudent(1);
        
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getById(1);
        });
    }
}
