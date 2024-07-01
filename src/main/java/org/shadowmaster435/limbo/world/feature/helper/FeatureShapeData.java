package org.shadowmaster435.limbo.world.feature.helper;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.limbo.util.callable.Callable;
import org.shadowmaster435.limbo.world.block.WeightedRandomBlockSelector;

import java.util.Random;

public record FeatureShapeData(WeightedRandomBlockSelector randomBlockSelector, @Nullable Distorter.DistorterSequence distorterSequence, @Nullable BlockPlacementConditions.Condition condition) {
    public static FeatureShapeData create(WeightedRandomBlockSelector randomBlockSelector, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(randomBlockSelector, null, condition);
    }

    public static FeatureShapeData create(Block block, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, block.getDefaultState()).build()), null, condition);
    }

    public static FeatureShapeData create(BlockState state, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, state).build()), null, condition);
    }

    public static FeatureShapeData create(WeightedRandomBlockSelector randomBlockSelector, Distorter.DistorterSequence distorterSequence, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(randomBlockSelector, distorterSequence, condition);
    }

    public static FeatureShapeData create(Block block, Distorter.DistorterSequence distorterSequence, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, block.getDefaultState()).build()), distorterSequence, condition);
    }

    public static FeatureShapeData create(BlockState state, Distorter.DistorterSequence distorterSequence, BlockPlacementConditions.Condition condition) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, state).build()), distorterSequence, condition);
    }
    public static FeatureShapeData create(WeightedRandomBlockSelector randomBlockSelector) {
        return new FeatureShapeData(randomBlockSelector, null, null);
    }

    public static FeatureShapeData create(Block block) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, block.getDefaultState()).build()), null, null);
    }

    public static FeatureShapeData create(BlockState state) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, state).build()), null, null);
    }

    public static FeatureShapeData create(WeightedRandomBlockSelector randomBlockSelector, Distorter.DistorterSequence distorterSequence) {
        return new FeatureShapeData(randomBlockSelector, distorterSequence, null);
    }

    public static FeatureShapeData create(Block block, Distorter.DistorterSequence distorterSequence) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, block.getDefaultState()).build()), distorterSequence, null);
    }

    public static FeatureShapeData create(BlockState state, Distorter.DistorterSequence distorterSequence) {
        return new FeatureShapeData(new WeightedRandomBlockSelector(WeightedRandomBlockSelector.builder().add(1f, state).build()), distorterSequence, null);
    }

    public BlockState select(Random random) {
        return randomBlockSelector.select(random);
    }

    public BlockPos distort(BlockPos pos, World world) {
        if (distorterSequence == null) {
            return pos;
        } else {
            return distorterSequence.add(pos);
        }
    }

    public boolean test(BlockPos pos, World world) {
        if (condition == null) {
            return true;
        } else {
            return new Callable(this, condition.method_name, pos.getClass(), world.getClass()).call(pos, world);
        }
    }

}
