package org.shadowmaster435.limbo.util.block_entity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public interface AnimatedBlockEntityRenderer<T extends AnimatedBlockEntity> extends BlockEntityRenderer<T> {

    @Override
    default void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var model = entity.getModel();
        var anim = entity.get_current_animation();
        if (model != null) {
            matrices.scale(1, -1, 1);
            matrices.translate(0.5,-1.5f,0.5);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(entity.getModel().get_texture())), light, overlay, 1f, 1f, 1f, 1f);

            if (anim != null) {
                model.updateAnimation(entity.animation_state, entity.get_current_animation(), entity.get_animation_counter() + tickDelta);
            }

        }


    }


}
