package org.lnu.teaching.web.application.design.deanery.service.faculty.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.BaseFacultyDto;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.FacultyDto;
import org.lnu.teaching.web.application.design.deanery.entity.faculty.FacultyEntity;
import org.lnu.teaching.web.application.design.deanery.mapper.FacultyMapper;
import org.lnu.teaching.web.application.design.deanery.repository.faculty.FacultyRepository;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private FacultyMapper facultyMapper;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    public void createExpectFacultyIsCreated() {
        // Given
        FacultyDto expectedDto = new FacultyDto();
        expectedDto.setId(147L);

        // When
        BaseFacultyDto baseFacultyDto = new BaseFacultyDto();

        FacultyEntity mappedFacultyEntity = new FacultyEntity();
        FacultyEntity createdFacultyEntity = new FacultyEntity();

        when(facultyMapper.toEntity(baseFacultyDto)).thenReturn(mappedFacultyEntity);
        when(facultyRepository.create(mappedFacultyEntity)).thenReturn(createdFacultyEntity);
        when(facultyMapper.toDto(createdFacultyEntity)).thenReturn(expectedDto);

        FacultyDto actualDto = facultyService.create(baseFacultyDto);

        // Then
        assertEquals(expectedDto, actualDto);

        InOrder inOrder = inOrder(facultyRepository, facultyMapper);
        inOrder.verify(facultyMapper).toEntity(baseFacultyDto);
        inOrder.verify(facultyRepository).create(mappedFacultyEntity);
        inOrder.verify(facultyMapper).toDto(createdFacultyEntity);
        inOrder.verifyNoMoreInteractions();
    }
}
