package org.shadowmaster435.limbo.shader;

import ladysnake.satin.api.event.EntitiesPostRenderCallback;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.SamplerUniform;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import ladysnake.satin.api.managed.uniform.UniformMat4;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.shadowmaster435.limbo.Limbo;

public class BelowWorldShader implements PostWorldRenderCallbackV2, EntitiesPostRenderCallback, ShaderEffectRenderCallback, ClientTickEvents.EndTick {
    public static final Identifier ID = new Identifier(Limbo.id, "shaders/post/below_world.json");
    public static final BelowWorldShader INSTANCE = new BelowWorldShader();

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public final ManagedShaderEffect testShader = ShaderEffectManager.getInstance().manage(ID, shader -> {
        shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer)mc.getFramebuffer()).getStillDepthMap());

        shader.setUniformValue("ViewPort", 0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
    });
    private final Uniform1f uniformSTime = testShader.findUniform1f("STime");
    private final UniformMat4 uniformInverseTransformMatrix = testShader.findUniformMat4("InverseTransformMatrix");
    private final Uniform3f uniformCameraPosition = testShader.findUniform3f("CameraPosition");
    private final Uniform3f uniformCenter = testShader.findUniform3f("Center");

    // fancy shader stuff
    private final Matrix4f projectionMatrix = new Matrix4f();
    private int ticks;

    private boolean isWorldNight(@Nullable PlayerEntity player) {
        if (player != null) {
            World world = player.getWorld();
            float celestialAngle = world.getSkyAngle(1.0f);
            return 0.23f < celestialAngle && celestialAngle < 0.76f;
        }
        return false;
    }

    @Override
    public void onEndTick(MinecraftClient minecraftClient) {
        if (!minecraftClient.isPaused()) {
            ticks++;
        }
    }

    @Override
    public void onWorldRendered(MatrixStack matrices, Camera camera, float tickDelta, long nanoTime) {
        uniformSTime.set((ticks + tickDelta) / 20f);
        uniformInverseTransformMatrix.set(GlMatrices.getInverseTransformMatrix(projectionMatrix));

        Vec3d cameraPos = camera.getPos();
        Vec3d playerpos = mc.player.getLerpedPos(tickDelta);
        uniformCenter.set((float)playerpos.x, (float)playerpos.y, (float)playerpos.z);

        uniformCameraPosition.set((float)cameraPos.x, (float)cameraPos.y, (float)cameraPos.z);
        if (MinecraftClient.getInstance().world.getRegistryKey().getValue().equals(new Identifier("limbo:limbo"))) {
            testShader.render(tickDelta);
        }
    }

    @Override
    public void renderShaderEffects(float tickDelta) {
    }

    private static float lerpf(double n, double prevN, float tickDelta) {
        return (float) MathHelper.lerp(tickDelta, prevN, n);
    }


    @Override
    public void onEntitiesRendered(Camera camera, Frustum frustum, float tickDelta) {

    }
}