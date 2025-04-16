package BackEnd.Enums;

/**
 * Represents different sizes of pets that Adopet platform supports.
 * This enum is typically used in pets size combo-box.
 */
public enum PetSize {
    EXTRA_SMALL("Extra-Small"),
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
    EXTRA_LARGE("Extra-Large");

    private final String label;

    /**
     * Constructs a PetSize enum constant with the readable label.
     *
     * @param label the display label for the pet size
     */
    PetSize(String label) {
        this.label = label;
    }

    /**
     * Returns the label associated with the pet size.
     *
     * @return the label of the pet size
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the PetSize enum that matches the given label.
     * @param label the label to convert
     * @return the matching PetSize enum
     */
    public static PetSize getEnum(String label) {
        if (label == null)
            return null;
        for (PetSize size : PetSize.values()) {
            if (size.getLabel().equalsIgnoreCase(label.trim())) {
                return size;
            }
        }
        return null;
    }

}
