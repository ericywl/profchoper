package profchoper.slot;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static profchoper.Constant.*;

public final class SlotPeriod {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private long duration;

    public SlotPeriod(DayOfWeek day, LocalTime startTime, LocalTime endTime)
            throws SlotPeriodErrorException {
        if (!startTime.isBefore(endTime)) {
            throw new SlotPeriodErrorException("Start time cannot be before end time.");
        }

        if (startTime.isBefore(DAY_START_TIME) || startTime.isAfter(DAY_END_TIME)
                || endTime.isAfter(DAY_END_TIME)) {
            throw new SlotPeriodErrorException("Error: Slots not within boundaries.");
        }

        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = ChronoUnit.MINUTES.between(startTime, endTime);
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }
}


