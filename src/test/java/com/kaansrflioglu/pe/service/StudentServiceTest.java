package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.model.Student;
import com.kaansrflioglu.pe.repository.StudentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private final StudentRepository repository = mock(StudentRepository.class);
    private final StudentService service = new StudentService(repository);

    @Test
    void testGetByIdFound() {
        Student student = Student.builder().id("1").name("Ali").build();
        when(repository.findById("1")).thenReturn(Optional.of(student));

        Student result = service.getById("1");
        assertEquals("Ali", result.getName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById("2"));
    }

    @Test
    void testUpdateStudent() {
        Student existing = Student.builder()
                .id("1")
                .name("Ali")
                .surname("Veli")
                .weight(70.0)
                .height(175.0)
                .preferredSports(List.of(new Sports()))
                .suitableSports(List.of(new Sports()))
                .parents(List.of(new Parent()))
                .build();

        Student updateData = Student.builder()
                .name("Ali Updated")
                .surname("Veli Updated")
                .weight(72.0)
                .height(176.0)
                .preferredSports(List.of(new Sports()))
                .suitableSports(List.of(new Sports()))
                .parents(List.of(new Parent()))
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Student.class))).thenReturn(updateData);

        Student result = service.update("1", updateData);
        assertEquals("Ali Updated", result.getName());
        assertEquals("Veli Updated", result.getSurname());
        assertEquals(72.0, result.getWeight());
        assertEquals(176.0, result.getHeight());
        assertEquals(1, result.getPreferredSports().size());
        assertEquals(1, result.getSuitableSports().size());
        assertEquals(1, result.getParents().size());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(repository).deleteById("1");
        service.delete("1");
        verify(repository, times(1)).deleteById("1");
    }
}