package com.website.pristinewash.repository;

import com.website.pristinewash.entity.ScheduleCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCallRepository extends JpaRepository<ScheduleCall, Integer> {
}
