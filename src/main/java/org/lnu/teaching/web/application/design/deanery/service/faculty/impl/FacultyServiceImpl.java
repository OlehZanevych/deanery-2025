package org.lnu.teaching.web.application.design.deanery.service.faculty.impl;

import lombok.extern.java.Log;
import org.lnu.teaching.web.application.design.deanery.annotation.TrackExecution;
import org.lnu.teaching.web.application.design.deanery.dto.common.ValueDto;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.BaseFacultyDto;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.FacultyDto;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.FacultyPatch;
import org.lnu.teaching.web.application.design.deanery.dto.faculty.query.params.FacultyFilterOptions;
import org.lnu.teaching.web.application.design.deanery.entity.faculty.FacultyEntity;
import org.lnu.teaching.web.application.design.deanery.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.lnu.teaching.web.application.design.deanery.mapper.FacultyMapper;
import org.springframework.stereotype.Service;
import org.lnu.teaching.web.application.design.deanery.repository.faculty.FacultyRepository;
import org.lnu.teaching.web.application.design.deanery.service.faculty.FacultyService;

import java.util.List;

@Log
@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Override
    public FacultyDto create(BaseFacultyDto facultyDto) {
        FacultyEntity facultyEntity = facultyMapper.toEntity(facultyDto);
        FacultyEntity createFacultyEntity = facultyRepository.create(facultyEntity);
        return facultyMapper.toDto(createFacultyEntity);
    }
    
    @Override
    @TrackExecution
    public List<FacultyDto> findAll(FacultyFilterOptions filterOptions, Integer limit, Integer offset) {
        log.info("Inside findAll method!");
        List<FacultyEntity> facultyEntities = facultyRepository.findAll(filterOptions, limit, offset);
        return facultyMapper.toDtoList(facultyEntities);
    }

    @Override
    public ValueDto<Integer> count(FacultyFilterOptions filterOptions) {
        int count = facultyRepository.count(filterOptions);
        return new ValueDto<>(count);
    }

    @Override
    @TrackExecution(isExecutionTimeEnabled = false)
    public FacultyDto find(Long id) {
        log.info("Inside find method!");
        FacultyEntity facultyEntity = facultyRepository.find(id);
        return facultyMapper.toDto(facultyEntity);
    }

    @Override
    public void update(Long id, BaseFacultyDto facultyDto) {
        FacultyEntity facultyEntity = facultyMapper.toEntity(facultyDto);
        facultyEntity.setId(id);

        facultyRepository.update(facultyEntity);
    }

    @Override
    public void patch(Long id, FacultyPatch facultyPatch) {
        if (facultyPatch.isEmpty()) {
            throw new BadRequestException("Faculty patch is empty!");
        }

        facultyRepository.patch(id, facultyPatch);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.delete(id);
    }
}
