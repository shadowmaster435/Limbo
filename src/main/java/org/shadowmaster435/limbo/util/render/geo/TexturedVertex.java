package org.shadowmaster435.limbo.util.render.geo;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.render.util.TexturedVertexFunction;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public record TexturedVertex(Vector3f position, Vector2f uv) {

    public void bake(BufferBuilder consumer) {

        consumer.vertex(position.x, position.y, position.z).color(1f,1f,1f,1f).texture(uv.x, uv.y).next();

    }

    public void render(MatrixStack matricies, BufferBuilder consumer, int light, int overlay, int index, @Nullable VertexFunction vertexFunction) {
        var ofs = new Vector3f(0);
        if (vertexFunction != null) {
            ofs = vertexFunction.run(index);
        }
        consumer.vertex(matricies.peek().getPositionMatrix(), position.x + ofs.x, position.y + ofs.y, position.z + ofs.z).color(1f,1f,1f,1f).texture(uv.x, uv.y).light(light).next();

    }

    public void render(MatrixStack matricies, BufferBuilder consumer, int light, int overlay, int index, TexturedVertexFunction vertexFunction) {
        var vec = vertexFunction.run(index);
        var ofs = vec.position;
        var uv_ofs = vec.uv;

        consumer.vertex(matricies.peek().getPositionMatrix(), position.x + ofs.x, position.y + ofs.y, position.z + ofs.z).color(1f,1f,1f,1f).texture(uv.x + uv_ofs.x, uv.y + uv_ofs.y).light(light).next();

    }

    @Override
    public String toString() {
        return "Position:" + position.x + "|" + position.y + "|" + position.z + " UV:" + uv.x + "|" + uv.y;
    }
}
