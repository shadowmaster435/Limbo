package org.shadowmaster435.limbo.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.init.ModSounds;
import org.shadowmaster435.limbo.util.Easing;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.ModMathHelper;
import org.shadowmaster435.limbo.util.Timer;

public class MonolithEntity extends PathAwareEntity {


    public int anger = 0;
    public Timer anger_timer;
    public boolean death_fall = false;
    public boolean hit_ground_from_death_fall = false;
    public boolean played_ground_collision_sound = false;
    public Vec3d random_death_fall_rotation_vector = new Vec3d(0,0,0);
    public Vec3d look_angle_on_death = new Vec3d(0,0,0);


    public MonolithEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        anger_timer = new Timer();
        anger_timer.repeating = false;
        setVelocity(0, 0,0);
        setNoGravity(true);
    }
    @Override
    public void onDeath(DamageSource damageSource) {
        random_death_fall_rotation_vector = new Vec3d(ModMathHelper.next_vector3f(-1, 1));
        look_angle_on_death = ModMathHelper.get_rotation_vector(getLerpTargetYaw(), getLerpTargetPitch());
        death_fall = true;
        setNoGravity(false);
    }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        playSound(ModSounds.MONOLITH_HURT_EVENT, 1, MathHelper.nextFloat(Random.createLocal(), 0.2f ,0.5f));
    }

    @Override
    public void tick() {

        super.tick();

        if (death_fall || getHealth() == 0) {
            anger = 0;
        } else {
            setNoGravity(true);
            update_anger();
        }
    }
    @Override
    public boolean canTakeDamage() {
        if (dead) {
            return true;
        } else {
            return !this.isInvulnerable() && this.isPartOfGame();
        }
    }

    public void update_death() {
        var y_size_ofs = Math.abs(getPitch()) / 90f;
        setBoundingBox(new Box(-0.5, -2 + (y_size_ofs * 2f),-0.5,0.5, 0,0.5).offset(getPos()));
        if (this.death_fall) {

            if (isOnGround()) {
                hit_ground_from_death_fall = true;
            }
            if (hit_ground_from_death_fall) {
                if (!played_ground_collision_sound) {
                    played_ground_collision_sound = true;
                }
            } else {
                var clamped_yaw = MathHelper.clamp((random_death_fall_rotation_vector.x * (getVelocity().y * 8)), -8f, 8f);
                var clamped_pitch = MathHelper.clamp((random_death_fall_rotation_vector.y * (getVelocity().y * 2)), -2f, 2f);

                setPitch((float) ((getPitch(MinecraftClient.getInstance().getTickDelta())) + clamped_pitch));
                setYaw((float) ((getYaw(MinecraftClient.getInstance().getTickDelta())) + clamped_yaw));
                setHeadYaw(getYaw());

            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MONOLITH_DEACTIVATE_EVENT;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (isDead() && isOnGround() && !played_ground_collision_sound) {
            getEntityWorld().playSound(MinecraftClient.getInstance().player, MinecraftClient.getInstance().player.getBlockPos(), ModSounds.MONOLITH_GROUND_HIT_EVENT, SoundCategory.HOSTILE, 0.5f, 1);
            played_ground_collision_sound = true;
        }
        super.fall(heightDifference, onGround, state, landedPosition);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (isDead() && isOnGround()) {
            try {
                if (source.getAttacker() == null) {
                    return false;
                }
                if (source.getAttacker().isPlayer()) {
                    this.emitGameEvent(GameEvent.ENTITY_DIE);
                    this.onKilledBy((LivingEntity) source.getAttacker());
                    this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
                    remove(RemovalReason.KILLED);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if (hit_ground_from_death_fall) {
            this.emitGameEvent(GameEvent.ENTITY_DIE);
            this.drop(damageSource);
            this.onKilledBy((LivingEntity) damageSource.getAttacker());
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            remove(RemovalReason.KILLED);
        } else {
            super.onDamaged(damageSource);
        }
    }

    public void update_anger() {
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

            if (anger > 0) {
                var player = getWorld().getClosestPlayer(this, 32);
                var player_pos = player.getPos().add(0,player.getStandingEyeHeight(), 0);
                var delta = ((anger) / 11f) + (anger_timer.delta() / 11f);
                if (anger == 11) {
                    delta = 1;
                }
                getWorld().playSound(player, player_pos.x, player_pos.y, player_pos.z, ModSounds.MONOLITH_STARE_EVENT, SoundCategory.PLAYERS, Easing.LINEAR.ease(delta, 0f, 0.5f, 1f), Easing.SINE_IN.ease(delta, 0.1f, 0.65f, 1f));

            }
            if (MinecraftClient.getInstance().player == null) {
                return;
            }
            this.lookAtEntity(MinecraftClient.getInstance().player, 360, 360);
            setHeadYaw(getYaw());
        }
    }


    @Override
    protected void updatePostDeath() {
        update_death();
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