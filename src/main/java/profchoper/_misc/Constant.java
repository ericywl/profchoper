package profchoper._misc;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final int SLOT_TIME = 30;
    public static final LocalTime DAY_FIRST_START_TIME = LocalTime.of(9, 0);
    public static final LocalTime DAY_END_TIME = LocalTime.of(17, 0);
    public static final LocalTime DAY_LAST_START_TIME = DAY_END_TIME.minus(30, ChronoUnit.MINUTES);

    public static final int WEEK_CAL_COL = 5;
    public static final int WEEK_CAL_ROW = 16;

    public static final String TERM = "TERM";
    public static final String MONTH = "MONTH";
    public static final String DATE = "DATE";
    public static final String WEEK = "WEEK";
    public static final String DATE_TIME = "DATE_TIME";
    public static final String SCHOOL_WEEK = "SCHOOL_WEEK";

    public static final String AVAIL = "AVAILABLE";
    public static final String PENDING = "PENDING";
    public static final String BOOKED = "BOOKED";

    public static final String INFOSYS = "50.001";
    public static final String COMPSTRUCT = "50.002";
    public static final String ALGO = "50.004";

    public static final String OKA = "Oka Kurniawan";
    public static final String MAN = "Ngai-Man Cheung";

    public static final String STUDENT = "STUDENT";
    public static final String PROF = "PROFESSOR";

    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    public static final String ROLE_PROF = "ROLE_PROFESSOR";

    public static final Map<LocalTime, Integer> TIME_TO_ROW = new HashMap<>();
    static {
        TIME_TO_ROW.put(LocalTime.of(9, 0, 0), 0);
        TIME_TO_ROW.put(LocalTime.of(9, 30, 0), 1);
        TIME_TO_ROW.put(LocalTime.of(10, 0, 0), 2);
        TIME_TO_ROW.put(LocalTime.of(10, 30, 0), 3);
        TIME_TO_ROW.put(LocalTime.of(11, 0, 0), 4);
        TIME_TO_ROW.put(LocalTime.of(11, 30, 0), 5);
        TIME_TO_ROW.put(LocalTime.of(12, 0, 0), 6);
        TIME_TO_ROW.put(LocalTime.of(12, 30, 0), 7);
        TIME_TO_ROW.put(LocalTime.of(13, 0, 0), 8);
        TIME_TO_ROW.put(LocalTime.of(13, 30, 0), 9);
        TIME_TO_ROW.put(LocalTime.of(14, 0, 0), 10);
        TIME_TO_ROW.put(LocalTime.of(14, 30, 0), 11);
        TIME_TO_ROW.put(LocalTime.of(15, 0, 0), 12);
        TIME_TO_ROW.put(LocalTime.of(15, 30, 0), 13);
        TIME_TO_ROW.put(LocalTime.of(16, 0, 0), 14);
        TIME_TO_ROW.put(LocalTime.of(16, 30, 0), 15);
    }
}
