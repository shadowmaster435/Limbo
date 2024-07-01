package org.shadowmaster435.limbo.world.feature.helper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.SimplexNoise;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.callable.Callable;

public interface Distorter {




    default BlockPos random_offset(Random random, int max_range, BlockPos pos) {
        var ofs = new BlockPos(random.nextBetween(-max_range, max_range),random.nextBetween(-max_range, max_range),random.nextBetween(-max_range, max_range));
        return pos.add(ofs);
    }

    default BlockPos noise_offset(Random random, Vec3d noise_scale, Vec3d distance_scale, BlockPos pos) {
        var scaled_pos = pos.toCenterPos().multiply(noise_scale);
        var noise = new Vector3f(SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000)),SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000)),SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000))).mul(distance_scale.toVector3f());
        return pos.add((int) noise.x, (int) noise.y, (int) noise.z);
    }

    default BlockPos random_choice(Random random, BlockPos pos, BlockPos... choices) {
        return pos.add(choices[random.nextBetween(0, choices.length - 1)]);
    }

    default BlockPos random_offset(Random random, int max_range, int x, int y, int z) {
        return random_offset(random, max_range, new BlockPos(x,y,z));
    }

    default BlockPos sine_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
        var sine = MathHelper.lerp(MathHelper.sin(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
        var result = pos;
        switch (axis) {
            case X -> result = pos.add(sine, 0, 0);
            case Y -> result = pos.add(0, sine, 0);
            case Z -> result = pos.add(0, 0, sine);
        }
        return result;
    }

    default BlockPos cosine_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
        var cosine = MathHelper.lerp(MathHelper.cos(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
        var result = pos;
        switch (axis) {
            case X -> result = pos.add(cosine, 0, 0);
            case Y -> result = pos.add(0, cosine, 0);
            case Z -> result = pos.add(0, 0, cosine);
        }
        return result;
    }


    default BlockPos axis_trig_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
        var sine = MathHelper.lerp(MathHelper.sin(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
        var cosine = MathHelper.lerp(MathHelper.cos(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
        var result = pos;
        switch (axis) {
            case X -> result = pos.add(0, cosine, sine);
            case Y -> result = pos.add(cosine, 0, sine);
            case Z -> result = pos.add(cosine, sine, 0);
        }
        return result;
    }


    //region Direction Offsets


    default BlockPos[] get_direction_offsets(Random random, int min_range, int max_range) {
        return new BlockPos[]{
                new BlockPos(Direction.UP.getVector().multiply(random.nextBetween(min_range, max_range))),
                new BlockPos(Direction.DOWN.getVector().multiply(random.nextBetween(min_range, max_range))),
                new BlockPos(Direction.NORTH.getVector().multiply(random.nextBetween(min_range, max_range))),
                new BlockPos(Direction.SOUTH.getVector().multiply(random.nextBetween(min_range, max_range))),
                new BlockPos(Direction.EAST.getVector().multiply(random.nextBetween(min_range, max_range))),
                new BlockPos(Direction.WEST.getVector().multiply(random.nextBetween(min_range, max_range)))
        };
    }

    default BlockPos[] get_direction_offsets(Random random, int max_range) {
        return new BlockPos[]{
                new BlockPos(Direction.UP.getVector().multiply(random.nextBetween(1, max_range))),
                new BlockPos(Direction.DOWN.getVector().multiply(random.nextBetween(1, max_range))),
                new BlockPos(Direction.NORTH.getVector().multiply(random.nextBetween(1, max_range))),
                new BlockPos(Direction.SOUTH.getVector().multiply(random.nextBetween(1, max_range))),
                new BlockPos(Direction.EAST.getVector().multiply(random.nextBetween(1, max_range))),
                new BlockPos(Direction.WEST.getVector().multiply(random.nextBetween(1, max_range)))
        };
    }

    default BlockPos[] get_direction_offsets(int distance) {
        return new BlockPos[]{
                new BlockPos(Direction.UP.getVector().multiply(distance)),
                new BlockPos(Direction.DOWN.getVector().multiply(distance)),
                new BlockPos(Direction.NORTH.getVector().multiply(distance)),
                new BlockPos(Direction.SOUTH.getVector().multiply(distance)),
                new BlockPos(Direction.EAST.getVector().multiply(distance)),
                new BlockPos(Direction.WEST.getVector().multiply(distance))
        };
    }
    default BlockPos random_direction_offset(Random random, int min_range, int max_range, Direction[] directions, BlockPos pos) {
        return pos.offset(directions[random.nextBetween(0, directions.length - 1)], random.nextBetween(min_range, max_range));
    }

    default BlockPos random_direction_offset(Random random, int min_range, int max_range, Direction[] directions, int x, int y, int z) {
        return random_direction_offset(random, min_range, max_range, directions, new BlockPos(x,y,z));
    }

    default BlockPos random_direction_offset(Random random, int min_range, int max_range, Direction direction, BlockPos pos) {
        return pos.offset(direction, random.nextBetween(min_range, max_range));
    }

    default BlockPos random_direction_offset(Random random, int min_range, int max_range, Direction direction, int x, int y, int z) {
        return random_direction_offset(random, min_range, max_range, direction, new BlockPos(x,y,z));
    }

    default BlockPos random_direction_offset(Random random, int min_range, int max_range, BlockPos pos) {
        return pos.offset(Direction.random(random), random.nextBetween(min_range, max_range));
    }

    default BlockPos random_direction_offset(Random random, int min_range, int max_range, int x, int y, int z) {
        return random_direction_offset(random, min_range, max_range, new BlockPos(x,y,z));
    }
    default BlockPos random_direction_offset(Random random, int max_range, Direction[] directions, BlockPos pos) {
        return pos.offset(directions[random.nextBetween(0, directions.length - 1)], random.nextBetween(1, max_range));
    }

    default BlockPos random_direction_offset(Random random, int max_range, Direction[] directions, int x, int y, int z) {
        return random_direction_offset(random, max_range, directions, new BlockPos(x,y,z));
    }

    default BlockPos random_direction_offset(Random random, int max_range, Direction direction, BlockPos pos) {
        return pos.offset(direction, random.nextBetween(1, max_range));
    }

    default BlockPos random_direction_offset(Random random, int max_range, Direction direction, int x, int y, int z) {
        return random_direction_offset(random, max_range, direction, new BlockPos(x,y,z));
    }

    default BlockPos random_direction_offset(Random random, int max_range, BlockPos pos) {
        return pos.offset(Direction.random(random), random.nextBetween(1, max_range));
    }

    default BlockPos random_direction_offset(Random random, int max_range, int x, int y, int z) {
        return random_direction_offset(random, max_range, new BlockPos(x,y,z));
    }

    default DistorterSequence custom(Callable callable, Object... inputs) {
        return callable.bind(inputs).call();
    }

    //endregion

    class DistorterSequence {

        private BlockPos output = new BlockPos(0,0,0);

        public DistorterSequence() {
            output = new BlockPos(0,0,0);
        }

        public static DistorterSequence create() {
            return new DistorterSequence();
        }
        
        public BlockPos add(BlockPos pos) {
            return pos.add(output);
        }
        
        public BlockPos get() {
            return output;
        }
        
        
        public DistorterSequence(BlockPos pos) {
            this.output = pos;
        }

        public DistorterSequence random_offset(Random random, int max_range, BlockPos pos) {
            var ofs = new BlockPos(random.nextBetween(-max_range, max_range),random.nextBetween(-max_range, max_range),random.nextBetween(-max_range, max_range));
            return new DistorterSequence(pos.add(ofs));
        }

        public DistorterSequence noise_offset(Random random, Vec3d noise_scale, Vec3d distance_scale, BlockPos pos) {
            var scaled_pos = pos.toCenterPos().multiply(noise_scale);
            var noise = new Vector3f(SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000)),SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000)),SimplexNoise.noise((float) scaled_pos.x, (float) scaled_pos.y, (float) scaled_pos.z, random.nextBetween(-30000000, 30000000))).mul(distance_scale.toVector3f());
            return new DistorterSequence(pos.add((int) noise.x, (int) noise.y, (int) noise.z));
        }

        public DistorterSequence random_choice(Random random, BlockPos pos, BlockPos... choices) {
            return new DistorterSequence(pos.add(choices[random.nextBetween(0, choices.length - 1)]));
        }

        public DistorterSequence random_offset(Random random, int max_range, int x, int y, int z) {
            return random_offset(random, max_range, new BlockPos(x,y,z));
        }

        public DistorterSequence sine_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
            var sine = MathHelper.lerp(MathHelper.sin(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
            var result = pos;
            switch (axis) {
                case X -> result = pos.add(sine, 0, 0);
                case Y -> result = pos.add(0, sine, 0);
                case Z -> result = pos.add(0, 0, sine);
            }
            return new DistorterSequence(result);
        }

        public DistorterSequence cosine_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
            var cosine = MathHelper.lerp(MathHelper.cos(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
            var result = pos;
            switch (axis) {
                case X -> result = pos.add(cosine, 0, 0);
                case Y -> result = pos.add(0, cosine, 0);
                case Z -> result = pos.add(0, 0, cosine);
            }
            return new DistorterSequence(result);
        }


        public DistorterSequence axis_trig_offset(BlockPos pos, Direction.Axis axis, int min_amp, int max_amp, float frequency) {
            var sine = MathHelper.lerp(MathHelper.sin(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
            var cosine = MathHelper.lerp(MathHelper.cos(pos.getComponentAlongAxis(axis)) * frequency, min_amp, max_amp);
            var result = pos;
            switch (axis) {
                case X -> result = pos.add(0, cosine, sine);
                case Y -> result = pos.add(cosine, 0, sine);
                case Z -> result = pos.add(cosine, sine, 0);
            }
            return new DistorterSequence(result);
        }

        public DistorterSequence custom(Callable callable, Object... inputs) {
            return new DistorterSequence(callable.bind(inputs).call());
        }


        //region Direction Offsets
        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, Direction[] directions, BlockPos pos) {
            return new DistorterSequence(pos.offset(directions[random.nextBetween(0, directions.length - 1)], random.nextBetween(min_range, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, Direction[] directions, int x, int y, int z) {
            return random_direction_offset(random, min_range, max_range, directions, new BlockPos(x,y,z));
        }

        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, Direction direction, BlockPos pos) {
            return new DistorterSequence(pos.offset(direction, random.nextBetween(min_range, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, Direction direction, int x, int y, int z) {
            return random_direction_offset(random, min_range, max_range, direction, new BlockPos(x,y,z));
        }

        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, BlockPos pos) {
            return new DistorterSequence(pos.offset(Direction.random(random), random.nextBetween(min_range, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int min_range, int max_range, int x, int y, int z) {
            return random_direction_offset(random, min_range, max_range, new BlockPos(x,y,z));
        }
        public DistorterSequence random_direction_offset(Random random, int max_range, Direction[] directions, BlockPos pos) {
            return new DistorterSequence(pos.offset(directions[random.nextBetween(0, directions.length - 1)], random.nextBetween(1, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int max_range, Direction[] directions, int x, int y, int z) {
            return random_direction_offset(random, max_range, directions, new BlockPos(x,y,z));
        }

        public DistorterSequence random_direction_offset(Random random, int max_range, Direction direction, BlockPos pos) {
            return new DistorterSequence(pos.offset(direction, random.nextBetween(1, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int max_range, Direction direction, int x, int y, int z) {
            return random_direction_offset(random, max_range, direction, new BlockPos(x,y,z));
        }

        public DistorterSequence random_direction_offset(Random random, int max_range, BlockPos pos) {
            return new DistorterSequence(pos.offset(Direction.random(random), random.nextBetween(1, max_range)));
        }

        public DistorterSequence random_direction_offset(Random random, int max_range, int x, int y, int z) {
            return random_direction_offset(random, max_range, new BlockPos(x,y,z));
        }
        //endregion
    }
    
}
