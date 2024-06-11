package org.shadowmaster435.limbo.util.render.geo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.Vector3;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public record TexturedQuad(TexturedQuadVertex bl, TexturedQuadVertex br, TexturedQuadVertex tl, TexturedQuadVertex tr) {


    public static TexturedQuad create_vertical_x(Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        var bl = new TexturedQuadVertex(new Vector3f(0,0,depth_ofs), new Vector2f(u.x,v.x));
        var br = new TexturedQuadVertex(new Vector3f(size.x,0,depth_ofs), new Vector2f(u.y,v.x));
        var tl = new TexturedQuadVertex(new Vector3f(0,size.y,depth_ofs), new Vector2f(u.x,v.y));
        var tr = new TexturedQuadVertex(new Vector3f(size.x,size.y,depth_ofs), new Vector2f(u.y,v.y));

        return new TexturedQuad(bl, br, tl, tr);
    }
    public static TexturedQuad create_vertical_z(Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        var bl = new TexturedQuadVertex(new Vector3f(depth_ofs,0,0), new Vector2f(u.x,v.x));
        var br = new TexturedQuadVertex(new Vector3f(depth_ofs,0,size.x), new Vector2f(u.y,v.x));
        var tl = new TexturedQuadVertex(new Vector3f(depth_ofs,size.y,0), new Vector2f(u.x,v.y));
        var tr = new TexturedQuadVertex(new Vector3f(depth_ofs,size.y,size.x), new Vector2f(u.y,v.y));

        return new TexturedQuad(bl, br, tl, tr);
    }

    public void render(MatrixStack matricies, Identifier texture, int light, int overlay, int index, VertexFunction vertexFunction) {
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
