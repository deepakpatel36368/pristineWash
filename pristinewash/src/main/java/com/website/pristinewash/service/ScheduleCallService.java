package com.website.pristinewash.service;

import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.utility.ScheduleState;

import java.util.List;

public interface ScheduleCallService {
    void saveScheduleCall(ScheduleCallDTO scheduleCallDTO, String userName);

    List<ScheduleCallDTO> findAllScheduleCall() throws Exception;

    void updateStateById(Long id, ScheduleState scheduleState);
}
