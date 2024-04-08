package com.website.pristinewash.DTO;

import com.website.pristinewash.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleCallDTO {
    private String name;
    private String username;
    private LocalDate date;
    private LocalTime time;
    private String message;
}
