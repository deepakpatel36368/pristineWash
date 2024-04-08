package com.website.pristinewash.serviceImp;

import com.website.pristinewash.DTO.ScheduleCallDTO;
import com.website.pristinewash.entity.ScheduleCall;
import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.ScheduleCallRepository;
import com.website.pristinewash.repository.UserRepository;
import com.website.pristinewash.service.ScheduleCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ScheduleCallServiceImpl implements ScheduleCallService {

    @Autowired
    ScheduleCallRepository scheduleCallRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveScheduleCall(ScheduleCallDTO scheduleCallDTO) {
        ScheduleCall scheduleCall = mapScheduleCallDTOTOScheduleCall(scheduleCallDTO);
        scheduleCallRepository.save(scheduleCall);
    }

    public ScheduleCall mapScheduleCallDTOTOScheduleCall(ScheduleCallDTO scheduleCallDTO) {
        ScheduleCall scheduleCall ;
        User user = userRepository.findByUsername(scheduleCallDTO.getUsername());
        if(user != null) {
            scheduleCall = new ScheduleCall();
            scheduleCall.setUser(user);
            scheduleCall.setDate(scheduleCallDTO.getDate());
            scheduleCall.setTime(scheduleCallDTO.getTime());
            scheduleCall.setMessage(scheduleCallDTO.getMessage());

            return  scheduleCall;
        } else {
            throw new UsernameNotFoundException("User is trying to schedule call to different number " + scheduleCallDTO.getUsername());
        }

    }


}
