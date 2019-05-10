package se.ltucoders.bothniabladetdam.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;

@Service
public class DataParsingService {


    // Converts string date to LocalDateTime. Use 2018-04-05 as input format.
    public LocalDateTime parseDateTime(String dateTimeString) {
        try {
            if (dateTimeString != null && !dateTimeString.trim().isEmpty()) {
                // Takes yyyy-MM-dd as input and adds HH:mm:ss default values if
                // no other values are provided.
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd[HH:mm:ss]")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                return localDateTime;
            }
        } catch (DateTimeParseException ex) {

            System.err.println("The date can not be parsed. Enter valid date!");
            ex.printStackTrace();
        }
        return null;
    }


    // Converts string date to LocalDateTime when given specific format
    public LocalDateTime parseDateTime(String dateTimeString, String format) {
        try {
            if (dateTimeString != null && !dateTimeString.trim().isEmpty()) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(format).toFormatter();
                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                return localDateTime;
            }
        } catch (DateTimeParseException ex) {
            System.err.println("The date can not be parsed. Enter valid date!");
            ex.printStackTrace();
        }
        return null;
    }




    // TODO: not a good idea to change user input. Better to use String or Integer.
    // Converts string number to integer.
    public int parseNumber(String stringNumber) {
        try {
            if (stringNumber != null) {
                int number = Integer.parseInt(stringNumber);
                return number;
            }
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage());
            System.err.println("The number can not be parsed. Enter valid number!");
        }
        return -1;
    }
}
