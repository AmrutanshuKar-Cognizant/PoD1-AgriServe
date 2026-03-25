package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.WorkshopDTO;
import java.util.List;


public interface WorkshopService {


    List<WorkshopDTO> getAllWorkshops();


    List<WorkshopDTO> getActiveWorkshopsForFarmers();
    // ... your existing methods ...


    WorkshopDTO scheduleWorkshop(WorkshopDTO workshopDto);


    List<WorkshopDTO> getWorkshopsByOfficer(Long officerId);


    WorkshopDTO updateWorkshopStatus(Long workshopId, String status);

    WorkshopDTO updateWorkshop(Long workshopId, WorkshopDTO dto);
    void deleteWorkshop(Long workshopId);
}
