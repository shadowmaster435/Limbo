package org.shadowmaster435.limbo.util.model;


import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.ModAnimationHelper;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntity;

import java.util.Optional;
import java.util.function.Function;

public abstract class SinglePartBlockEntityModel<E extends AnimatedBlockEntity> extends BlockEntityModel<E> {

    private static final Vector3f TEMP = new Vector3f();

    public SinglePartBlockEntityModel(Identifier texture) {
        this(RenderLayer::getEntityCutoutNoCull, texture);
    }

    public SinglePartBlockEntityModel(Function<Identifier, RenderLayer> function, Identifier texture) {
        super(function, texture);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);

    }

    public abstract ModelPart getPart();

    public Optional<ModelPart> getChild(String name) {
        return name.equals("root") ? Optional.of(this.getPart()) : this.getPart().traverse().filter((part) -> part.hasChild(name)).findFirst().map((part) -> part.getChild(name));
    }

    public void updateAnimation(AnimationState animationState, Animation animation, float animationProgress) {
        this.updateAnimation(animationState, animation, animationProgress, 1.0F);
    }

    protected void animateMovement(Animation animation, float limbAngle, float limbDistance, float limbAngleScale, float limbDistanceScale) {
        long l = (long)(limbAngle * 50.0F * limbAngleScale);
        float f = Math.min(limbDistance * limbDistanceScale, 1.0F);
        ModAnimationHelper.animate(this, animation, l, f, TEMP);
    }

    protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, float speedMultiplier) {
        animationState.update(animationProgress, 1f);
        animationState.run((state) -> ModAnimationHelper.animate(this, animation, state.getTimeRunning(), 1.0F, TEMP));

    }

    public void animate(Animation animation, long run_time) {
        ModAnimationHelper.animate(this, animation, run_time , 1.0F, TEMP);
    }
}
