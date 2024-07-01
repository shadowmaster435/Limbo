package org.shadowmaster435.limbo.world.feature.helper;

import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FeatureShape {

    public enum Shape {
        sphere("sphere"),
        x_cylinder("x_cylinder"),
        y_cylinder("y_cylinder"),
        z_cylinder("z_cylinder");

        final String method_name;

        Shape(String method_name) {
            this.method_name = method_name;
        }
    }

    public final FeatureShapeData data;

    public FeatureShape(FeatureShapeData data) {
        this.data = data;
    }

    public void sphere(BlockPos center, Random random, World world, int radius) {
        var min = center.add(-radius, -radius, -radius);
        var max = center.add(radius, radius, radius);

        var iter = new CuboidBlockIterator(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (!data.test(pos, world)) {
                continue;
            }
            if (center.getSquaredDistance(pos) <= radius) {
                world.setBlockState(data.distort(pos,world), data.select(random));
            }
        }
    }

    public void y_cylinder(BlockPos center, Random random, World world, int radius) {
        var min = center.add(-radius, 0, -radius);
        var max = center.add(radius, 0, radius);

        var iter = new CuboidBlockIterator(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (!data.test(pos, world)) {
                continue;
            }
            if (center.getSquaredDistance(pos) <= radius) {
                world.setBlockState(data.distort(pos,world), data.select(random));
            }
        }
    }

    public void x_cylinder(BlockPos center, Random random, World world, int radius) {
        var min = center.add(0, -radius, -radius);
        var max = center.add(0, radius, radius);

        var iter = new CuboidBlockIterator(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (!data.test(pos, world)) {
                continue;
            }
            if (center.getSquaredDistance(pos) <= radius) {
                world.setBlockState(data.distort(pos,world), data.select(random));
            }
        }
    }

    public void z_cylinder(BlockPos center, Random random, World world, int radius) {
        var min = center.add(-radius, -radius, 0);
        var max = center.add(radius, radius, 0);

        var iter = new CuboidBlockIterator(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

        while (iter.step()) {
            var pos = new BlockPos(iter.getX(), iter.getY(), iter.getZ());
            if (!data.test(pos, world)) {
                continue;
            }
            if (center.getSquaredDistance(pos) <= radius) {
                world.setBlockState(data.distort(pos,world), data.select(random));
            }
        }
    }


}
