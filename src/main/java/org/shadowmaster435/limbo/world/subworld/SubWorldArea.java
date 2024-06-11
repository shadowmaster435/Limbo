package org.shadowmaster435.limbo.world.subworld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SubWorldArea {

    public WorldArea area;
    public Vec3d min_pos;
    public Vec3d max_pos;
    public World world;

    public SubWorldArea source_world;
    public SubWorldArea previous_subworld;

    public SubWorldArea(BlockPos min_pos, BlockPos max_pos, World world) {
        this.area = new WorldArea(min_pos, max_pos, world, true);
        this.min_pos = min_pos.toCenterPos().subtract(0.5,0.5,0.5);
        this.max_pos = max_pos.toCenterPos().subtract(0.5,0.5,0.5);
        this.world = world;
    }


}
