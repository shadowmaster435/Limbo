package org.shadowmaster435.limbo.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.entity.MonolithEntity;
import org.shadowmaster435.limbo.entity.model.MonolithModel;
import org.shadowmaster435.limbo.init.ModEntities;

import javax.swing.text.html.parser.Entity;

public class MonolithEntityRenderer extends MobEntityRenderer<MonolithEntity, MonolithModel> {

    public MonolithEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MonolithModel(context.getPart(ModEntities.MODEL_MONOLITH_LAYER)), 0.5f);
    }

    @Override
    public void render(MonolithEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        var r = Random.createLocal();
        var lerp = MathHelper.lerp(Math.max((mobEntity.anger - 6), 0) / 5f, 0f, 0.125f);
        var x = r.nextFloat() * lerp;
        var y = r.nextFloat() * lerp;
        var z = r.nextFloat() * lerp;
        if (!mobEntity.isOnGround() && mobEntity.isDead()) {
            var vec = mobEntity.random_death_fall_rotation_vector.multiply(mobEntity.getVelocity().y);
            matrixStack.peek().getPositionMatrix().rotateX((float) vec.x);
            matrixStack.peek().getPositionMatrix().rotateY((float) vec.y);
            matrixStack.peek().getPositionMatrix().rotateZ((float) vec.z);

        }
        matrixStack.translate(x, y, z);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

    }

    @Override
    public Identifier getTexture(MonolithEntity entity) {
        if (entity.isDead()) {
            return new Identifier(Limbo.id, "textures/entity/monolith/monolith_dead.png");

        } else {
            return new Identifier(Limbo.id, "textures/entity/monolith/monolith_" + (entity.anger + 1) + ".png");
        }
    }
}