package org.shadowmaster435.limbo.block.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.entity.LuminatorEntity;
import org.shadowmaster435.limbo.block.entity.ModelTestEntity;
import org.shadowmaster435.limbo.util.model.SinglePartBlockEntityModel;

public class LuminatorModel extends SinglePartBlockEntityModel<LuminatorEntity> {
    //region Animations
    public static final Animation idle_no_crystal = Animation.Builder.create(2.0F).looping()
            .addBoneAnimation("dim_shard", new Transformation(Transformation.Targets.SCALE,
                    new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("north_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("south_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom2", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("east_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom3", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("west_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom4", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .build();

    public static final Animation idle = Animation.Builder.create(2.0F).looping()
            .addBoneAnimation("dim_shard", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(26.5528F, 0.0F, -39.2365F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(26.5528F, -360.0F, -39.2365F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("dim_shard", new Transformation(Transformation.Targets.SCALE,
                    new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("north_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("bottom", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("south_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("bottom2", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("east_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom3", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("bottom3", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("west_platform", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("bottom4", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("bottom4", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("top", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("top2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("top3", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("top4", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("dim_shard_parent", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 79.0F), Transformation.Interpolations.CUBIC)
            ))
            .addBoneAnimation("dim_shard_parent", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.5F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                    new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
            ))
            .build();
    //endregion
    //region Vars
    public final ModelPart root;
    private final ModelPart base;
    private final ModelPart dim_shard_parent;
    private final ModelPart dim_shard;
    private final ModelPart pedestal;
    private final ModelPart platforms;
    private final ModelPart west_platform;
    private final ModelPart bottom4;
    private final ModelPart top4;
    private final ModelPart north_platform;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart south_platform;
    private final ModelPart bottom2;
    private final ModelPart top2;
    private final ModelPart east_platform;
    private final ModelPart bottom3;
    private final ModelPart top3;
    //endregion
    public LuminatorModel(ModelPart root) {
        super(new Identifier(Limbo.id, "textures/block/luminator.png"));
        this.root = root;
        this.base = root.getChild("model");
        this.dim_shard_parent = base.getChild("dim_shard_parent");
        this.dim_shard = dim_shard_parent.getChild("dim_shard");
        this.pedestal = base.getChild("pedestal");
        this.platforms = base.getChild("platforms");
        this.west_platform = platforms.getChild("west_platform");
        this.bottom4 = west_platform.getChild("bottom4");
        this.top4 = west_platform.getChild("top4");
        this.north_platform = platforms.getChild("north_platform");
        this.bottom = north_platform.getChild("bottom");
        this.top = north_platform.getChild("top");
        this.south_platform = platforms.getChild("south_platform");
        this.bottom2 = south_platform.getChild("bottom2");
        this.top2 = south_platform.getChild("top2");
        this.east_platform = platforms.getChild("east_platform");
        this.bottom3 = east_platform.getChild("bottom3");
        this.top3 = east_platform.getChild("top3");
    }
    //region Getters
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("model", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

        ModelPartData dim_shard_parent = base.addChild("dim_shard_parent", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, 0.0F));

        ModelPartData dim_shard = dim_shard_parent.addChild("dim_shard", ModelPartBuilder.create().uv(0, 8).cuboid(-0.229F, -1.771F, -1.7709F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-0.9744F, -1.0256F, -1.0256F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-1.2966F, 0.2966F, 0.2965F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.4833F, 0.0122F, -0.4634F, -0.4204F, -0.6848F));

        ModelPartData pedestal = base.addChild("pedestal", ModelPartBuilder.create().uv(0, 12).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(16, 0).cuboid(-3.0F, -7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData platforms = base.addChild("platforms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData west_platform = platforms.addChild("west_platform", ModelPartBuilder.create(), ModelTransform.pivot(8.0F, -7.5F, 0.0F));

        ModelPartData bottom4 = west_platform.addChild("bottom4", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData top4 = west_platform.addChild("top4", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData north_platform = platforms.addChild("north_platform", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.5F, -8.0F));

        ModelPartData bottom = north_platform.addChild("bottom", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData top = north_platform.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData south_platform = platforms.addChild("south_platform", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.5F, 8.0F));

        ModelPartData bottom2 = south_platform.addChild("bottom2", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData top2 = south_platform.addChild("top2", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData east_platform = platforms.addChild("east_platform", ModelPartBuilder.create(), ModelTransform.pivot(-8.0F, -7.5F, 0.0F));

        ModelPartData bottom3 = east_platform.addChild("bottom3", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData top3 = east_platform.addChild("top3", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }



    @Override
    public ModelPart getPart() {
        return root;
    }
    //endregion
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
