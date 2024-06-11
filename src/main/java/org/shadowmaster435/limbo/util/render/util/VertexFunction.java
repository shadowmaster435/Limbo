package org.shadowmaster435.limbo.util.render.util;

import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.Vector3;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Supplier;

public record VertexFunction(Function<Integer, Vector3f> func) {

    public Vector3f run() {
        return func.apply(0);

    }

    public Vector3f run(int index) {
        return func.apply(index);

    }

}
