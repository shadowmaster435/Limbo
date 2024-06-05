package org.shadowmaster435.limbo.shader;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.systems.VertexSorter;

import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.resource.ModResourceLoader;

import java.util.function.Supplier;

public class RenderLayerShader {

    public String target = "final";

    public final String name;

    public ManagedShaderEffect effect;
    public ManagedFramebuffer buffer;
    public RenderLayer layer;


    private Supplier<Void> shader_callback_method;

    public RenderLayerShader(String name) {
        this("final", name);
    }

    public RenderLayerShader(String target, String name) {
        this.name = name;

    }

    public void register() {
        effect = ShaderEffectManager.getInstance().manage(new Identifier(Limbo.id, "shaders/post/" + name + ".json"));
       // buffer = effect.getTarget(target);
      //  RenderLayerHelper.registerBlockRenderLayer(layer);

        this.layer = buffer.getRenderLayer(RenderLayer.getTranslucent());
    }


    public void set_uniform(String name, int value) {
        effect.setUniformValue(name, value);
    }

    public void set_uniform(String name, float value) {
        effect.setUniformValue(name, value);
    }

    public void set_uniform(String name, String texture_name) {
        effect.setSamplerUniform(name, ModResourceLoader.shader_textures.get(new Identifier(Limbo.id, texture_name)));
    }
/*    public void set_uniform(String name, float[] value) {
        shader.getProgram().getUniformOrDefault(name).set(value);
    }*/

    public RenderLayer create_layer() {
        return new RenderLayer(name, VertexFormats.POSITION, VertexFormat.DrawMode.QUADS, 1536, false, true, Runnables.doNothing(), Runnables.doNothing()) {
            @Override
            public void draw(BufferBuilder buffer, VertexSorter sorter) {
                super.draw(buffer, sorter);
            }
        };
    }




}
