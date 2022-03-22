package de.kb1000.notelemetry;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class NoTelemetryMixinConfigPlugin {
    
    public void onLoad(String mixinPackage) {
    }

    private boolean isForge() {
        return classExists("net.minecraftforge.fml.common.Mod") && !classExists("net.fabricmc.loader.api.FabricLoader");
    }

    private static boolean classExists(String name) {
        try {
            return NoTelemetryMixinConfigPlugin.class.getClassLoader().loadClass(name) != null;
        } catch (Exception | LinkageError e) {
            return false;
        }
    }

    
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin")) {
            return classExists("com.mojang.authlib.yggdrasil.response.UserAttributesResponse$Privileges");
        }
        return true;
    }

    
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    
    public List<String> getMixins() {
        return null;
    }

    
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
