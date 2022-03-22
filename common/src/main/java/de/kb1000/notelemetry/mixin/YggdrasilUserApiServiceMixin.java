package de.kb1000.notelemetry.mixin;

import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import de.kb1000.notelemetry.TelemetryConfig;
import de.kb1000.notelemetry.TelemetryOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.Executor;

@Mixin(YggdrasilUserApiService.class)
public class YggdrasilUserApiServiceMixin {
    /**
     * @author kb1000
     */
    //Overwrite(remap = false)
    public boolean telemetryAllowed() {
        return false;
    }

    /**
     * @author kb1000
     */
    @Inject(method = "newTelemetrySession", at = @At("HEAD"), remap = false, cancellable = true)
    public void newTelemetrySession(Executor executor, CallbackInfoReturnable<TelemetrySession> cir) {
        if (TelemetryConfig.instance.getTelemetryOption() == TelemetryOptions.OFF) {
            cir.setReturnValue(TelemetrySession.DISABLED);
        }
    }
}
