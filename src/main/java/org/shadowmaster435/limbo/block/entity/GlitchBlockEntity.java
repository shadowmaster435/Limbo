package org.shadowmaster435.limbo.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.init.ModSounds;
import org.shadowmaster435.limbo.util.Easing;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.Timer;

public class GlitchBlockEntity extends BlockEntity {

    public Timer open_timer;
    public Timer static_start_timer;
    public Timer static_delay_timer;
    public Timer eye_banish_timer;

    public Timer static_fade_timer;

    public Timer collapse_timer;
    public boolean sound_played = false;
    public boolean opened = false;
    public boolean static_building = false;
    public boolean static_delayed = false;

    public boolean static_full = false;
    public boolean finish = false;
    public float static_influence = 0.0f;
    public float orb_scale = 1.0f;

    public Timer eye_timer;
    public Timer eye_delay_timer;

    public float eye_delta = 0;
    public boolean eye_transition = false;
    public boolean eye_delayed = true;
    public boolean delaye_s = false;

    public boolean eye_can_see_you = false;
    public boolean eye_saw_you = false;
    public boolean eye_banishing_you = false;
    
    public boolean eye_banished_you = false;

    public GlitchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GLITCH_BLOCK_ENTITY, pos, state);
       
    }
    public static void tick(World world, BlockPos pos, BlockState state, GlitchBlockEntity be) {
        //world.breakBlock(pos, false);

        //animate_shader(be, pos, world);
       // animate_eye(be, world);
    }
    public static void animate_shader(GlitchBlockEntity be, BlockPos pos, World world) { // nightmare code ball

        if (!be.sound_played) {
            be.open_timer = new Timer();
            be.static_start_timer = new Timer();
            be.static_fade_timer = new Timer();
            be.static_delay_timer = new Timer();
            be.eye_banish_timer = new Timer();

            be.collapse_timer = new Timer();
            be.open_timer.start(40);
            world.playSoundAtBlockCenter(pos, ModSounds.PORTAL_OPEN_EVENT, SoundCategory.BLOCKS, 1f, 1f, true);
            be.sound_played = true;
        }
        be.collapse_timer.update();
        be.static_fade_timer.update();
        be.static_start_timer.update();
        be.open_timer.update();
        be.static_delay_timer.update();
        be.eye_banish_timer.update();
    
        if (be.eye_saw_you && !be.eye_banishing_you && be.eye_delta <= 1) {
            be.eye_banish_timer.start(20);

            be.eye_banishing_you = true;
        }

        if (be.eye_banishing_you && be.eye_banish_timer.ticks_left ==0) {
            be.eye_banished_you = true;
            MiscUtil.in_limbo = true;
            MiscUtil.start_exit_world = true;
        }



        if (!be.open_timer.is_finished()) {

            be.orb_scale = Easing.EXPO_IN.ease(be.open_timer.ticks_left + MinecraftClient.getInstance().getTickDelta(), 0f, 1f, 40);
        } else {

            if (!be.opened) {
                be.static_delay_timer.start(100);
                be.eye_can_see_you = true;

                be.opened = true;
            }
        }
        if (be.eye_saw_you) {

            return;
        }
        if (!be.static_delay_timer.is_finished()) {

        } else {


            if (!be.static_delayed && be.opened) {


                be.static_start_timer.start(35);
                be.eye_can_see_you = false;
                be.static_delayed = true;
            }
        }
        if (be.static_start_timer.ticks_left == 5) {
            world.playSoundAtBlockCenter(pos, ModSounds.PORTAL_CLOSE_EVENT, SoundCategory.BLOCKS, 1f, 1f, true);

        }
        if (!be.static_start_timer.is_finished()) {

        } else {
            if (!be.static_building && be.static_delayed) {

                be.static_fade_timer.start(25);
                be.static_building = true;

            }
        }

        if (!be.static_fade_timer.is_finished() && be.static_start_timer.is_finished()) {
            be.static_influence = be.static_fade_timer.delta();

        } else {
            if (!be.static_full && be.static_building) {
                be.collapse_timer.start(20);
                be.static_full = true;

            }
        }
        if (!be.collapse_timer.is_finished() && be.static_full) {
            be.orb_scale = 1.0f - Easing.EXPO_OUT.ease(be.collapse_timer.ticks_left + MinecraftClient.getInstance().getTickDelta(), 0f, 1f, 30);
        } else {
            if (!be.finish && be.static_full) {
                world.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                be.finish = true;
            }
        }
    }

    public static void animate_eye(GlitchBlockEntity be, World world) {


        var cam = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        var dist = cam.distanceTo(MinecraftClient.getInstance().player.getLerpedPos(MinecraftClient.getInstance().getTickDelta()).add(0, MinecraftClient.getInstance().player.getStandingEyeHeight(), 0));
        var farawaycam = dist > 1;
        if (!farawaycam) {
            be.eye_delta = 60;
        }
        if (!be.eye_delayed) {

            if (farawaycam && !be.eye_transition) {
                be.eye_timer = new Timer();
                be.eye_timer.start(80);
                if (be.eye_can_see_you) {
                    be.eye_transition = true;
                    world.playSoundAtBlockCenter(be.getPos(), ModSounds.EYE_SAW_YOU_EVENT, SoundCategory.BLOCKS, 1f, 1f, true);

                    be.eye_saw_you = true;
                }
            }
            if (be.eye_transition) {
                be.eye_timer.update();
                be.eye_delta = be.eye_timer.ticks_left + MinecraftClient.getInstance().getTickDelta();
            }
        } else {
            if (!be.delaye_s) {
                be.delaye_s = true;
                be.eye_delay_timer = new Timer();
                be.eye_delay_timer.start(40);
            } else {
                be.eye_delay_timer.update();
                if (be.eye_delay_timer.ticks_left <= 0) {
                    be.eye_delayed = false;
                }
            }
        }
    }

}
