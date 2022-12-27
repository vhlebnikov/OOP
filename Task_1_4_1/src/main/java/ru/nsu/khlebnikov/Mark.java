package ru.nsu.khlebnikov;

import java.util.Arrays;

/**
 * My enumeration of marks.
 */
public enum Mark {
    GREAT("Отлично"),
    GOOD("Хорошо"),
    SATISFACTORY("Удовлетворительно"),
    UNSATISFACTORY("Неудовлетворительно"),
    PASS("Зачёт"),
    INSUFFICIENT("Незачёт");

    private final String mark;

    Mark(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }

    /**
     * Convert string representation of mark to enum value.
     *
     * @param mark string representation of mark
     * @return value with type Mark
     */
    public static Mark getEnumMark(String mark) {
        return Arrays.stream(values())
                .filter(m -> m.getMark().equals(mark))
                .findAny()
                .orElseThrow(() -> new EnumConstantNotPresentException(Mark.class, mark));
    }

    /**
     * Convert enum representation of mark to double value.
     *
     * @param mark enum representation of mark
     * @return value with type Double
     */
    public static Double getDoubleMark(Mark mark) {
        return switch (mark) {
            case GREAT, PASS -> 5d;
            case GOOD -> 4d;
            case SATISFACTORY -> 3d;
            case UNSATISFACTORY, INSUFFICIENT -> 2d;
        };
    }
}
