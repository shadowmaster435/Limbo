package org.shadowmaster435.limbo.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;


public class ModMathHelper extends MathHelper {

    public static double get_angle_between(Vector3f a, Vector3f b) {
        var dot = a.dot(b);
        var mag_a = magnitude(a.x, a.y, a.z);
        var mag_b = magnitude(b.x, b.y, b.z);
        return Math.acos(dot / (mag_a * mag_b));
    }

    public static Vec3d get_rotation_vector(double yaw, double pitch) {
        var real_yaw = Math.toRadians(yaw);
        var real_pitch = Math.toRadians(pitch);
        double X = Math.sin(real_pitch) * Math.cos(real_yaw);
        double Y = Math.sin(real_pitch) * Math.sin(real_yaw);
        double Z = -Math.cos(real_pitch);

        return new Vec3d(X, Z, Y);
    }

    public double yaw_towards(Vec3d origin, Vec3d location) {
        var sub = origin.subtract(location);
        double dX = sub.x;
        double dZ = sub.z;
        return Math.atan2(dZ, dX);
    }

    public double pitch_towards(Vec3d origin, Vec3d location) {
        var sub = origin.subtract(location);
        double dX = sub.x;
        double dY = sub.y;
        double dZ = sub.z;
        return Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
    }



}
