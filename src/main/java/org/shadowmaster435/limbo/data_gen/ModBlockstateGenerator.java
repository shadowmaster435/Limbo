package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.init.ModBlocks;

import java.util.Optional;

import static net.minecraft.data.client.TextureMap.*;

public class ModBlockstateGenerator  extends FabricModelProvider {
    public ModBlockstateGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        fences(blockStateModelGenerator);
        fence_gates(blockStateModelGenerator);
        stairs(blockStateModelGenerator);
        slabs(blockStateModelGenerator);
        doors(blockStateModelGenerator);
        pillars(blockStateModelGenerator);
        side_top_bottom(blockStateModelGenerator);
        one_texture(blockStateModelGenerator);
        mixed_types(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // ...
    }

    public static void mixed_types(BlockStateModelGenerator blockStateModelGenerator) {
        for (Block block : ModBlocks.manual.keySet()) {

            var type = ModBlocks.manual.get(block);
            switch (type) {

                case "one_texture" -> blockStateModelGenerator.registerSimpleCubeAll(block);
                case "side_top_bottom" ->  registerSideTopBottom(block, blockStateModelGenerator);
                case "pillar" -> blockStateModelGenerator.registerLog(block);

            }
        }
    }

    public static void side_top_bottom(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.side_top_bottom.keySet()) {
            var entry = ModBlocks.side_top_bottom.get(id_str);
            registerSideTopBottom(entry, blockStateModelGenerator);
        }
    }

    public static void one_texture(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.one_texture_blocks.keySet()) {
            var entry = ModBlocks.one_texture_blocks.get(id_str);
            blockStateModelGenerator.registerSimpleCubeAll(entry);
        }
    }

    public static void pillars(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.pillars.keySet()) {
            var entry = ModBlocks.pillars.get(id_str);
            blockStateModelGenerator.registerLog(entry);
        }
    }

    public static void stairs(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.stairs.keySet()) {
            var entry = ModBlocks.stairs.get(id_str);
            registerStairs(entry, blockStateModelGenerator);
        }
    }

    public static void slabs(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.slabs.keySet()) {
            var entry = ModBlocks.slabs.get(id_str);
            registerSlabs(entry, blockStateModelGenerator);
        }
    }

    public static void fence_gates(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.fence_gates.keySet()) {
            var entry = ModBlocks.fence_gates.get(id_str);
            registerFenceGate(entry, blockStateModelGenerator);
        }
    }
    public static void doors(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.doors.keySet()) {
            var entry = ModBlocks.doors.get(id_str);
            blockStateModelGenerator.registerDoor(entry);
        }
    }

    public static void fences(BlockStateModelGenerator blockStateModelGenerator) {
        for (String id_str : ModBlocks.fences.keySet()) {
            var entry = ModBlocks.fences.get(id_str);
            registerFence(entry, blockStateModelGenerator);
        }

    }
    public static void registerSlabs(Block block, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = all(block);
        var full = Models.CUBE_ALL.upload(block, "_full", textureMap, blockStateModelGenerator.modelCollector);
        var bottom = Models.SLAB.upload(block, "_bottom", textureMap, blockStateModelGenerator.modelCollector);
        var top = Models.SLAB_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Item item = block.asItem();
        Models.SLAB.upload(ModelIds.getItemModelId(item), textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, bottom, top, full));

    }
    public static void registerStairs(Block block, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = sideTopBottomTextureStairsAll(block);
        var normal = Models.STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        var inner = Models.INNER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        var outer = Models.OUTER_STAIRS.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Item item = block.asItem();
        Models.STAIRS.upload(ModelIds.getItemModelId(item), textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, inner, normal, outer));

    }

    public static void registerSideTopBottom(Block block, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = TextureMap.sideTopBottom(block);
        var model = Models.CUBE_BOTTOM_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Item item = block.asItem();
        //Models.CUBE_BOTTOM_TOP.upload(ModelIds.getItemModelId(item), TextureMap.texture(block), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block,model));
    }

    public static void registerFenceGate(Block block, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = TextureMap.texture(block);
        var closed = Models.TEMPLATE_CUSTOM_FENCE_GATE.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        var open = Models.TEMPLATE_FENCE_GATE_OPEN.upload(block,  textureMap, blockStateModelGenerator.modelCollector);
        var closed_wall = Models.TEMPLATE_CUSTOM_FENCE_GATE_WALL.upload(block,  textureMap, blockStateModelGenerator.modelCollector);
        var open_wall = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Item item = block.asItem();
        Models.TEMPLATE_FENCE_GATE.upload(ModelIds.getItemModelId(item), TextureMap.texture(block), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(block, open, closed, open_wall,closed_wall, true));
    }

    public static final Model TEMPLATE_FENCE_INVENTORY = block("fence_inventory", TextureKey.TEXTURE);
    public static final Model TEMPLATE_FENCE_POST = block("fence_post", TextureKey.TEXTURE);
    public static final Model TEMPLATE_FENCE_SIDE = block("fence_side", TextureKey.TEXTURE);
    public static TextureMap sideTopBottomTextureStairsAll(Block block) {
        return new TextureMap()
                .put(TextureKey.SIDE, getSubId(block, ""))
                .put(TextureKey.TOP, getSubId(block, ""))
                .put(TextureKey.BOTTOM, getSubId(block, ""));
    }


    public static void registerFence(Block block, BlockStateModelGenerator blockStateModelGenerator) {
    //    blockStateModelGenerator.registerSimpleCubeAll(block);
        TextureMap textureMap = TextureMap.texture(block);
        Identifier post = TEMPLATE_FENCE_POST.upload(block, "_post", textureMap, blockStateModelGenerator.modelCollector);
        Identifier limb = TEMPLATE_FENCE_SIDE.upload(block, "_side", textureMap, blockStateModelGenerator.modelCollector);
        Item item = block.asItem();
        TEMPLATE_FENCE_INVENTORY.upload(ModelIds.getItemModelId(item), TextureMap.texture(block), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector
                .accept(
                        MultipartBlockStateSupplier.create(block)
                                .with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
                                .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.UVLOCK, true).put(VariantSettings.MODEL, limb))
                                .with(
                                        When.create().set(Properties.EAST, true),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, limb).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90)

                                )
                                .with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, limb).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .with(
                                        When.create().set(Properties.WEST, true),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, limb).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270)
                                )
                );
    }
    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("minecraft", "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}