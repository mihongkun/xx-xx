package org.forkjoin.apikit.spring;


import org.apache.commons.lang3.StringUtils;
import org.forkjoin.apikit.spring.utils.DateTimeUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 */
public class DateConverter implements Converter<String, Date> {
    private String format;

    public DateConverter() {
        this(DateTimeUtils.FORMAT);
    }

    public DateConverter(String format) {
        this.format = format;
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}