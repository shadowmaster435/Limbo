package org.shadowmaster435.limbo.world.subworld;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.*;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.world.util.BlockData;

import java.util.HashMap;

public class BlockArea {

    private final BlockPos size;
    private HashMap<BlockPos, BlockData> unloaded_blocks = new HashMap<>();
    private BlockPos min_pos;
    private BlockPos max_pos;


    public BlockArea(BlockPos min_pos, BlockPos max_pos, World world, boolean autoswap) {
        var p = min_pos.add(max_pos);
        size = new BlockPos(Math.absExact(p.getX()), Math.absExact(p.getY()), Math.absExact(p.getZ()));
        this.min_pos = min_pos;
        this.max_pos = max_pos;
        HashMap<BlockPos, BlockData> unloaded_blocks = new HashMap<>();
        var iter = new CuboidBlockIterator(min_pos.getX(), min_pos.getY(), min_pos.getZ(), max_pos.getX(), max_pos.getY(), max_pos.getZ());
        if (world != null) {

            while (iter.step()) {
                var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
                unloaded_blocks.put(pos, BlockData.create_from(world, pos));
                if (autoswap) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);
                }
            }
            this.unloaded_blocks = unloaded_blocks;

        }
    }

    public BlockArea(BlockPos min_pos, BlockPos max_pos, BlockPos size, HashMap<BlockPos, BlockData> unloaded_blocks) {
        this.size = size;
        this.unloaded_blocks = unloaded_blocks;
        var corners = MiscUtil.get_corners(size);

        this.min_pos = min_pos;
        this.max_pos = max_pos;

    }

    public void swap(World world, BlockPos pos, BlockPos bepos) {
        HashMap<BlockPos, BlockData> temp = new HashMap<>();
        var iterator = new CuboidBlockIterator(min_pos.getX(), min_pos.getY(), min_pos.getZ(), max_pos.getX(), max_pos.getY(), max_pos.getZ());

        while (iterator.step()) {
            var offset = new BlockPos(iterator.getX(), iterator.getY(), iterator.getZ()).subtract(pos);
            if (offset.add(pos).equals(bepos)) {
                continue;
            }
            if (!unloaded_blocks.containsKey(offset)) {
                if (!world.getBlockState(offset.add(pos)).getBlock().equals(Blocks.AIR)) {
                    temp.put(offset, BlockData.create_from(world, offset.add(pos)));
                }
                if (!world.getBlockState(offset.add(pos)).getBlock().equals(unloaded_blocks.get(offset))) {
                    world.setBlockState(offset.add(pos), Blocks.AIR.getDefaultState());

                }

                continue;
            }
            var data = unloaded_blocks.get(offset);
            if (!world.getBlockState(offset.add(pos)).getBlock().equals(Blocks.AIR)) {
                temp.put(offset, BlockData.create_from(world, offset.add(pos)));
            }
            data.place(world, offset.add(pos));
        }

        unloaded_blocks = temp;
    }

    public NbtCompound to_nbt() {
        NbtCompound compound = new NbtCompound();
        NbtList entries = new NbtList();
        for (BlockPos pos : unloaded_blocks.keySet()) {
            NbtCompound entry_compound = new NbtCompound();

            var entry = unloaded_blocks.get(pos);
            var list = new NbtList();
            entry_compound.put("pos", NbtHelper.fromBlockPos(pos));
            entry_compound.put("blockstate", entry.to_nbt());

            list.add(0, entry_compound);
            entries.add(0, list);
        }
        compound.put("entries", entries);
        compound.put("area_size", NbtHelper.fromBlockPos(size));

        return compound;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static BlockArea from_nbt(BlockPos min_pos, BlockPos max_pos, NbtCompound compound) {
        var size = NbtHelper.toBlockPos(compound, "area_size").get();
        HashMap<BlockPos, BlockData> data = new HashMap<>();
        var entries = (NbtList) compound.get("entries");
        assert entries != null;
        for (NbtElement entry : entries) {
            var element = (NbtCompound) ((NbtList) entry).get(0);

            var pos_element =  element.getIntArray("pos");

            var pos = new BlockPos(pos_element[0], pos_element[1], pos_element[2]);

            data.put(pos, BlockData.from_nbt(element));
        }
        return new BlockArea(min_pos, max_pos, size, data);
    }

}
