package org.shadowmaster435.limbo.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.util.math.ColorHelper;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageScreen.class)
public class MessageScreenMixin {

    @Inject(at = @At("TAIL"), method = "renderBackground")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            context.fill(0,0,context.getScaledWindowWidth(), context.getScaledWindowHeight(), ColorHelper.Argb.getArgb(255,0,0,0));

        }
    }

}
