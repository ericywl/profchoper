package profchoper._misc;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Constant {
    public static final int SLOT_TIME = 30;
    public static final LocalTime DAY_FIRST_START_TIME = LocalTime.of(9, 0);
    public static final LocalTime DAY_END_TIME = LocalTime.of(17, 0);
    public static final LocalTime DAY_LAST_START_TIME = DAY_END_TIME.minus(30, ChronoUnit.MINUTES);

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
}
