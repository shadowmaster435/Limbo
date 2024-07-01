package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.init.ModBlocks;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    private static final TagKey<Block> AXE_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft:mineable/axe"));
    private static final TagKey<Block> PICKAXE_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft:mineable/pickaxe"));
    private static final TagKey<Block> FENCES = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft:fences"));
    private static final TagKey<Block> WOODEN_FENCES = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft:wooden_fences"));

    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var args = Arrays.stream(FabricLoader.getInstance().getLaunchArguments(true)).toList();
        if (args.contains("blockdgen") || args.contains("dgenall")) {
            axe(getOrCreateTagBuilder(AXE_EFFECTIVE));
            pickaxe(getOrCreateTagBuilder(PICKAXE_EFFECTIVE));
            fences(getOrCreateTagBuilder(FENCES));
            fences(getOrCreateTagBuilder(WOODEN_FENCES));
        }
    }

    public void pickaxe(FabricTagBuilder builder) {
        for (String id_str : ModBlocks.any_pickaxe_effective.keySet()) {
            var entry = ModBlocks.any_pickaxe_effective.get(id_str);
            builder.add(entry);
        }
        builder.setReplace(false);
    }
    public void fences(FabricTagBuilder builder) {
        for (String id_str : ModBlocks.fences.keySet()) {
            var entry = ModBlocks.fences.get(id_str);
            builder.add(entry);
        }
        builder.setReplace(false);
    }

    public void axe(FabricTagBuilder builder) {
        for (String id_str : ModBlocks.axe_effective.keySet()) {
            var entry = ModBlocks.axe_effective.get(id_str);
            builder.add(entry);

        }
        builder.setReplace(false);

    }
}
