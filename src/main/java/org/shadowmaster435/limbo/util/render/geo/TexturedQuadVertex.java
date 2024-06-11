package org.shadowmaster435.limbo.util.render.geo;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public record TexturedQuadVertex(Vector3f position, Vector2f uv) {



    public void render(MatrixStack matricies, BufferBuilder consumer, int light, int overlay, int index, VertexFunction vertexFunction) {
        var ofs = vertexFunction.run(index);
        consumer.vertex(matricies.peek().getPositionMatrix(), position.x + ofs.x, position.y + ofs.y, position.z + ofs.z).color(1f,1f,1f,1f).texture(uv.x, uv.y).light(light).next();

    }

    @Override
    public String toString() {
        return "Position:" + position.x + "|" + position.y + "|" + position.z + " UV:" + uv.x + "|" + uv.y;
    }
}
