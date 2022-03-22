package de.kb1000.notelemetry;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public enum TelemetryOptions {
    OFF(0),
    ANONYMOUS(1),
    FULL(2);

    public final int id;

    TelemetryOptions(int id) {
        this.id = id;
    }

    public static TelemetryOptions fromId(int id) {
        return switch (id) {
            case 0 -> OFF;
            case 1 -> ANONYMOUS;
            case 2 -> FULL;
            default -> throw new IllegalArgumentException("Unknown id: " + id);
        };
    }

    public Text getText() {
        return switch (this) {
            case OFF -> new LiteralText("Off");
            case ANONYMOUS -> new LiteralText("Anonymous");
            case FULL -> new LiteralText("Full");
        };
    }
}
