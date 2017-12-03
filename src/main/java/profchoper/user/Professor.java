package profchoper.user;

import profchoper.slot.SlotPeriod;

import java.util.List;

public class Professor {
    private final String profName;
    private String officeLocation;
    private List<SlotPeriod> availablePeriods;

    public Professor(String profName, String officeLoc) {
        this.profName = profName;
        this.officeLocation = officeLoc;
    }

    @Override
    public String toString() {
        return profName;
    }

    public String getProfName() {
        return profName;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }
}
