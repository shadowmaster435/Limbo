package org.shadowmaster435.limbo.world.subworld;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.*;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.world.util.BlockData;

import java.util.HashMap;

public class BlockArea {

    private final BlockPos size;

    private HashMap<BlockPos, BlockData> unloaded_blocks = new HashMap<>();

    public BlockArea(BlockPos min_pos, BlockPos max_pos, World world, boolean autoswap) {
        var p = min_pos.add(max_pos);
        size = new BlockPos(Math.absExact(p.getX()), Math.absExact(p.getY()), Math.absExact(p.getZ()));
        HashMap<BlockPos, BlockData> unloaded_blocks = new HashMap<>();
        var iter = new CuboidBlockIterator(min_pos.getX(), min_pos.getY(), min_pos.getZ(), max_pos.getX(), max_pos.getY(), max_pos.getZ());

        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            unloaded_blocks.put(pos, BlockData.create_from(world, pos));
            if (autoswap) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
            }
        }

        this.unloaded_blocks = unloaded_blocks;
    }

    public BlockArea(BlockPos size, HashMap<BlockPos, BlockData> unloaded_blocks) {
        this.size = size;
        this.unloaded_blocks = unloaded_blocks;
    }

    public void swap(World world, BlockPos pos) {
        HashMap<BlockPos, BlockData> temp = new HashMap<>();
        for (BlockPos offset: unloaded_blocks.keySet()) {
            var data = unloaded_blocks.get(offset);
            temp.put(offset, BlockData.create_from(world, pos.add(offset)));
            data.place(world, pos.add(offset));
        }
        unloaded_blocks = temp;
    }

    public NbtCompound to_nbt() {
        NbtCompound compound = new NbtCompound();
        NbtList entries = new NbtList();
        for (BlockPos pos : unloaded_blocks.keySet()) {
            var entry = unloaded_blocks.get(pos);
            var list = new NbtList();
            list.add(0, NbtHelper.fromBlockPos(pos));
            list.add(1, NbtString.of(entry.get_save_string()));
            entries.add(0, list);
        }
        compound.put("entries", entries);
        compound.put("area_size", NbtHelper.fromBlockPos(size));

        return to_nbt();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static BlockArea from_nbt(NbtCompound compound) {
        var size = NbtHelper.toBlockPos(compound, "area_size").get();
        HashMap<BlockPos, BlockData> data = new HashMap<>();
        var entries = (NbtList) compound.get("entries");
        assert entries != null;
        for (NbtElement entry : entries) {
            var element = (NbtList) entry;
            var pos_element = (NbtIntArray) element.get(0);
            var pos = new BlockPos(pos_element.get(0).intValue(), pos_element.get(1).intValue(), pos_element.get(2).intValue());
            NbtCompound str_data;
            try {
                str_data = NbtHelper.fromNbtProviderString(element.getString(1));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            data.put(pos, BlockData.from_nbt(str_data));
        }
        return new BlockArea(size, data);
    }

}
