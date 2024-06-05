package org.shadowmaster435.limbo.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.util.math.ColorHelper;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/world/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/server/WorldGenerationProgressTracker;IIII)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            context.fill(0,0,context.getScaledWindowWidth(), context.getScaledWindowHeight(), ColorHelper.Argb.getArgb(255,0,0,0));

        }
    }

}
