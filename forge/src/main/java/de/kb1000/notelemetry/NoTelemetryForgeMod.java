package de.kb1000.notelemetry;

import net.minecraft.client.MinecraftClient;
import net.minecraftforge.fml.common.Mod;

@Mod("no_telemetry")
public class NoTelemetryForgeMod {
    public static final String MODID = "no_telemetry_plus";

    public NoTelemetryForgeMod() {
        TelemetryConfig.createInstance(MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve("telemetry.config"));
    }
}
