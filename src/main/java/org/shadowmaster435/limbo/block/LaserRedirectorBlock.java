package org.shadowmaster435.limbo.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.limbo.block.entity.LaserEmitterEntity;
import org.shadowmaster435.limbo.block.entity.LaserRedirectorEntity;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.util.MiscUtil;

import java.util.Objects;

public class LaserRedirectorBlock extends BlockWithEntity {



    public LaserRedirectorBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.FACING, Direction.UP));

    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }


    public static boolean hitting_other_redirector(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == ModBlocks.LASER_REDIRECTOR) {
            var result = MiscUtil.dir_raycast(world.getBlockState(pos).get(Properties.FACING), pos, world, 32);
            var ray = MiscUtil.to_block_pos(result.getPos());
           // System.out.println(ray);
            return world.getBlockState(ray).getBlock() == ModBlocks.LASER_REDIRECTOR;
        }
        return false;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LaserRedirectorEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.FACING, ctx.getPlayerLookDirection());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.FACING);
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlocks.LASER_REDIRECTOR_BLOCK_ENTITY, LaserRedirectorEntity::tick);
    }
}
