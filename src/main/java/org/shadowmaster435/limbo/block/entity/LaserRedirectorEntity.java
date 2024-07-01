package org.shadowmaster435.limbo.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.init.ModBlocks;

public class LaserRedirectorEntity extends BlockEntity {

    public int distance;

    public BlockPos hit_by = null;

    public LaserRedirectorEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LASER_REDIRECTOR_BLOCK_ENTITY, pos, state);
    }
    public static void tick(World world, BlockPos pos, BlockState state, LaserRedirectorEntity be) {
        if (be.hit_by == null || world.getBlockEntity(be.hit_by) == null) {
            be.distance = 0;
        }
    }
}
