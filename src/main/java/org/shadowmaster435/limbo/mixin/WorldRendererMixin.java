package org.shadowmaster435.limbo.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.RenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {


    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void check_exit(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        if (MiscUtil.start_exit_world) {
            MiscUtil.exit_world();
            ci.cancel();
        }

    }
    @Inject(at = @At("HEAD"), method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
    public void disable_celestial_bodies(Matrix4f matrix4f, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world.getRegistryKey().getValue().equals(new Identifier("limbo:limbo"))) {
            ci.cancel();
        }
    }
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        MiscUtil.tick_globals();
    }

    @Inject(at = @At("HEAD"), method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V", cancellable = true)
    public void disable_clouds(MatrixStack matrices, Matrix4f matrix4f, Matrix4f matrix4f2, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world.getRegistryKey().getValue().equals(new Identifier("limbo:limbo"))) {
            RenderHelper.render_sky_eye(matrices);
            ci.cancel();
        }
    }

}
