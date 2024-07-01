package org.shadowmaster435.limbo.shader;

import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import ladysnake.satin.api.managed.uniform.UniformMat4;
import ladysnake.satin.api.util.GlMatrices;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public abstract class DepthRenderLayerShader extends RenderLayerShader {

    public UniformMat4 uniformInverseTransformMatrix;
    public Uniform3f uniformCameraPosition;
    public final Matrix4f projectionMatrix = new Matrix4f();

    private final MinecraftClient client = MinecraftClient.getInstance();

    public DepthRenderLayerShader(String name) {
        super(name);

    }

    @Override
    public void register() {
        super.register();
        this.uniformInverseTransformMatrix = effect.findUniformMat4("InverseTransformMatrix");
        this.uniformCameraPosition = effect.findUniform3f("CameraPosition");
    }

    @Override
    public void onWorldRendered(MatrixStack posingStack, Camera camera, float tickDelta, long nanoTime) {
        if (uniformInverseTransformMatrix != null) {
            uniformInverseTransformMatrix.set(GlMatrices.getInverseTransformMatrix(projectionMatrix));
        }
        if (uniformCameraPosition != null) {
            Vec3d cameraPos = camera.getPos();
            uniformCameraPosition.set((float)cameraPos.x, (float)cameraPos.y, (float)cameraPos.z);
        }
    }
}
