package com.pdv.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.jboss.logging.Logger;

import com.pdv.domain.entities.enums.EnumDateFormat;

import jakarta.inject.Inject;

public class DateUtils {

    @Inject
    public static Logger logger;

    public static final DateTimeFormatter DDMMYYYYHHMMSS = EnumDateFormat.DDMMYYYYHHMMSS.getFormat();

    public static String formatDDMMYYYYHHMMSS(LocalDateTime date) {
        return date != null ? DDMMYYYYHHMMSS.format(date) : "";
    }

    public static String convertToDateFormat(String date) {
        if (date == null || date.isEmpty()) {
            logger.warn("Data nula ou vazia fornecida para conversão");
            return "";
        }

        try {

            if (date.contains("T")) {
                return date.substring(0, 10);
            }

            if (date.contains("/")) {
                String[] parts = date.split("/");
                if (parts.length == 3) {

                    String year = parts[0].length() == 4 ? parts[0] : parts[2];
                    String month = parts[0].length() == 4 ? parts[1] : parts[1];
                    String day = parts[0].length() == 4 ? parts[2] : parts[0];

                    month = month.length() == 1 ? "0" + month : month;
                    day = day.length() == 1 ? "0" + day : day;

                    return year + "-" + month + "-" + day;
                }
            }

            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }

            String[] possiblePatterns = {
                    "dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd",
                    "dd-MM-yyyy", "MM-dd-yyyy",
                    "dd.MM.yyyy", "yyyy.MM.dd"
            };

            for (String pattern : possiblePatterns) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat(pattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = inputFormat.parse(date);
                    return outputFormat.format(parsedDate);
                } catch (ParseException ignored) {
                }
            }

            // Se chegou aqui, não conseguimos converter com nenhum formato conhecido
            logger.error("Não foi possível converter a data: " + date + " para o formato yyyy-MM-dd");
            throw new IllegalArgumentException("Formato de data não reconhecido: " + date);

        } catch (Exception e) {
            logger.error("Erro ao converter data: " + date, e);
            throw new IllegalArgumentException("Erro ao processar a data: " + date, e);
        }
    }

    public static LocalDateTime parseDateTime(String dateStart) {
        if (dateStart == null || dateStart.isEmpty()) {
            return LocalDateTime.now();
        }

        try {
            return LocalDate.parse(dateStart).atStartOfDay();
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(dateStart);
            } catch (DateTimeParseException ex) {
                return LocalDateTime.now();
            }
        }
    }

    public static LocalDateTime resetTime(LocalDateTime dateTime) {
        return dateTime.with(LocalTime.MIN);
    }
}
