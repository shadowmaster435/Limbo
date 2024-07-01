package org.shadowmaster435.limbo.util.render.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.entity.renderer.FeatureTestRenderer;
import org.shadowmaster435.limbo.block.entity.renderer.ModelTestRenderer;
import org.shadowmaster435.limbo.init.ModShaders;
import org.shadowmaster435.limbo.resource.ModResourceLoader;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

import java.io.IOException;
import java.util.function.Supplier;

public record TexturedQuad(TexturedVertex bl, TexturedVertex br, TexturedVertex tl, TexturedVertex tr) {

    public void bake(BufferBuilder buffer) {
        bl.bake(buffer);
        tl.bake(buffer);
        tr.bake(buffer);
        br.bake(buffer);
    }

    public BufferBuilder.BuiltBuffer bake() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bl.bake(buffer);
        tl.bake(buffer);
        tr.bake(buffer);
        br.bake(buffer);
        return buffer.end();
    }

    public static TexturedQuad create_vertical_x(Vector2f size, Vector2f u, Vector2f v, Vector3f ofs) {
        var bl = new TexturedVertex(ofs, new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(size.x,0,0).add(ofs), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(0,size.y,0).add(ofs), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(size.x,size.y,0).add(ofs), new Vector2f(u.y,v.y));
        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_vertical_z(Vector2f size, Vector2f u, Vector2f v, Vector3f ofs) {
        var bl = new TexturedVertex(ofs, new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(0,0,size.x).add(ofs), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(0,size.y,0).add(ofs), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(0,size.y,size.x).add(ofs), new Vector2f(u.y,v.y));

        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_horizontal(Vector2f size, Vector2f u, Vector2f v, Vector3f ofs) {
        var bl = new TexturedVertex(ofs, new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(size.x,0,0).add(ofs), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(0,0,size.y).add(ofs), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(size.x,0,size.y).add(ofs), new Vector2f(u.y,v.y));
        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_vertical_x(Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        var bl = new TexturedVertex(new Vector3f(0,0,depth_ofs), new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(size.x,0,depth_ofs), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(0,size.y,depth_ofs), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(size.x,size.y,depth_ofs), new Vector2f(u.y,v.y));
        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_vertical_z(Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        var bl = new TexturedVertex(new Vector3f(depth_ofs,0,0), new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(depth_ofs,0,size.x), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(depth_ofs,size.y,0), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(depth_ofs,size.y,size.x), new Vector2f(u.y,v.y));

        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_horizontal(Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        var bl = new TexturedVertex(new Vector3f(0,depth_ofs,0), new Vector2f(u.x,v.x));
        var br = new TexturedVertex(new Vector3f(size.x,depth_ofs,0), new Vector2f(u.y,v.x));
        var tl = new TexturedVertex(new Vector3f(0,depth_ofs,size.y), new Vector2f(u.x,v.y));
        var tr = new TexturedVertex(new Vector3f(size.x,depth_ofs,size.y), new Vector2f(u.y,v.y));
        return new TexturedQuad(bl, br, tl, tr);
    }

    public void render(MatrixStack matricies, String texture, int light, int overlay, int index, Supplier<ShaderProgram> program, @Nullable VertexFunction vertexFunction) {
        var tess = Tessellator.getInstance();
        var consumer = tess.getBuffer();
        RenderSystem.setShader(program);
        RenderSystem.setShaderTexture(0, new Identifier(texture));
        ModShaders.testShader.findSampler("SamplerTest").set(MinecraftClient.getInstance().getFramebuffer());
        ModShaders.testShader.findUniform3f("playerpos").set(MinecraftClient.getInstance().player.getLerpedPos(MinecraftClient.getInstance().getTickDelta()).toVector3f());

        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();

        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
        bl.render(matricies, consumer, light, overlay, index, vertexFunction);
        tl.render(matricies, consumer, light, overlay, index + 1, vertexFunction);
        tr.render(matricies, consumer, light, overlay, index + 1, vertexFunction);
        br.render(matricies, consumer, light, overlay, index, vertexFunction);
        BufferRenderer.drawWithGlobalProgram(consumer.end());
        RenderSystem.disableDepthTest();
        RenderSystem.enableCull();
    }


    public void render(MatrixStack matricies, Identifier texture, int light, int overlay, int index, @Nullable VertexFunction vertexFunction) {
        var tess = Tessellator.getInstance();
        var consumer = tess.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapProgram);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
        bl.render(matricies, consumer, light, overlay, index, vertexFunction);
        tl.render(matricies, consumer, light, overlay, index + 1, vertexFunction);
        tr.render(matricies, consumer, light, overlay, index + 1, vertexFunction);
        br.render(matricies, consumer, light, overlay, index, vertexFunction);
        BufferRenderer.drawWithGlobalProgram(consumer.end());
        RenderSystem.disableDepthTest();
        RenderSystem.enableCull();
    }

    @Override
    public String toString() {
        return "TexturedQuad{" +
                "bl=" + bl +
                ", br=" + br +
                ", tl=" + tl +
                ", tr=" + tr +
                '}';
    }
}
