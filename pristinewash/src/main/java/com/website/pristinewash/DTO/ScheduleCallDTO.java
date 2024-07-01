package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.User;
import com.website.pristinewash.utility.ScheduleState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleCallDTO {
    private Long id;
    private String username;
    private LocalDate date;
    private LocalTime time;
    private String message;
    private AddressDTO addressDTO;
    private UserDTO userDTO;
    private ScheduleState scheduleState;
}
