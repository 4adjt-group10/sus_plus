package br.com.susmanager.controller.dto.professional;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BusinessHours {
    private DayOfWeek dayOfWeek;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "10:00:00")
    private LocalTime start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "22:00:00")
    private LocalTime end;

    public BusinessHours(DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isOpen(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().equals(dayOfWeek)
                && !dateTime.toLocalTime().isBefore(start)
                && !dateTime.toLocalTime().isAfter(end);
    }

    public boolean isClosed(LocalDateTime dateTime) {
        return !isOpen(dateTime);
    }

    public boolean isBeforeOpening(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().equals(dayOfWeek) && dateTime.toLocalTime().isBefore(start);
    }

    public boolean isAfterClosing(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().equals(dayOfWeek) && dateTime.toLocalTime().isAfter(end);
    }

}
