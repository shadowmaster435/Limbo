package org.shadowmaster435.limbo.util.block_entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.BlockPos;
import org.shadowmaster435.limbo.util.model.SinglePartBlockEntityModel;

public class AnimatedBlockEntity extends BlockEntity {

    private SinglePartBlockEntityModel<? extends AnimatedBlockEntity> model;
    private long animation_counter = 0;
    public AnimationState animation_state;
    private Animation current_animation;

    public AnimatedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        animation_state = new AnimationState();
    }

    public Animation get_current_animation() {
        return current_animation;
    }

    public long get_animation_counter() {
        return animation_counter;
    }

    public void set_animation_counter(long value) {
        animation_counter = value;
    }

    public void set_current_animation(Animation animation) {
        this.current_animation = animation;
    }

    public void setModel(SinglePartBlockEntityModel<? extends AnimatedBlockEntity> model) {
        this.model = model;
    }



    public SinglePartBlockEntityModel<? extends AnimatedBlockEntity> getModel() {
        return this.model;
    }

}
