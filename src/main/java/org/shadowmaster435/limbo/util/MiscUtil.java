package org.shadowmaster435.limbo.util;

import net.minecraft.block.Block;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.Heightmap;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.shadowmaster435.limbo.shader.CollapseOrbDepthFX;
import org.shadowmaster435.limbo.util.callable.Callable;
import org.shadowmaster435.limbo.util.callable.CallableTimerTask;
import org.shadowmaster435.limbo.util.misc.ByteBufferBackedInputStream;
import org.shadowmaster435.limbo.util.render.texture.ByteBufferTexture;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

public class MiscUtil {

    public static ByteBufferTexture heightmap_texture = new ByteBufferTexture(128, 128);
    public static ByteBuffer heightmap_buffer = BufferUtils.createByteBuffer((128 * 128) * 4);

    public static final Animation animation = Animation.Builder.create(1.0F).looping()
            .addBoneAnimation("bone", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("bone2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();
    public static boolean start_exit_world = false;
    public static boolean in_limbo = false;
    public static float eye_anger = 0;
    public static Timer eye_calm_timer;

    public static void init_globals() {
        eye_calm_timer = new Timer();
        eye_calm_timer.repeating = false;

    }




    public static BlockHitResult dir_raycast(Direction dir, BlockPos origin, World world, int mul) {
        var vec = dir.getVector();
        var start = MiscUtil.to_vec3d(vec.multiply(1).add(origin));
        if (dir.getAxis() != Direction.Axis.Z) {
            start = start.offset(dir, 1);
        } else {
            start = start.offset(dir, 0);
        }
        var end = MiscUtil.to_vec3d(vec.multiply(mul).add(origin));
        return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, ShapeContext.absent()));
    }

    public static RaycastContext create_raycast_context(Vec3d start, Vec3d end) {
        return new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, ShapeContext.absent());
    }

    public static PlayerEntity get_player_in_box(World world, Box box) {
        var list = world.getOtherEntities(null, box);
        list.removeIf(e -> !(e instanceof PlayerEntity));
        if (!list.isEmpty()){
            return (PlayerEntity) list.get(0);
        } else {
            return null;
        }
    }

    //region position stuff

    public static Vec3d to_vec3d(Vec3i pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vec3d to_vec3d(BlockPos pos) {
        return pos.toCenterPos().subtract(0.5,0.5,0.5);
    }

    public static BlockPos div_blockpos(BlockPos pos, int i) {
        return new BlockPos(Math.round(pos.getX() / ((float) i)), Math.round(pos.getY() / ((float) i)), Math.round(pos.getZ() / ((float) i)));
    }
    public static BlockPos div_blockpos(BlockPos pos, double i) {
        return new BlockPos((int) Math.round(pos.getX() / i), (int) Math.round(pos.getY() / i), (int) Math.round(pos.getZ() / i));
    }
    public static BlockPos div_blockpos(BlockPos pos, float i) {
        return new BlockPos(Math.round(pos.getX() / i), Math.round(pos.getY() / i), Math.round(pos.getZ() / i));
    }

    public static List<BlockPos> get_corners(BlockPos size) {
        List<BlockPos> l = new ArrayList<>();
        var min = to_block_pos(new Vec3d(size.getX() / -2f, size.getY() / -2f,size.getZ() / -2f));
        var max = to_block_pos(new Vec3d(size.getX() / 2f, size.getY() / 2f,size.getZ() / 2f));
        l.add(min);
        l.add(max);
        return l;
    }

    public static boolean box_contains_with_offset(Box box, Vec3d pos, Vec3d ofs) {
        var offset = box.offset(ofs);
        return offset.contains(pos);
    }

    public static Box create_from_points(Vec3d min, Vec3d max) {
        return new Box(new Vec3d(0,0,0), abs_vec3d(min.subtract(max)));
    }

    public static Vec3d abs_vec3d(Vec3d val) {
        return new Vec3d(Math.abs(val.x),Math.abs(val.y),Math.abs(val.z));
    }

    public static BlockPos to_block_pos(Vec3d pos) {
        return new BlockPos((int) Math.round(pos.x), (int) Math.round(pos.y), (int) Math.round(pos.z));
    }

    public static BlockPos to_block_pos(Vector3f pos) {
        return new BlockPos(Math.round(pos.x), Math.round(pos.y), Math.round(pos.z));

    }

    public static Vec3d get_lerped_player_pos(PlayerEntity player, float tick_delta) {
        return player.getLerpedPos(tick_delta);
    }

    public static Vec3d get_lerped_player_pos(PlayerEntity player) {
        return player.getLerpedPos(MinecraftClient.getInstance().getTickDelta());
    }

    public static Vec3d get_lerped_player_pos() {
        return MinecraftClient.getInstance().player.getLerpedPos(MinecraftClient.getInstance().getTickDelta());
    }

    public static Vec3d get_lerped_player_eye_pos(PlayerEntity player, float tick_delta) {
        return get_lerped_player_pos(player, tick_delta).add(0.0, player.getStandingEyeHeight(), 0.0);
    }

    public static Vec3d get_lerped_player_eye_pos(PlayerEntity player) {
        return get_lerped_player_pos(player).add(0.0, player.getStandingEyeHeight(), 0.0);
    }

    public static Vec3d get_lerped_player_eye_pos() {
        return get_lerped_player_pos().add(0.0, MinecraftClient.getInstance().player.getStandingEyeHeight(), 0.0);
    }

    public static Vec3d get_lerped_centered_player_eye_pos(PlayerEntity player, float tick_delta) {
        return get_lerped_player_eye_pos(player,tick_delta).add(0.5,0,0.5);
    }

    public static Vec3d get_lerped_centered_player_eye_pos(PlayerEntity player) {
        return get_lerped_player_eye_pos(player,MinecraftClient.getInstance().getTickDelta()).add(0.5,0,0.5);
    }

    public static Vec3d get_lerped_centered_player_eye_pos() {
        return get_lerped_player_eye_pos().add(0.5, 0, 0.5);
    }


    public static Box box_from_size(Vec3d size) {
        return new Box(new Vec3d(0,0,0), size);
    }

    public static Box create_centered_box(int radius, BlockPos origin) {
        var min = radius / -2d;
        var max = radius / 2d;
        var bpos = new Vec3d(origin.getX(), origin.getY(), origin.getZ());
        var min_pos = new Vec3d(min,min,min).add(bpos);
        var max_pos = new Vec3d(max,max,max).add(bpos);

        return new Box(min_pos, max_pos);
    }

    public static Box create_centered_box(int radius) {
        var min = radius / -2d;
        var max = radius / 2d;
        var min_pos = new Vec3d(min,min,min);
        var max_pos = new Vec3d(max,max,max);

        return new Box(min_pos, max_pos);
    }

    //endregion

    public static Timer windowhidetest = new Timer();
    public static boolean window_hidden = false;
    public static boolean window_was_fullscreen = false;
    public static boolean fullscreen_window_was_hidden = false;

    public static void pre_world_render(float tickDelta, long limitTime, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f) {
        if (!MinecraftClient.getInstance().isPaused()) {
            heightmap_texture.write(heightmap_buffer);
        }
        if (window_hidden) {
            if (window_was_fullscreen) {
                GLFW.glfwHideWindow(MinecraftClient.getInstance().getWindow().getHandle());

            }
            MinecraftClient.getInstance().setScreen(null);
        } else {
            fullscreen_window_was_hidden = false;
        }
    }

    public static void tick_globals() {
        tick_eye();
        if (MinecraftClient.getInstance().world != null) {
            cache_heightmap();

        }
    }

    public static void show_window() {
        var window = MinecraftClient.getInstance().getWindow().getHandle();
        GLFW.glfwShowWindow(window);
        GLFW.glfwFocusWindow(window);
        MinecraftClient.getInstance().mouse.lockCursor();

        if (window_was_fullscreen) {
            MinecraftClient.getInstance().getWindow().toggleFullscreen();
            window_was_fullscreen = false;
        }
        window_hidden = false;
        MinecraftClient.getInstance().options.pauseOnLostFocus = last_focus_pause_value;



    }
    public static java.util.Timer timer = new java.util.Timer();
    public static java.util.Timer fullscreen_hide_timer = new java.util.Timer();
    public static boolean last_focus_pause_value = false;
    public static void hide_window() {
        var window = MinecraftClient.getInstance().getWindow().getHandle();
        if (MinecraftClient.getInstance().getWindow().isFullscreen()) {
            MinecraftClient.getInstance().getWindow().toggleFullscreen();
            window_was_fullscreen = true;
        }
        last_focus_pause_value = MinecraftClient.getInstance().options.pauseOnLostFocus;
        MinecraftClient.getInstance().options.pauseOnLostFocus = false;
        timer.schedule(new CallableTimerTask(new Callable(MiscUtil.class, "show_window"), true), 2000);

        GLFW.glfwHideWindow(window);
        window_hidden = true;

    }

    public static void hide_fullscreen_window() {
        var window = MinecraftClient.getInstance().getWindow().getHandle();

        GLFW.glfwHideWindow(window);
    }



    public static void cache_heightmap() {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        var world = client.world;

        int radius = 128;
        heightmap_buffer.clear();
        for (int x = -radius / 2; x < radius / 2; ++x) {
            for (int z = -radius/ 2; z < radius / 2; ++z) {
                var pos = player.getBlockPos().add(x, 0,z);
                var height = world.getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX(), pos.getZ());
                pos_to_rgba(heightmap_buffer, new BlockPos(x, height, z), radius);
            }
        }
        heightmap_buffer.compact();
        byte[] arr = {};
        heightmap_buffer.get(arr);
        heightmap_texture.write(heightmap_buffer);
        var str = new StringBuilder();
        for (int i = 0; i < heightmap_buffer.capacity(); ++i) {
            str.append(heightmap_buffer.get(i)).append(",");
        }
        CollapseOrbDepthFX.INSTANCE.testShader.setSamplerUniform("HMTest", heightmap_texture.id);
    }

    public static void pos_to_rgba(ByteBuffer buffer, BlockPos pos, int range) {
        float actual_x = pos.getX() + (range / 2f);
        float actual_y = pos.getY() + 64;

        float actual_z = pos.getZ() + (range / 2f);

        byte x_wrap_count = (byte) ((int) Math.floor(actual_x) / 255f);
        byte y_wrap_count = (byte) ((int) Math.floor(actual_y) / 255f);

        byte z_wrap_count = (byte) ((int) Math.floor(actual_z) / 255f);
        byte r = (byte) ((actual_x) % 128);
        byte g = (byte) ((actual_y ) % 128);
        byte b = (byte) ((actual_z) % 128);
        StringBuilder a_string = new StringBuilder();
        StringBuilder x_wrap_string = new StringBuilder(Integer.toBinaryString(x_wrap_count % 8));
        StringBuilder y_wrap_string = new StringBuilder(Integer.toBinaryString(y_wrap_count % 4));
        StringBuilder z_wrap_string = new StringBuilder(Integer.toBinaryString(x_wrap_count % 8));
        while (x_wrap_string.length() < 3) {
            x_wrap_string.insert(0, "0");

        }
        while (y_wrap_string.length() < 2) {
            y_wrap_string.insert(0, "0");
        }
        while (z_wrap_string.length() < 3) {

            z_wrap_string.insert(0, "0");
        }
        StringBuilder str = new StringBuilder();
        str.append(z_wrap_string).append(y_wrap_string).append(x_wrap_string);
        byte[] arr = {r, g, b, (byte) Integer.parseInt(str.toString(), 2)};

        buffer.put(arr);
    }

    public static void tick_eye() {
        if (eye_calm_timer == null || MinecraftClient.getInstance().player == null) {
            return;
        }
        if (eye_calm_timer.ticks_left == 0) {
            eye_anger -= 0.0001f;
        }
        eye_anger = MathHelper.clamp(eye_anger, 0, 1);
        eye_calm_timer.update();
    }


    public static void exit_world() {
        var client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.world.disconnect();
        }

        client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
        client.setScreen(new TitleScreen());
        start_exit_world = false;
    }

    public static HitResult raycast(Vec3d start, Vec3d end, World world, Entity entity) {
        return world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, entity));
    }
}
