package org.shadowmaster435.limbo.util.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;

import java.util.HashMap;

public class CodecHelper {


    public static Codec<HashMap<?,?>> create_hashmap_codec(Class<?> key_type, Class<?> value_type) {
        return null;
    }

}
