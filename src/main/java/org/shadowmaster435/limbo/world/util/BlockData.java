package org.shadowmaster435.limbo.world.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Optional;

public record BlockData(NbtCompound blockstate, Optional<NbtCompound> block_entity_data) {


    public static BlockData from_nbt(NbtCompound nbt) {
        NbtCompound state = nbt.getCompound("blockstate");
        NbtCompound be_data = null;
        if (nbt.contains("block_entity_data")) {
            be_data = nbt.getCompound("block_entity_data");
        }
        return new BlockData(state, Optional.ofNullable(be_data));
    }

    public static BlockData create_from(World world, BlockPos pos) {
        HashMap<String, Object> data = new HashMap<>();
        var state = world.getBlockState(pos);
        var state_nbt = NbtHelper.fromBlockState(state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        DynamicRegistryManager dynamicRegistryManager = world.getRegistryManager();
        NbtCompound be_nbt = null;
        if (blockEntity != null) {
            be_nbt = blockEntity.createNbtWithId(dynamicRegistryManager);
        }
        return new BlockData(state_nbt, Optional.ofNullable(be_nbt));
    }

    public void place(World world, BlockPos pos) {
        var state = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), blockstate);
        BlockEntity blockEntity = null;

        if (block_entity_data.isPresent()) {

            blockEntity = BlockEntity.createFromNbt(pos,state,block_entity_data.get(), world.getRegistryManager());
        }
        if (state.getBlock().equals(Blocks.AIR)) {
            if (world.getBlockEntity(pos) != null) {
                world.removeBlockEntity(pos);
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

        } else {
            world.setBlockState(pos, state, 11);

        }
        if (blockEntity != null) {
            world.addBlockEntity(blockEntity);
        }
    }

    public NbtCompound to_nbt() {
        var res = new NbtCompound();
        var new_compound = new NbtCompound();
        if (block_entity_data.isPresent()) {
            new_compound.put("block_entity_data", block_entity_data.get());
            res = res.copyFrom(new_compound);
        }
        res.copyFrom(blockstate);
        return res;
    }

}
