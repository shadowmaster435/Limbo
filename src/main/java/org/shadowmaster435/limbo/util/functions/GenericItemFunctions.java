package org.shadowmaster435.limbo.util.functions;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface GenericItemFunctions {

    default void set_stack_in_hand(PlayerEntity entity, ItemStack stack) {
        entity.setStackInHand(entity.getActiveHand(), stack);
    }

    default void spawn(ItemStack stack, World world, Vec3d pos) {
        world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, stack));
    }

}
