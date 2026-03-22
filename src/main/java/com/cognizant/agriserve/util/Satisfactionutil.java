package com.cognizant.agriserve.util;

import com.cognizant.agriserve.dto.SatisfactionMetricDTO;
import com.cognizant.agriserve.entity.*;
import java.time.LocalDate;

public class Satisfactionutil {


    public static SatisfactionMetric Satisfactionutili(SatisfactionMetricDTO dt,
                                                       TrainingProgram program,
                                                       User manager,
                                                       Double averageScore) {
        SatisfactionMetric st = new SatisfactionMetric();
        st.setTrainingProgram(program);
        st.setProgramManager(manager);
        st.setScore(averageScore);
        st.setStatus("EVALUATED");
        st.setDate(LocalDate.now());
        return st;
    }
}