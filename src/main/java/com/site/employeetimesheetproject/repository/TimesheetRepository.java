package com.site.employeetimesheetproject.repository;


import com.site.employeetimesheetproject.model.Timesheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * ClassName: TimesheetRepository
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public interface TimesheetRepository extends MongoRepository<Timesheet, String> {
    @Query("{ 'timeEntries.date' : { '$gte' : ?0, '$lte' : ?1 } }")
    List<Timesheet> findByTimeEntriesDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("{ 'employee._id' : ?0, 'timeEntries.date': { '$gte': ?1, '$lte': ?2 } }")
    List<Timesheet> findByEmployeeIdAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate);

    @Query("{ 'project._id' : ?0, 'timeEntries.date': { '$gte': ?1, '$lte': ?2 } }")
    List<Timesheet> findByProjectIdAndDateRange(String projectId, LocalDate startDate, LocalDate endDate);

    List<Timesheet> findByEmployeeId(String employeeId);
    List<Timesheet> findByProjectId(String projectId);
}
