package org.shadowmaster435.limbo.world.subworld;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class SubWorldLoadZone extends Box {

    private final World world;
    public boolean player_present = false;
    public boolean just_loaded = false;
    public SubWorldArea area;
    public BlockPos load_pos;

    public SubWorldLoadZone(BlockPos min_pos, BlockPos max_pos, World world, SubWorldArea area, BlockPos load_pos) {
        super(min_pos.getX(), min_pos.getY(), min_pos.getZ(), max_pos.getX(), max_pos.getY(), max_pos.getZ());
        this.world = world;
        this.area = area;
        this.load_pos = load_pos;
    }

    public void update() { // call every tick
        var player = get_player();
        player_present = player != null;
        if (!player_present) {
            just_loaded = false;
        }
        if (!just_loaded && should_load() && player_present) {
            area.area.swap(world, load_pos);
            assert player != null;
            player.setPosition(load_pos.toCenterPos().subtract(0.5,0,0.5));
        }
    }

    public boolean should_load() {
        return player_present && get_player_distance() <= 0.5f;
    }

    public float get_player_distance() {
        var p = get_player();
        var lpos =(p != null) ? p.getLerpedPos(MinecraftClient.getInstance().getTickDelta()) : new Vec3d(0,0,0);
        return (p != null) ? (float) lpos.add(0, p.getStandingEyeHeight(), 0).distanceTo(getCenter()) : 1;
    }

    public PlayerEntity get_player() {
        var pos = getCenter();
        var predicate = TargetPredicate.createAttackable().setPredicate((t) -> {
            boolean is_player = t.isPlayer();
            boolean in_box = contains(t.getPos().add(t.getBoundingBox().getCenter()));
            return in_box && is_player;
        });
        return world.getClosestPlayer(predicate, pos.x, pos.y, pos.z);
    }

}
