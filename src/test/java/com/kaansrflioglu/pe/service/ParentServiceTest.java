package com.kaansrflioglu.pe.service;

import com.kaansrflioglu.pe.exception.ResourceNotFoundException;
import com.kaansrflioglu.pe.model.Parent;
import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.repository.ParentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParentServiceTest {

    private final ParentRepository repository = mock(ParentRepository.class);
    private final ParentService service = new ParentService(repository);

    @Test
    void testGetByIdFound() {
        Parent parent = Parent.builder().id("1").name("Ali").build();
        when(repository.findById("1")).thenReturn(Optional.of(parent));

        Parent result = service.getById("1");
        assertEquals("Ali", result.getName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById("2"));
    }

    @Test
    void testUpdateParent() {
        Parent existing = Parent.builder()
                .id("1")
                .name("Ali")
                .surname("Veli")
                .phone("1234567890")
                .height(180.0)
                .relation("Father")
                .sportsBackground(List.of(new Sports()))
                .build();

        Parent updateData = Parent.builder()
                .name("Ali Updated")
                .surname("Veli Updated")
                .phone("0987654321")
                .height(182.0)
                .relation("Mother")
                .sportsBackground(List.of(new Sports()))
                .build();

        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Parent.class))).thenReturn(updateData);

        Parent result = service.update("1", updateData);
        assertEquals("Ali Updated", result.getName());
        assertEquals("Veli Updated", result.getSurname());
        assertEquals("0987654321", result.getPhone());
        assertEquals(182.0, result.getHeight());
        assertEquals("Mother", result.getRelation());
        assertEquals(1, result.getSportsBackground().size());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(existing);
    }

    @Test
    void testDeleteParent() {
        doNothing().when(repository).deleteById("1");
        service.delete("1");
        verify(repository, times(1)).deleteById("1");
    }
}