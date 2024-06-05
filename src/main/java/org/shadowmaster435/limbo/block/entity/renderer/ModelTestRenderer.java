package org.shadowmaster435.limbo.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.shadowmaster435.limbo.block.entity.ModelTestEntity;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntityRenderer;

public class ModelTestRenderer implements AnimatedBlockEntityRenderer<ModelTestEntity> {


    public ModelTestRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(ModelTestEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        entity.set_animation_counter(entity.getWorld().getTime());
        AnimatedBlockEntityRenderer.super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
        entity.animation_state.startIfNotRunning((int) MinecraftClient.getInstance().world.getTime());
    }
}
