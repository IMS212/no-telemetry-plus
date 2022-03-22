package de.kb1000.notelemetry.mixin;

import com.mojang.authlib.minecraft.TelemetryPropertyContainer;
import de.kb1000.notelemetry.TelemetryConfig;
import de.kb1000.notelemetry.TelemetryOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.UUID;

@Pseudo
@Mixin(targets = "net.minecraft.client.util.telemetry.TelemetrySender")
@Environment(EnvType.CLIENT)
public class TelemetrySenderMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    private boolean disableTelemetrySession() {
        return TelemetryConfig.instance.getTelemetryOption() == TelemetryOptions.OFF;
    }
    
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/authlib/minecraft/TelemetryPropertyContainer;addProperty(Ljava/lang/String;Ljava/lang/String;)V", ordinal = 0))
    private void anonymizeId(TelemetryPropertyContainer instance, String key, String value) {
        if (TelemetryConfig.instance.getTelemetryOption() != TelemetryOptions.FULL) {
            instance.addProperty(key, new UUID(0, 0).toString());
        } else {
            instance.addProperty(key, value);
        }
    }
    
    /**
     * @author IMS
     */
    @Overwrite
    private static void addProperty(String name, Optional<String> propertyValue, TelemetryPropertyContainer container) {
        if (TelemetryConfig.instance.getTelemetryOption() != TelemetryOptions.FULL) {
            container.addNullProperty(name);
        } else {
            propertyValue.ifPresentOrElse(value -> container.addProperty(name, value), () -> container.addNullProperty(name));
        }
    }
}
