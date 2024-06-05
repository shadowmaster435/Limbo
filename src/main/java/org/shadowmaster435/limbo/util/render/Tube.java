package org.shadowmaster435.limbo.util.render;

import org.joml.Vector3f;

public record Tube(Vector3f[] mesh_points) {
    enum CapType {
        CONE(0),
        FLAT(1),
        ROUND(2);

        private final int type;

        CapType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

}
