package com.site.employeetimesheetproject.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * ClassName: TimeEntry
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Getter
@Setter
public class TimeEntry {
    private LocalDate date;
    private float hours;

    public TimeEntry() {
    }

    public TimeEntry(LocalDate date, float hours) {
        this.date = date;
        this.hours = hours;
    }
}
