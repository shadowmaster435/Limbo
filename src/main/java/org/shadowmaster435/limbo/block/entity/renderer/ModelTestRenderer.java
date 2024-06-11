package org.shadowmaster435.limbo.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.entity.ModelTestEntity;
import org.shadowmaster435.limbo.util.RenderHelper;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntityRenderer;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuadStripPipe;
import org.shadowmaster435.limbo.util.render.util.VertexFunction;

public class ModelTestRenderer implements AnimatedBlockEntityRenderer<ModelTestEntity> {

    public static final TexturedQuadStripPipe quad_test = TexturedQuadStripPipe.create(16, new Vector3f(1,1,1), new Vector2f(0.0f, 1f),new Vector2f(0f, 1f));

    public ModelTestRenderer(BlockEntityRendererFactory.Context ctx) {

    }
    public static final Identifier tex = new Identifier(Limbo.id, "textures/block/dim_ore.png");
    @Override
    public void render(ModelTestEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var p = MinecraftClient.getInstance().player;
      //  matrices.translate(0.5,0,0.5);
     //   RenderHelper.face_matrix_towards(entity.getPos().toCenterPos(), p.getLerpedPos(tickDelta).add(0, p.getStandingEyeHeight(),0).add(p.getWidth() / 2,0, p.getWidth() / 2f), matrices);
      //  matrices.scale(1,-1,1);
        quad_test.render(matrices, tex, light, overlay, new VertexFunction((i) -> getp(i, tickDelta, entity)));

        //        entity.set_animation_counter(entity.getWorld().getTime());
//        AnimatedBlockEntityRenderer.super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
//        entity.animation_state.startIfNotRunning((int) MinecraftClient.getInstance().world.getTime());
    }

    public Vector3f getp(int i, float tickDelta, ModelTestEntity entity) {
        var ofs = (Math.sin(((((entity.getWorld().getTime() + tickDelta) + (i * 4f)) / 32)))) / 8f;
        var ofs2 = (Math.cos(((((entity.getWorld().getTime() + tickDelta) + (i * 4f)) / 32)))) / 8f;

        return new Vector3f((float) ofs, (float) 0, (float) ofs2);
    }

}
