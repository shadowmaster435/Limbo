package org.shadowmaster435.limbo.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.shadowmaster435.limbo.entity.MonolithEntity;

public class MonolithModel extends EntityModel<MonolithEntity> {
    private final ModelPart body;
    private final ModelPart eye;
    public MonolithModel(ModelPart root) {
        this.body = root.getChild("body");
        this.eye = body.getChild("eye");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(-4, 0).cuboid(-16.0F, -30.0F, -1.0F, 32.0F, 29.0F, 2.0F, new Dilation(0.0F))
                .uv(-4, 0).cuboid(-16.0F, -1.0F, -1.0F, 32.0F, 29.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData eye = body.addChild("eye", ModelPartBuilder.create().uv(8, 45).cuboid(-9.5F, -2.0F, -0.75F, 19.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -1.25F, 0.0F, 0.0F, -3.1416F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(MonolithEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        body.setAngles((float) Math.toRadians(180 - headPitch), (float) Math.toRadians(netHeadYaw + 180), 0);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, 15728864, overlay, red, green, blue, alpha);
    }
}