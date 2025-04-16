package BackEnd.Enums;

/**
 * Represents different types of pets that Adopet platform supports.
 * This enum is typically used in pets species combo-box.
 */
public enum PetType {
    DOG("Dog"),
    CAT("Cat"),
    MOUSE("Mouse"),
    RABBIT("Rabbit"),
    OTHER("Other");     // Represents any other type of pet not specifically listed

    private final String label;

    /**
     * Constructs a PetType enum with a readable label.
     *
     * @param label the display label associated with the pet type
     */
    PetType(String label) {
        this.label = label;
    }

    /**
     * Returns the label associated with this pet type.
     *
     * @return the display label of the pet type
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the PetType enum that matches the given label.
     * @param label the label to convert
     * @return the matching PetType enum
     */
    public static PetType getEnum(String label) {
        if (label == null)
            return null;
        for (PetType type : PetType.values()) {
            if (type.getLabel().equalsIgnoreCase(label.trim())) {
                return type;
            }
        }
        return null;
    }
}