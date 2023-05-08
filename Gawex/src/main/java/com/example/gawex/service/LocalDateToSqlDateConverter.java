package com.example.gawex.service;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.sql.Date;
import java.time.LocalDate;

public class LocalDateToSqlDateConverter implements Converter<LocalDate, Date> {

    @Override
    public Result<Date> convertToModel(LocalDate localDate, ValueContext context) {
        if (localDate == null) {
            return Result.ok(null);
        }
        return Result.ok(Date.valueOf(localDate));
    }

    @Override
    public LocalDate convertToPresentation(Date sqlDate, ValueContext context) {
        if (sqlDate == null) {
            return null;
        }
        return sqlDate.toLocalDate();
    }

}
