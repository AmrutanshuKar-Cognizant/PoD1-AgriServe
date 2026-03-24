package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.ParticipationRepository;
import com.cognizant.agriserve.dto.AttendanceUpdateRequestDTO;
import com.cognizant.agriserve.dto.ParticipationDTO;
import com.cognizant.agriserve.entity.Participation;
import com.cognizant.agriserve.exception.ResourceConflictException;
import com.cognizant.agriserve.service.ParticipationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ModelMapper modelMapper;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, ModelMapper modelMapper) {
        this.participationRepository = participationRepository;
        this.modelMapper = modelMapper;
    }

    // FIXED: Added missing @Override annotation
    @Override
    public ParticipationDTO registerForWorkshop(ParticipationDTO dto) {
        log.info("Attempting to register Farmer ID: {} for Workshop ID: {}", dto.getFarmerId(), dto.getWorkshopId());

        // Duplicate Registration Check (409 Conflict)
        boolean alreadyRegistered = participationRepository
                .existsByWorkshop_WorkshopIdAndFarmerId(dto.getWorkshopId(), dto.getFarmerId());

        if (alreadyRegistered) {
            log.warn("Conflict: Farmer {} is already registered for Workshop {}", dto.getFarmerId(), dto.getWorkshopId());
            throw new ResourceConflictException("Farmer is already registered for this workshop. Duplicate registrations are not allowed.");
        }

        Participation newRegistration = modelMapper.map(dto, Participation.class);
        newRegistration.setAttendanceStatus("Registered");

        Participation savedRegistration = participationRepository.save(newRegistration);

        ParticipationDTO responseDto = modelMapper.map(savedRegistration, ParticipationDTO.class);
        if (savedRegistration.getWorkshop() != null) {
            responseDto.setWorkshopId(savedRegistration.getWorkshop().getWorkshopId());
        }
        return responseDto;
    }

    @Override
    public List<ParticipationDTO> getParticipantsForWorkshop(Long workshopId) {
        log.info("Fetching participants for workshop ID: {}", workshopId);
        List<Participation> participations = participationRepository.findByWorkshop_WorkshopId(workshopId);

        return participations.stream()
                .map(p -> {
                    ParticipationDTO dto = modelMapper.map(p, ParticipationDTO.class);
                    if (p.getWorkshop() != null) dto.setWorkshopId(p.getWorkshop().getWorkshopId());
                    return dto;
                }).collect(Collectors.toList());
    }

    // FIXED: Matched exact casing of AttendanceUpdateRequestDTO
    @Override
    public ParticipationDTO updateAttendance(AttendanceUpdateRequestDTO requestDto) {
        log.info("Updating attendance for participation ID: {}", requestDto.getParticipationId());

        // Fetch Participation Record (404 Not Found)
        Participation existingRecord = participationRepository.findById(requestDto.getParticipationId())
                .orElseThrow(() -> new ResourceNotFoundException("Participation", "ID", requestDto.getParticipationId()));

        existingRecord.setAttendanceStatus(requestDto.getNewAttendanceStatus());
        Participation updatedRecord = participationRepository.save(existingRecord);

        ParticipationDTO responseDto = modelMapper.map(updatedRecord, ParticipationDTO.class);
        if (updatedRecord.getWorkshop() != null) responseDto.setWorkshopId(updatedRecord.getWorkshop().getWorkshopId());
        return responseDto;
    }
    // --- NEW: FARMER DASHBOARD (VIEW MY REGISTRATIONS) ---
    @Override
    public List<ParticipationDTO> getParticipationsByFarmerId(Long farmerId) {
        log.info("Fetching all workshop registrations for Farmer ID: {}", farmerId);

        // Ensure you add findByFarmerId to your ParticipationRepository!
        List<Participation> farmerHistory = participationRepository.findByFarmerId(farmerId);

        return farmerHistory.stream()
                .map(p -> {
                    ParticipationDTO dto = modelMapper.map(p, ParticipationDTO.class);
                    if (p.getWorkshop() != null) dto.setWorkshopId(p.getWorkshop().getWorkshopId());
                    return dto;
                }).collect(Collectors.toList());
    }
}