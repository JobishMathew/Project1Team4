package csteam4project1;

public class Badge {
    
    private String desc;
    private String badgeID;
    
    public Badge() {
        this("0000000", "Empty Employee");
    }

    public Badge(String badgeID, String desc){
        this.badgeID = badgeID;
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }
    
    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getBadgeID() {
        return badgeID;
    }
    
    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    public String toString() {
        return "#" + badgeID + " (" + getDescription() + ")";
    }
}