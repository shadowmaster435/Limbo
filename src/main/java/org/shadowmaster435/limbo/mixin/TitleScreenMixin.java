package org.shadowmaster435.limbo.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.ScreenHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {


    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            ScreenHelper.create_mod_title_screen((TitleScreen)(Object)this);
            ci.cancel();
        }
    }
    @Inject(at = @At("TAIL"), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            ScreenHelper.render_title_bg(((TitleScreen)(Object)this), context, mouseX, mouseY, delta);

        }
    }

}
