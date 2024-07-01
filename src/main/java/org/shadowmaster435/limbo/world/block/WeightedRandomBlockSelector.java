package org.shadowmaster435.limbo.world.block;

import net.minecraft.block.BlockState;
import org.shadowmaster435.limbo.util.misc.HashmapBuilder;

import java.util.HashMap;
import java.util.Random;

public class WeightedRandomBlockSelector {

    private HashMap<Float,BlockState> states = new HashMap<>();

    public WeightedRandomBlockSelector(HashMap<Float, BlockState> states) {
        this.states = states;
    }

    public static HashmapBuilder<Float, BlockState> builder() {
        return new HashmapBuilder<>();
    }

    public BlockState select(Random random) {
        var rng = random.nextFloat();
        var last_closest = 2f;
        var last_closest_key = 2f;
        for (float key : states.keySet()) {
            if (Math.abs(key - rng) < last_closest) {
                last_closest_key = key;
            }
        }
        return states.get(last_closest_key);
    }



}
