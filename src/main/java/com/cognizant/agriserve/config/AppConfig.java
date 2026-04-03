package com.cognizant.agriserve.config;

import com.cognizant.agriserve.dto.TrainingProgramDTO;
import com.cognizant.agriserve.entity.TrainingProgram;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.typeMap(TrainingProgram.class, TrainingProgramDTO.class).addMappings(m -> {
            m.map(src -> src.getManager().getUserId(), TrainingProgramDTO::setManagerId);
        });

        return mapper;
    }
}