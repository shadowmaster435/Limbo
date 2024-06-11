package org.shadowmaster435.limbo.util;

import net.minecraft.util.math.MathHelper;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;
import org.shadowmaster435.limbo.util.render.util.VertexGroupFunction;

import java.util.ArrayList;

public class BindableTimer extends Timer {
    
    
    private final String field_name;
    
    private final boolean invert_delta;

    private final float min_value;

    private final float max_value;

    private boolean started = false;
    
    
    public BindableTimer(String field_name) {
        this.field_name = field_name;
        this.invert_delta = false;
        this.min_value = 0;
        this.max_value = 1;
        ArrayList<Vector3> verts = new ArrayList<>();
        var func = new VertexFunction(index -> new Vector3(index, 0,0));
        var f = new VertexGroupFunction(verts, func);
        f.run();
    }

    public Vector3 funcs(int a) {
        return Vector3.ZERO;
    }

    public BindableTimer(String field_name, boolean invert_delta) {
        this.field_name = field_name;
        this.invert_delta = invert_delta;
        this.min_value = 0;
        this.max_value = 1;
    }

    public BindableTimer(String field_name, boolean invert_delta, float min_value, float max_value) {
        this.field_name = field_name;
        this.invert_delta = invert_delta;
        this.min_value = min_value;
        this.max_value = max_value;
    }
    public void update(Object bound_instance) {
        if (started) {
            super.update();
        }
        try {
            var delta = (invert_delta) ? 1 - delta() : delta();
            var val = MathHelper.lerp(delta, min_value, max_value);
            bound_instance.getClass().getField(field_name).setFloat(bound_instance, val);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start() {
        super.start();
        started = true;
    }
}
