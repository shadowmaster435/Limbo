package org.shadowmaster435.limbo.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.shadowmaster435.limbo.block.entity.model.LuminatorModel;
import org.shadowmaster435.limbo.block.entity.model.TestModel;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntity;

public class LuminatorEntity extends AnimatedBlockEntity {

    public LuminatorEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LUMINATOR_BLOCK_ENTITY, pos, state);
        setModel(new LuminatorModel(LuminatorModel.getTexturedModelData().createModel()));
        set_current_animation(LuminatorModel.idle);
    }
}
