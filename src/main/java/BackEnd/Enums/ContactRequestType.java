package BackEnd.Enums;

/**
 * Represents different types of contact requests that Adopet platform supports.
 * This enum is typically used in the contact us interested in combo-box.
 */
public enum ContactRequestType {
    ADOPTION_CONSULTATION("Adoption details"),
    ADOPTION_CANCELLATION("Cancelling adoption"),
    PRICING("Price of adoption"),
    TIPS("Tips for adoption"),
    OTHER("Other");

    private final String label;

    /**
     * Constructs a ContactRequestType enum constant with readable label.
     *
     * @param label the label associated with the contact request type
     */
    ContactRequestType(String label) {
        this.label = label;
    }

    /**
     * Returns the label of the contact request type.
     * This label is used in dropdown component.
     *
     * @return the display label for this contact request type
     */
    public String getLabel() {
        return label;
    }
}
