package de.kb1000.notelemetry.mixin;

import de.kb1000.notelemetry.TelemetryConfig;
import de.kb1000.notelemetry.TelemetryOptions;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AccessibilityOptionsScreen.class)
public class MixinAccessibilitySettings {
    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/option/SimpleOptionsScreen;<init>(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/option/GameOptions;Lnet/minecraft/text/Text;[Lnet/minecraft/client/option/Option;)V"
            ),
            index = 3
    )
    private static Option[] noTelemetry$addTelemetryButton(Option[] old) {
        Option[] options = new Option[old.length + 1];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 1] = TelemetryConfig.TELEMETRY_OPTIONS_CYCLING_OPTION;
        return options;
    }
}
