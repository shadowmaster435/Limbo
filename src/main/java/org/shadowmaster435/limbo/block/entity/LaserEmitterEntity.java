package org.shadowmaster435.limbo.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.block.LaserRedirectorBlock;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.util.MiscUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LaserEmitterEntity extends BlockEntity {
    public int distance;


    public LaserEmitterEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LASER_EMITTER_BLOCK_ENTITY, pos, state);
    }


    public boolean hit_target(World world, BlockPos pos) {
        this.distance = ray(world, pos).getManhattanDistance(pos);
        return propagate(world, pos, 0, 32);
    }

    public boolean propagate(World world, BlockPos pos, int depth, int max_depth) {
        var result = false;
        var hit_pos = ray(world, pos);
        BlockEntity ent = world.getBlockEntity(pos);
        if (ent != null) {
            if (ent instanceof LaserRedirectorEntity e) {
                e.hit_by = this.getPos();
                e.distance = hit_pos.getManhattanDistance(pos);
            }
        }
        if (can_propagate(world, hit_pos, depth, max_depth)) {

            result = propagate(world, hit_pos, depth + 1, max_depth);
        } else if (is_target(world, hit_pos)) {
            return is_target(world, hit_pos);
        }

        return result;
    }

    public BlockPos ray(World world, BlockPos pos) {
        return MiscUtil.dir_raycast(world.getBlockState(pos).get(Properties.FACING), pos,world, 32).getBlockPos();
    }

    public boolean can_propagate(World world, BlockPos hit_pos, int depth, int max_depth) {
        return world.getBlockState(hit_pos).getBlock() == ModBlocks.LASER_REDIRECTOR && depth < max_depth;
    }

    public boolean is_target(World world, BlockPos hit_pos) {
        return world.getBlockState(hit_pos).getBlock() == Blocks.TARGET;

    }

    public static void tick(World world, BlockPos pos, BlockState state, LaserEmitterEntity be) {
        if (be.hit_target(world, pos)) {
            System.out.println("yay");
        }
    }

}
