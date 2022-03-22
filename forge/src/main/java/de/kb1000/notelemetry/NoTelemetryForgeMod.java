package de.kb1000.notelemetry;

import net.minecraft.client.MinecraftClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("no_telemetry")
public class NoTelemetryForgeMod {
    public static final String MODID = "no_telemetry_plus";

    public NoTelemetryForgeMod() {
        TelemetryConfig.createInstance(FMLPaths.CONFIGDIR.get().resolve("telemetry.config"));
        System.out.println("[NoTelemetryPlus] " + (TelemetryConfig.instance.getTelemetryOption() == TelemetryOptions.OFF ? "disabling " : "anonymizing") + " telemetry");
    }
}
