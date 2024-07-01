package org.shadowmaster435.limbo.world.subworld;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.Timer;
import org.shadowmaster435.limbo.util.TriggerBox;


public class SubWorldLoadZone extends TriggerBox {

    public World world;
    public SubWorldArea area;
    public BlockPos load_pos;
    public BlockPos be_pos;


    public SubWorldLoadZone(BlockPos min_pos, BlockPos max_pos, World world, SubWorldArea area, BlockPos be_pos, BlockPos load_pos) {
        super(min_pos, max_pos);
        this.world = world;
        this.area = area;
        this.load_pos = load_pos;
        this.be_pos = be_pos;
        bind_enter(this, "swap");

    }

    public SubWorldLoadZone(BlockPos pos, World world, SubWorldArea area) {
        super(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        this.world = world;
        this.area = area;
        var ofs = pos.toCenterPos().subtract(0,0.5,0);
        this.load_pos = pos;
    }



    public void update() { // call every tick
        super.update(get_player());

    }

    public void swap() {
        area.area.swap(world, load_pos, be_pos);
        var be =  world.getBlockEntity(be_pos);
        if (be != null) {
            be.markDirty();

        }
    }

    public boolean should_load() {
        return MiscUtil.get_player_in_box(world, this) != null;
    }

    public float get_player_distance() {
        var p = get_player();
        return (p != null) ? (float) MiscUtil.get_lerped_player_eye_pos(p).distanceTo(getCenter()) : 1;
    }

    public PlayerEntity get_player() {
        var pos = getCenter();
        var predicate = TargetPredicate.createAttackable().setPredicate(this::get_player_predicate);
        return MinecraftClient.getInstance().player; //world.getClosestPlayer(pos.x, pos.y, pos.z, 3, false);
    }

    public boolean get_player_predicate(LivingEntity t) {
        boolean is_player = t.isPlayer();
        boolean in_box = contains(t.getPos());
        return in_box && is_player;
    }

}
