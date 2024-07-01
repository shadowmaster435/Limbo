package org.shadowmaster435.limbo.shader;

import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;

public class LimboSkyShader extends DepthRenderLayerShader {

    public static final Identifier id = new Identifier(Limbo.id, "limbo_sky.json");
    public static final LimboSkyShader INSTANCE = new LimboSkyShader("limbo_sky");
    public Uniform1f time;



    public int ticks = 0;

    public LimboSkyShader(String name) {
        super(name);
    }

    @Override
    public void register() {
        super.register();
        time =  core.findUniform1f("time");
    }

    @Override
    public void onEntitiesRendered(Camera camera, Frustum frustum, float tickDelta) {

    }

    @Override
    public void renderShaderEffects(float tickDelta) {
        INSTANCE.core.findUniform1f("time").set(ticks + tickDelta);
    }

    @Override
    public void onEndTick(MinecraftClient client) {
        ticks += 1;
    }
}
