package org.shadowmaster435.limbo.shader;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;

public class VanishingBlockShader extends DepthRenderLayerShader {

    public static final Identifier id = new Identifier(Limbo.id, "shaders/post/vanishing_block.json");
    public static final VanishingBlockShader INSTANCE = new VanishingBlockShader("vanishing_block");


    public VanishingBlockShader(String name) {
        super(name);
    }

    @Override
    public void register() {
        super.register();

    }

    @Override
    public void onEntitiesRendered(Camera camera, Frustum frustum, float tickDelta) {

    }

    @Override
    public void renderShaderEffects(float tickDelta) {
    }

    @Override
    public void onEndTick(MinecraftClient client) {

    }
}
