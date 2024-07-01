package org.shadowmaster435.limbo.util.functions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.callable.Condition;

import java.util.HashMap;

public interface GenericBlockFunctions {
    //region Box Functions
    default boolean has_block_in_box(Box box, World world, Block block) {
        var iter = new CuboidBlockIterator((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            
            var id = Registries.BLOCK.getId(world.getBlockState(pos).getBlock()).toString();
            var compare_id = Registries.BLOCK.getId(block).toString();
            
            if (compare_id.equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    default HashMap<String, Integer> count_blocks_in_box(Box box, World world, boolean exclude_air) {
        var iter = new CuboidBlockIterator((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
        HashMap<String, Integer> result = new HashMap<>();
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (world.isAir(pos) && exclude_air) {
                continue;
            }
            var id = Registries.BLOCK.getId(world.getBlockState(pos).getBlock()).toString();
            result.put(id, result.getOrDefault(id, 0) + 1);
        }
        return result;
    }
    
    default HashMap<BlockPos, Block> get_blocks_in_box(Box box, World world, boolean exclude_air) {
        var iter = new CuboidBlockIterator((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
        HashMap<BlockPos, Block> result = new HashMap<>();
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (world.isAir(pos) && exclude_air) {
                continue;
            }
            result.put(pos, world.getBlockState(pos).getBlock());
        }
        return result;
    }
    
    default HashMap<BlockPos, BlockState> get_states_in_box(Box box, World world, boolean exclude_air) {
        var iter = new CuboidBlockIterator((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
        HashMap<BlockPos, BlockState> result = new HashMap<>();
        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (world.isAir(pos) && exclude_air) {
                continue;
            }
            result.put(pos, world.getBlockState(pos));
        }
        return result;
    }
    //endregion
    //region setters
    default void set_block(Block block, BlockPos pos, World world) {
        world.setBlockState(pos, block.getDefaultState(), Block.NOTIFY_ALL);
    }
    default void set_blockstate(BlockState state, BlockPos pos, World world) {
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }
    default void set_block_if(Block block, BlockPos pos, World world, Condition condition) {
        if (condition.test()) {
            world.setBlockState(pos, block.getDefaultState(), Block.NOTIFY_ALL);
        }
    }
    default void set_blockstate_if(BlockState state, BlockPos pos, World world, Condition condition) {
        if (condition.test()) {
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
    }
    //endregion
    //region getters
    default Block get_block(BlockPos pos, World world) {
        return get_blockstate(pos, world).getBlock();
    }
    default BlockState get_blockstate(BlockPos pos, World world) {
        return world.getBlockState(pos);
    }
    default Identifier get_block_id(Block block) {
        return Registries.BLOCK.getId(block);
    }
    default Block get_block_from_id(Identifier identifier) {
        return Registries.BLOCK.get(identifier);
    }
    default Block get_block_from_id(String identifier) {
        return Registries.BLOCK.get(new Identifier(identifier));
    }
    default BlockPos get_hit_blockpos(Vec3d start, Vec3d end, World world) {
        return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, ShapeContext.absent())).getBlockPos();
    }
    default Direction get_hit_side(Vec3d start, Vec3d end, World world) {
        return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, ShapeContext.absent())).getSide();
    }
    default Vec3d get_hit_pos(Vec3d start, Vec3d end, World world) {
        return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, ShapeContext.absent())).getPos();
    }
    default BlockState get_hit_state(Vec3d start, Vec3d end, World world) {
        return world.getBlockState(world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, ShapeContext.absent())).getBlockPos());
    }
    default Block get_hit_block(Vec3d start, Vec3d end, World world) {
        return get_hit_state(start, end, world).getBlock();
    }
    
    //endregion
    //region Other
    default BlockPos to_block_pos(Vec3d pos) {
        return new BlockPos((int) Math.round(pos.x), (int) Math.round(pos.y), (int) Math.round(pos.z));
    }

    default BlockPos to_block_pos(Vector3f pos) {
        return new BlockPos(Math.round(pos.x), Math.round(pos.y), Math.round(pos.z));

    }
    default Vec3d to_vec3d(Vec3i pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }
    default Vec3d to_vec3d(BlockPos pos) {
        return pos.toCenterPos().subtract(0.5,0.5,0.5);
    }
    default BlockPos div_blockpos(BlockPos pos, int i) {
        return new BlockPos(Math.round(pos.getX() / ((float) i)), Math.round(pos.getY() / ((float) i)), Math.round(pos.getZ() / ((float) i)));
    }
    default BlockPos div_blockpos(BlockPos pos, double i) {
        return new BlockPos((int) Math.round(pos.getX() / i), (int) Math.round(pos.getY() / i), (int) Math.round(pos.getZ() / i));
    }
    default BlockPos div_blockpos(BlockPos pos, float i) {
        return new BlockPos(Math.round(pos.getX() / i), Math.round(pos.getY() / i), Math.round(pos.getZ() / i));
    }
    //endregion
}
