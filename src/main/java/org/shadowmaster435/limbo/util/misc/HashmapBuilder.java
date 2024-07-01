package org.shadowmaster435.limbo.util.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.shadowmaster435.limbo.world.block.WeightedRandomBlockSelector;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HashmapBuilder<K, V> {

    private final HashMap<K, V> result;

    public HashmapBuilder(HashMap<K, V> current) {
        this.result = current;
    }

    public HashmapBuilder() {
        this.result = new HashMap<>();
    }

    public static <K, V> HashmapBuilder<K, V> create() {
        return new HashmapBuilder<>();
    }

    public HashmapBuilder<K, V> add(K key, V value) {
        this.result.put(key, value);
        return new HashmapBuilder<>(this.result);
    }

    public <C> C build(Class<C> cl) {
        try {
            return cl.getConstructor(HashMap.class).newInstance(result);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<K, V> build() {
        return this.result;
    }


}
