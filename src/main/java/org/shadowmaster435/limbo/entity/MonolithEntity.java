package org.shadowmaster435.limbo.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.init.ModSounds;
import org.shadowmaster435.limbo.util.Easing;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.Timer;

public class MonolithEntity extends PathAwareEntity {


    public int anger = 0;
    public Timer anger_timer;

    public MonolithEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        anger_timer = new Timer();
        anger_timer.repeating = false;
    }


    @Override
    protected void playHurtSound(DamageSource damageSource) {
        playSound(ModSounds.MONOLITH_HURT_EVENT, 1, MathHelper.nextFloat(Random.createLocal(), 0.2f ,0.5f));
    }

    @Override
    public void tick() {
        super.tick();
        anger_timer.update();
        if (anger_timer.ticks_left == 0) {
            anger_timer.start(20);
            if (can_see_player()) {
                anger = Math.min(11, anger + 1);
            } else {
                anger = Math.max(0, anger - 1);
            }

        }
        if (can_see_player()) {
            if (anger == 11) {
                MiscUtil.eye_anger += 0.0005f;
            }
//            if (anger > 5) {
//                var player = getWorld().getClosestPlayer(this, 64);
//                var player_pos = player.getPos();
//                var delta = ((anger - 5) / 6f) + (anger_timer.delta() / 6f);
//                if (anger == 11) {
//                    delta = 1;
//                }
//                getWorld().playSound(player, player_pos.x, player_pos.y, player_pos.z, ModSounds.MONOLITH_STARE_EVENT, SoundCategory.PLAYERS, Easing.QUAD_IN.ease(delta, 0f, 0.5f, 1f), Easing.QUAD_IN.ease(delta, 0.1f, 1f, 1f));
//            }
            if (anger > 0) {
                var player = getWorld().getClosestPlayer(this, 32);
                var player_pos = player.getPos().add(0,player.getStandingEyeHeight(), 0);
                var delta = ((anger) / 11f) + (anger_timer.delta() / 11f);
                if (anger == 11) {
                    delta = 1;
                }
                getWorld().playSound(player, player_pos.x, player_pos.y, player_pos.z, ModSounds.MONOLITH_STARE_EVENT, SoundCategory.PLAYERS, Easing.LINEAR.ease(delta, 0f, 0.5f, 1f), Easing.SINE_IN.ease(delta, 0.1f, 1f, 1f));

            }
            this.lookAtEntity(MinecraftClient.getInstance().player, 360, 180);
            setHeadYaw(getYaw());
        }
    }


    public boolean can_see_player() {
        var player = getWorld().getClosestPlayer(this, 32);
        if (player != null) {
            var pos = player.getPos().add(0,player.getStandingEyeHeight(), 0);

            var ray = MiscUtil.raycast(getPos(), pos, getWorld(), this).getType();
            return MiscUtil.raycast(getPos(), pos, getWorld(), this).getType() == HitResult.Type.MISS;
        }
        return false;
    }




    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }
}