package org.shadowmaster435.limbo.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.shadowmaster435.limbo.block.entity.model.TestModel;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntity;

public class ModelTestEntity extends AnimatedBlockEntity {

    public ModelTestEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MODEL_TEST_ENT, pos, state);
        setModel(new TestModel(TestModel.getTexturedModelData().createModel()));
        set_current_animation(MiscUtil.animation);
    }
}
