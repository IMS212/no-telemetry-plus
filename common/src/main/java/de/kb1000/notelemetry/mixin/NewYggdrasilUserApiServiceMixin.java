package de.kb1000.notelemetry.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import com.mojang.authlib.yggdrasil.response.UserAttributesResponse;
import de.kb1000.notelemetry.TelemetryConfig;
import de.kb1000.notelemetry.TelemetryOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(YggdrasilUserApiService.class)
public class NewYggdrasilUserApiServiceMixin {
    @Redirect(method = "fetchProperties", at = @At(value = "INVOKE", target = "Lcom/mojang/authlib/yggdrasil/response/UserAttributesResponse$Privileges;getTelemetry()Z", remap = false), remap = false, require = 0)
    private boolean getTelemetry(UserAttributesResponse.Privileges privileges) {
        return TelemetryConfig.instance.getTelemetryOption() != TelemetryOptions.OFF;
    }
}
