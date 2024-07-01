package org.shadowmaster435.limbo.world.feature.helper;

import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface BlockPlacementConditions {


    enum Condition {
        leaves_wont_decay("not_floating"),
        not_floating("leaves_wont_decay");
        final String method_name;
        Condition(String method_name) {
            this.method_name = method_name;
        }

    }

    default boolean leaves_wont_decay(BlockPos pos, World world) {
        for (Direction dir : Direction.values()) {
            var neighbor_state = world.getBlockState(pos.offset(dir));
            var is_neighbor_supportive = neighbor_state.streamTags().noneMatch((blockTagKey -> blockTagKey == BlockTags.LOGS || blockTagKey == BlockTags.LEAVES));
            if (!is_neighbor_supportive) {
                continue;
            }
            for (int i = 0; i < 7; ++i) {
                var state = world.getBlockState(pos.offset(dir, i + 1));
                var is_log = state.streamTags().noneMatch((blockTagKey -> blockTagKey == BlockTags.LOGS));
                var is_supportive = state.streamTags().noneMatch((blockTagKey -> is_log || blockTagKey == BlockTags.LEAVES));
                if (is_supportive) {
                    if (is_log) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    default boolean not_floating(BlockPos pos, World world) {
        for (Direction dir : Direction.values()) {
            if (world.getBlockState(pos.offset(dir)).getBlock() != Blocks.AIR) {
                return true;
            }
        }
        return false;
    }



}
