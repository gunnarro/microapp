package com.gunnarro.calendar.domain.calendar;

public interface CalendarEventFilter<T, E> {

    public boolean isMatch(T object, E text);
}
