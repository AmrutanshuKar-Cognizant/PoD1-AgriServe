package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.AttendanceUpdateRequestDTO;
import com.cognizant.agriserve.dto.ParticipationDTO;
import java.util.List;


public interface ParticipationService {


    ParticipationDTO registerForWorkshop(ParticipationDTO dto);


    List<ParticipationDTO> getParticipantsForWorkshop(Long workshopId);


    ParticipationDTO updateAttendance(AttendanceUpdateRequestDTO requestDto);
    List<ParticipationDTO> getParticipationByFarmerId(Long farmerId);
}