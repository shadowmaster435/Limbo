package org.shadowmaster435.limbo.util;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.model.SinglePartBlockEntityModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModAnimationHelper {
    public ModAnimationHelper() {
    }

    public static void animate(SinglePartBlockEntityModel<?> model, Animation animation, long runningTime, float scale, Vector3f tempVec) {
        float f = getRunningSeconds(animation, runningTime);
        animation.boneAnimations().entrySet().iterator();
        for(Map.Entry<String, List<Transformation>> entry : animation.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getChild(entry.getKey());
            List<Transformation> list = entry.getValue();
            optional.ifPresent(part -> list.forEach(transformation -> {
                Keyframe[] keyframes = transformation.keyframes();
                int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> f <= keyframes[index].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = f - keyframe.timestamp();
                float k;
                if (j != i) {
                    k = MathHelper.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    k = 0.0F;
                }
                part.resetTransform();
                keyframe2.interpolation().apply(tempVec, k, keyframes, i, j, scale);
                transformation.target().apply(part, tempVec);

            }));
        }

    }

    private static float getRunningSeconds(Animation animation, long runningTime) {
        float f = (float)runningTime / 1000.0F;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }

}
