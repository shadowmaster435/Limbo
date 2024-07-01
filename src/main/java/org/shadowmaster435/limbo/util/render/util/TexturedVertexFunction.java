package org.shadowmaster435.limbo.util.render.util;

import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.render.geo.TexturedVertex;

import java.util.function.Function;

public record TexturedVertexFunction(Function<Integer, TexturedVertex> func) {

    public TexturedVertex run() {
        return func.apply(0);

    }

    public TexturedVertex run(int index) {
        return func.apply(index);
        
    }

}
