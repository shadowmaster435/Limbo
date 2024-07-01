package org.shadowmaster435.limbo.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.limbo.block.base.AbstractInteractableBlock;
import org.shadowmaster435.limbo.block.entity.FeatureTestEntity;
import org.shadowmaster435.limbo.init.ModBlocks;

public class ForceFieldBlock extends AbstractInteractableBlock {



    public ForceFieldBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public void open(PlayerEntity player) {
        var hand_item = player.getMainHandStack();
    }

}
