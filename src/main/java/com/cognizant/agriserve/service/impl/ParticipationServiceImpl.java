package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.ParticipationRepository;
import com.cognizant.agriserve.dto.AttendanceUpdateRequestDto;
import com.cognizant.agriserve.dto.ParticipationDto;
import com.cognizant.agriserve.entity.Participation;
import com.cognizant.agriserve.service.ParticipationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationServiceImpl(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    @Override
    public List<ParticipationDto> getParticipantsForWorkshop(Long workshopId) {

        List<Participation> participations = participationRepository.findByWorkshop_WorkshopId(workshopId);

        return participations.stream()
                .map(p -> new ParticipationDto(
                        p.getParticipationId(),
                        p.getWorkshop().getWorkshopId(),
                        p.getFarmerId(),
                        p.getAttendanceStatus(),
                        p.getFeedback()
                )).collect(Collectors.toList());
    }

    @Override
    public ParticipationDto updateAttendance(AttendanceUpdateRequestDto requestDto) {

        // 1. Validate existence: Ensure we aren't updating a non-existent record
        Participation existingRecord = participationRepository.findById(requestDto.getParticipationId())
                .orElseThrow(() -> new RuntimeException("Participation record not found for ID: " + requestDto.getParticipationId()));

        // 2. State mutation: Update only the specific field requested
        existingRecord.setAttendanceStatus(requestDto.getNewAttendanceStatus());

        // 3. Persist the change
        Participation updatedRecord = participationRepository.save(existingRecord);

        // 4. Return the safe response mapping
        return new ParticipationDto(
                updatedRecord.getParticipationId(),
                updatedRecord.getWorkshop().getWorkshopId(),
                updatedRecord.getFarmerId(),
                updatedRecord.getAttendanceStatus(),
                updatedRecord.getFeedback()
        );
    }
}