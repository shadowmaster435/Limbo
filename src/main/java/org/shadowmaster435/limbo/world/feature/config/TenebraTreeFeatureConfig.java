package org.shadowmaster435.limbo.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public record TenebraTreeFeatureConfig(IntProvider trunk_height, IntProvider trunk_size, SimpleBlockStateProvider logs, SimpleBlockStateProvider leaves) implements FeatureConfig {
    public static final Codec<TenebraTreeFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            IntProvider.createValidatingCodec(4, 10).fieldOf("trunk_height").forGetter(TenebraTreeFeatureConfig::trunk_height),
                            IntProvider.createValidatingCodec(1, 2).fieldOf("trunk_size").forGetter(TenebraTreeFeatureConfig::trunk_height),
                            SimpleBlockStateProvider.CODEC.fieldOf("logs").forGetter(TenebraTreeFeatureConfig::logs),
                            SimpleBlockStateProvider.CODEC.fieldOf("leaves").forGetter(TenebraTreeFeatureConfig::leaves)
                    ).apply(instance, TenebraTreeFeatureConfig::new));
}