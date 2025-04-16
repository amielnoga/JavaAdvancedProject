package BackEnd.Enums;

/**
 * Represents the different statuses of contact requests that Adopet platform supports.
 * This enum is typically used in the contact us status combo-box.
 */
public enum ContactRequestStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed"),
    SPAM("Spam");

    private final String label;

    /**
     * Constructs a ContactRequestStatus enum constant with readable label.
     *
     * @param label the label associated with the contact request status
     */
    ContactRequestStatus(String label) {
        this.label = label;
    }

    /**
     * Returns the label of the contact request status.
     * This label is used in dropdown component.
     *
     * @return the display label for this contact request status
     */
    public String getLabel() {
        return label;
    }
}
