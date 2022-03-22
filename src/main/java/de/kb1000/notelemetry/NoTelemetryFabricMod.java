package de.kb1000.notelemetry;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

public class NoTelemetryFabricMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TelemetryConfig.createInstance(MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve("telemetry.config"));
    }
}
