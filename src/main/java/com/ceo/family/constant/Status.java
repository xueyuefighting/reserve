package com.ceo.family.constant;

public enum Status {
    NORMAL(0),DELETE(1);
    private final int value;

    private Status(int value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     * @return null if the value is not found.
     */
    public static Status findByValue(int value) {
        switch (value) {
            case 0:
                return NORMAL;
            case 1:
                return DELETE;
            default:
                return null;
        }
    }
}
