package org.shadowmaster435.limbo.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class MiscUtil {
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

    public static void tick_globals() {
        tick_eye();
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
