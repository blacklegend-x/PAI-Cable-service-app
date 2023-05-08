package com.example.gawex.service;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.sql.Time;
import java.time.LocalTime;

public class LocalTimeToSqlTimeConverter implements Converter<LocalTime, Time> {

    @Override
    public Result<Time> convertToModel(LocalTime localTime, ValueContext context) {
        if (localTime == null) {
            return Result.ok(null);
        }
        return Result.ok(Time.valueOf(localTime));
    }

    @Override
    public LocalTime convertToPresentation(Time sqlTime, ValueContext context) {
        if (sqlTime == null) {
            return null;
        }
        return sqlTime.toLocalTime();
    }
}
