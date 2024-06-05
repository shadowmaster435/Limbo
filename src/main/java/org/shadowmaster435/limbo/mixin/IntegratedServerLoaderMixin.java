package org.shadowmaster435.limbo.mixin;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServerLoader.class)
public class IntegratedServerLoaderMixin {


    @Inject(at = @At("HEAD"), method = "checkBackupAndStart", cancellable = true)
    public void init(LevelStorage.Session session, SaveLoader saveLoader, ResourcePackManager dataPackManager, Runnable onCancel, CallbackInfo ci) {
        if (MiscUtil.in_limbo) {
            ((IntegratedServerLoader)(Object)this).start(session, saveLoader, dataPackManager);

            ci.cancel();
        }
    }

}
