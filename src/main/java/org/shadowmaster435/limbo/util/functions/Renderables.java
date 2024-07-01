package org.shadowmaster435.limbo.util.functions;

import net.minecraft.util.math.Direction;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuad;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public interface Renderables {


    //region Textured Quad
    default TexturedQuad create_quad(Direction.Axis axis, Vector2f size, Vector2f uv, Vector3f ofs) {
        TexturedQuad result = null;
        switch (axis) {
            case X -> result = TexturedQuad.create_vertical_x(size, new Vector2f(0,0), uv, ofs);
            case Y -> result = TexturedQuad.create_horizontal(size, new Vector2f(0,0), uv, ofs);
            case Z -> result = TexturedQuad.create_vertical_z(size, new Vector2f(0,0), uv, ofs);
        }
        return result;
    }


    default TexturedQuad create_quad(Direction.Axis axis, Vector2f size, Vector2f uv, float depth_ofs) {
        TexturedQuad result = null;
        switch (axis) {
            case X -> result = TexturedQuad.create_vertical_x(size, new Vector2f(0,0), uv, depth_ofs);
            case Y -> result = TexturedQuad.create_horizontal(size, new Vector2f(0,0), uv, depth_ofs);
            case Z -> result = TexturedQuad.create_vertical_z(size, new Vector2f(0,0), uv, depth_ofs);
        }
        return result;
    }

    default TexturedQuad create_quad(Direction.Axis axis, Vector2f size, Vector2f u, Vector2f v, Vector3f ofs) {
        TexturedQuad result = null;
        switch (axis) {
            case X -> result = TexturedQuad.create_vertical_x(size, u, v, ofs);
            case Y -> result = TexturedQuad.create_horizontal(size, u, v, ofs);
            case Z -> result = TexturedQuad.create_vertical_z(size, u, v, ofs);
        }
        return result;
    }


    default TexturedQuad create_quad(Direction.Axis axis, Vector2f size, Vector2f u, Vector2f v, float depth_ofs) {
        TexturedQuad result = null;
        switch (axis) {
            case X -> result = TexturedQuad.create_vertical_x(size, u, v, depth_ofs);
            case Y -> result = TexturedQuad.create_horizontal(size, u, v, depth_ofs);
            case Z -> result = TexturedQuad.create_vertical_z(size, u, v, depth_ofs);
        }
        return result;
    }
    //endregion
}
