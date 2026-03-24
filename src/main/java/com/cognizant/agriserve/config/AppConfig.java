package com.cognizant.agriserve.config;

import com.cognizant.agriserve.dto.TrainingProgramDTO;
import com.cognizant.agriserve.entity.TrainingProgram;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Teach ModelMapper how to map the User object to the managerId field
        mapper.typeMap(TrainingProgram.class, TrainingProgramDTO.class).addMappings(m -> {
            m.map(src -> src.getManager().getUserID(), TrainingProgramDTO::setManagerId);
        });

        return mapper;
    }
}