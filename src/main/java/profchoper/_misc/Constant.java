package profchoper._misc;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Constant {
    public static final int SLOT_TIME = 30;
    public static final LocalTime DAY_FIRST_START_TIME = LocalTime.of(9, 0);
    public static final LocalTime DAY_END_TIME = LocalTime.of(17, 0);
    public static final LocalTime DAY_LAST_START_TIME = DAY_END_TIME.minus(30, ChronoUnit.MINUTES);

    public static final String AVAIL = "AVAILABLE";
    public static final String PENDING = "PENDING";
    public static final String BOOKED = "BOOKED";

    public static final String INFOSYS = "50.001";
    public static final String COMPSTRUCT = "50.002";
    public static final String ALGO = "50.004";

    public static final String OKA = "Oka Kurniawan";
    public static final String MAN = "Ngai-Man Cheung";
}
