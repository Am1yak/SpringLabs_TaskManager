package org.example.springlab2.dtos;

import java.util.Date;

public class DateDto {
    private Date date;

    public DateDto(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
