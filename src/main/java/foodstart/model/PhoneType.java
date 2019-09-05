package foodstart.model;

/**
 * The possible types of phone numbers
 */
public enum PhoneType
{
    MOBILE("Mobile"), WORK("Work"), HOME("Home");

    private String name;
    PhoneType(String name) {
        this.name = name;
    }
}
