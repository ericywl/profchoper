package profchoper.user;

public class Professor {
    private final String profName;
    private String officeLocation;

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
