package profchoper._misc;

import java.time.LocalTime;

public class Constant {
    public static final int SLOT_TIME = 30;
    public static final LocalTime DAY_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime DAY_END_TIME = LocalTime.of(17, 0);

    public static final String AVAIL = "AVAILABLE";
    public static final String PENDING = "PENDING";
    public static final String BOOKED = "BOOKED";
}
