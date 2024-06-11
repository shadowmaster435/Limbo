package org.shadowmaster435.limbo.util.render.geo;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.Vector3;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

import java.util.ArrayList;
import java.util.Arrays;

public record TexturedQuadStrip(ArrayList<TexturedQuad> quads) {


    public void render(MatrixStack matricies, Identifier texture, int light, int overlay, VertexFunction vertexFunction) {
        for (int i = 0; i < quads.size(); ++i) {
            var quad = quads.get(i);
            quad.render(matricies, texture, light, overlay, i, vertexFunction);
        }
    }

    public static TexturedQuadStrip create_from_quad(int subdivisions, TexturedQuad quad) {
        var u = new Vector2f(quad.bl().uv().x, quad.br().uv().x);
        var v = new Vector2f(quad.bl().uv().y, quad.tl().uv().y);
        var left_start = quad.bl().position();
        var right_start = quad.br().position();
        var left_end = quad.tl().position();
        var right_end = quad.tr().position();
        ArrayList<Vector3f> left = new ArrayList<>();
        ArrayList<Vector3f> right = new ArrayList<>();
        for (int i = 0; i < subdivisions + 1; ++i) {
            var delta = (float) i / (float) subdivisions;
            var l = new Vector3(left_start).lerp(new Vector3(left_end), delta);
            var r = new Vector3(right_start).lerp(new Vector3(right_end), delta);
            left.add(l);
            right.add(r);

        }

        ArrayList<TexturedQuad> quad_list = new ArrayList<>();
        for (int i = 0; i < left.size() -1; ++i) {
            quad_list.add(create_strip_quad(i, u, v, left, right));
        }
        return new TexturedQuadStrip(quad_list);
    }


    private static TexturedQuad create_strip_quad(int index, Vector2f u, Vector2f v, ArrayList<Vector3f> left, ArrayList<Vector3f> right) {
        var first_index = Math.max(index, 0);
        var second_index = Math.min(left.size() - 1, index + 1);
        var first_v_delta = (float) first_index / (float) (left.size() - 1);
        var second_v_delta = (float) second_index / (float) (left.size() - 1);
        if (first_v_delta == second_v_delta) {
            second_v_delta = 1f;
        }
        var left_first = left.get(first_index);
        var right_first = right.get(first_index);
        var left_second = left.get(second_index);
        var right_second = right.get(second_index);
        var first_v = MathHelper.lerp(first_v_delta, v.x, v.y);
        var second_v = MathHelper.lerp(second_v_delta, v.x, v.y);
        var bl = new TexturedQuadVertex(left_first, new Vector2f(u.x, first_v));
        var br = new TexturedQuadVertex(right_first, new Vector2f(u.y, first_v));
        var tl = new TexturedQuadVertex(left_second, new Vector2f(u.x, second_v));
        var tr = new TexturedQuadVertex(right_second, new Vector2f(u.y, second_v));
        var q = new TexturedQuad(bl, br, tl, tr);

        return q;
    }

    @Override
    public String toString() {
        return "TexturedQuadStrip{" +
                "quads=" + Arrays.toString(quads.toArray()) +
                '}';
    }
}
