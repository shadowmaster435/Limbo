package org.shadowmaster435.limbo.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.entity.ModelTestEntity;
import org.shadowmaster435.limbo.init.ModShaders;
import org.shadowmaster435.limbo.shader.CollapseOrbDepthFX;
import org.shadowmaster435.limbo.shader.DepthRenderLayerShader;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.RenderHelper;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntityRenderer;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuadStripPipe;
import org.shadowmaster435.limbo.util.render.texture.ByteBufferTexture;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

import java.nio.ByteBuffer;

public class ModelTestRenderer implements AnimatedBlockEntityRenderer<ModelTestEntity> {

    public static final TexturedQuadStripPipe quad_test = TexturedQuadStripPipe.create(16, new Vector3f(1,1,1), new Vector2f(0.0f, 1f),new Vector2f(0f, 1f));

    public ModelTestRenderer(BlockEntityRendererFactory.Context ctx) {

    }
    public final ByteBufferTexture byte_tex = new ByteBufferTexture(128, 128);

    public static final Identifier tex = new Identifier(Limbo.id, "textures/block/dim_ore.png");
    @Override
    public void render(ModelTestEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var p = MinecraftClient.getInstance().player;

        quad_test.render(matrices, tex, light, overlay, new VertexFunction((i) -> getp(i, tickDelta, entity)));
        try {

//            buffer.clear();
//            buffer.put(new byte[] {127,-128, -128, 127, -128, 127, -128, 127,
//                    -128, -128, 127, 127, -128, -128, -128, 127});
//            buffer.compact();
//            byte_tex.write(buffer);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Vector3f getp(int i, float tickDelta, ModelTestEntity entity) {
        var ofs = (Math.sin(((((entity.getWorld().getTime() + tickDelta) + (i * 4f)) / 32)))) / 8f;
        var ofs2 = (Math.cos(((((entity.getWorld().getTime() + tickDelta) + (i * 4f)) / 32)))) / 8f;

        return new Vector3f((float) ofs, (float) 0, (float) ofs2);
    }

}
