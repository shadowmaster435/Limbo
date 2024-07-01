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
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
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
import org.joml.Vector3f;
import org.shadowmaster435.limbo.Limbo;

public class CollapseOrbDepthFX implements PostWorldRenderCallbackV2, EntitiesPostRenderCallback, ShaderEffectRenderCallback, ClientTickEvents.EndTick {
    public static final Identifier FANCY_NIGHT_SHADER_ID = new Identifier(Limbo.id, "shaders/post/collapse_orb.json");
    public static final CollapseOrbDepthFX INSTANCE = new CollapseOrbDepthFX();

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public final ManagedShaderEffect testShader = ShaderEffectManager.getInstance().manage(FANCY_NIGHT_SHADER_ID, shader -> {
        shader.setSamplerUniform("DepthSampler", ((ReadableDepthFramebuffer)mc.getFramebuffer()).getStillDepthMap());

        shader.setUniformValue("ViewPort", 0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
    });
    private final Uniform1f uniformSTime = testShader.findUniform1f("STime");
    private final UniformMat4 uniformInverseTransformMatrix = testShader.findUniformMat4("InverseTransformMatrix");
    private final Uniform3f uniformCameraPosition = testShader.findUniform3f("CameraPosition");
    public final Uniform3f uniformCenter = testShader.findUniform3f("Center");
    public final Uniform1f orb_scale = testShader.findUniform1f("OrbScale");
    public final Uniform1f eye_delta = testShader.findUniform1f("EyeLookDelta");
    public final Uniform1f static_influence = testShader.findUniform1f("StaticInfluence");
    public final SamplerUniform floor_tex = testShader.findSampler("Floor");

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
        Vector3f cameraPos = camera.getPos().toVector3f();
        uniformCameraPosition.set(cameraPos.x, cameraPos.y, cameraPos.z);
        Entity e = camera.getFocusedEntity();
        testShader.render(tickDelta);

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