package org.shadowmaster435.limbo.util.render.util;

import org.shadowmaster435.limbo.util.Vector3;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public record VertexGroupFunction(ArrayList<Vector3> dest, VertexFunction func) {

    public void run() {
        for (int i = 0; i < dest.size(); ++i) {
            var vec = dest.get(i);
            vec.set(func.run(i));
        }
    }

}