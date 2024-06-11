package org.shadowmaster435.limbo.util.render.geo;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public record TexturedQuadStripPipe(TexturedQuadStrip n, TexturedQuadStrip s, TexturedQuadStrip e, TexturedQuadStrip w) {


    public void render(MatrixStack matricies, Identifier texture, int light, int overlay, VertexFunction vertexFunction) {
        n.render(matricies, texture, light, overlay, vertexFunction);
        s.render(matricies, texture, light, overlay, vertexFunction);
        e.render(matricies, texture, light, overlay, vertexFunction);
        w.render(matricies, texture, light, overlay, vertexFunction);
    }

    public static TexturedQuadStripPipe create(int subdivisions, Vector3f size, Vector2f u, Vector2f v) {
        var n_quad = TexturedQuad.create_vertical_z(new Vector2f(size.x, size.y), u, v, 0);
        var s_quad = TexturedQuad.create_vertical_z(new Vector2f(size.x, size.y), u, v, size.z);
        var e_quad = TexturedQuad.create_vertical_x(new Vector2f(size.z, size.y), u, v, size.x);
        var w_quad = TexturedQuad.create_vertical_x(new Vector2f(size.z, size.y), u, v, 0);
        var n = TexturedQuadStrip.create_from_quad(subdivisions, n_quad);
        var s = TexturedQuadStrip.create_from_quad(subdivisions, s_quad);
        var e = TexturedQuadStrip.create_from_quad(subdivisions, e_quad);
        var w = TexturedQuadStrip.create_from_quad(subdivisions, w_quad);
        return new TexturedQuadStripPipe(n, s, e, w);
    }


}
