package com.oldnews.backend.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.MonthDay;
import java.time.chrono.IsoEra;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDate {
    public ArticleDate(int year, MonthDay monthDay, IsoEra era) {
        this.year = year;
        this.month = monthDay.getMonthValue();
        this.day = monthDay.getDayOfMonth();
        this.era = era;
    }

    private int year;

    private int month;

    private int day;

    @Enumerated(EnumType.STRING)
    private IsoEra era = IsoEra.CE;
}
