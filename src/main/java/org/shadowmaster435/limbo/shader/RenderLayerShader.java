package org.shadowmaster435.limbo.shader;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.systems.VertexSorter;

import ladysnake.satin.api.event.EntitiesPostRenderCallback;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.RenderLayerHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.resource.ModResourceLoader;

import java.util.function.Supplier;

public abstract class RenderLayerShader implements PostWorldRenderCallbackV2, EntitiesPostRenderCallback, ShaderEffectRenderCallback, ClientTickEvents.EndTick {

    public String target = "final";

    public final String name;

    public ManagedShaderEffect effect;
    public ManagedCoreShader core;
    public ManagedFramebuffer buffer;
    public RenderLayer layer;

    public RenderLayerShader(String name) {
        this("final", name);
    }

    public RenderLayerShader(String target, String name) {
        this.name = name;
    }
    public void register_all() {
        register();
        register_block_layer();
        register_auto_render(this);
    }
    public void register_core() {
        core = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Limbo.id, name), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
        // buffer =((ReadableDepthFramebuffer)MinecraftClient.getInstance().getFramebuffer()).getStillDepthMap() ;

        //     this.layer = buffer.getRenderLayer(RenderLayer.getTranslucent());
    }

    public void register() {
        effect = ShaderEffectManager.getInstance().manage(new Identifier(Limbo.id, "shaders/post/" + name + ".json"));
        buffer = effect.getTarget(target);

        this.layer = buffer.getRenderLayer(RenderLayer.getTranslucent());
    }

    public void register_block_layer() {
        RenderLayerHelper.registerBlockRenderLayer(layer);

    }

    public <T extends RenderLayerShader> void register_auto_render(T effect) {
        ClientTickEvents.END_CLIENT_TICK.register(effect);
        ShaderEffectRenderCallback.EVENT.register(effect);
        PostWorldRenderCallbackV2.EVENT.register(effect);
    }


    @Override
    public abstract void onEntitiesRendered(Camera camera, Frustum frustum, float tickDelta);
    @Override
    public abstract void onWorldRendered(MatrixStack posingStack, Camera camera, float tickDelta, long nanoTime);

    @Override
    public abstract void renderShaderEffects(float tickDelta);

    @Override
    public abstract void onEndTick(MinecraftClient client);
}
