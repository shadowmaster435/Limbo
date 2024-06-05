package org.shadowmaster435.limbo.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.world.BackupPromptScreen;
import net.minecraft.util.math.ColorHelper;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackupPromptScreen.class)
public class BackupPromptScreenMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true)
    public void init(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            context.fill(0,0,context.getScaledWindowWidth(), context.getScaledWindowHeight(), ColorHelper.Argb.getArgb(255,0,0,0));

            ci.cancel();
        }
    }


}
