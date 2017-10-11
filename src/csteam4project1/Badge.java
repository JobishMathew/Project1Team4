package csteam4project1;

/**
 *
 * @author Aaron Branham and Cole Landers
 */
public class Badge {
    private String desc;
    private String badgeID;

	public Badge(String badgeID, String desc){
        this.badgeID = badgeID;
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }

    public String getBadgeID() {
        return badgeID;
    }

    @Override
    public String toString() {
        return "#" + badgeID + " (" + getDescription() + ")";
    }
}