package org.shadowmaster435.limbo.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {


    @Inject(at = @At("HEAD"), method = "canUse", cancellable = true)
    public void add_tenebra(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (ScreenHandler.canUse(((CraftingScreenHandler)(Object)this).context, player, ModBlocks.TENEBRA_CRAFTING_TABLE)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

}
