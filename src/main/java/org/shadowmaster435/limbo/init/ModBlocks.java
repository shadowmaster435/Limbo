package org.shadowmaster435.limbo.init;


import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.GlitchBlock;
import org.shadowmaster435.limbo.block.IlluminumLightBlock;
import org.shadowmaster435.limbo.block.LuminatorBlock;
import org.shadowmaster435.limbo.block.ModelTest;
import org.shadowmaster435.limbo.block.base.AbstractCraftingTableBlock;
import org.shadowmaster435.limbo.block.entity.GlitchBlockEntity;
import org.shadowmaster435.limbo.block.entity.LuminatorEntity;
import org.shadowmaster435.limbo.block.entity.ModelTestEntity;
import org.shadowmaster435.limbo.block.entity.renderer.GlitchBlockRenderer;
import org.shadowmaster435.limbo.block.entity.renderer.LuminatorRenderer;
import org.shadowmaster435.limbo.block.entity.renderer.ModelTestRenderer;

import java.util.HashMap;
import java.util.Optional;

public class ModBlocks {
    //region Vars
    public static final GlitchBlock GLITCH_BLOCK = new GlitchBlock(AbstractBlock.Settings.create().sounds(ModBlockSoundGroups.LIMBO_STONE).strength(0f).nonOpaque().noCollision());
    public static BlockEntityType<GlitchBlockEntity> GLITCH_BLOCK_ENTITY;

    public static final SaplingGenerator TENEBRA = new SaplingGenerator(
            "tenebra", Optional.empty(), Optional.of(ConfiguredFeatures.of("limbo:tenebra_tree")), Optional.of(ConfiguredFeatures.of("limbo:tenebra_tree"))
    ); // feature needs tweaking to avoid leaf decay

    public static final ModelTest MODEL_TEST = new ModelTest(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).nonOpaque().burnable().strength(0.2f));
    public static final IlluminumLightBlock ILLUMINUM_LIGHT = new IlluminumLightBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).breakInstantly().nonOpaque().noCollision().luminance(a -> 12).strength(0.01f));
    public static final AbstractCraftingTableBlock TENEBRA_CRAFTING_TABLE = new AbstractCraftingTableBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
    public static final SaplingBlock TENEBRA_SAPLING = new SaplingBlock(TENEBRA, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).burnable().noCollision().strength(0.01f));

    public static final LuminatorBlock LUMINATOR_BLOCK = new LuminatorBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).nonOpaque().burnable().strength(0.2f));


    public static HashMap<String, Block> auto_reg = new HashMap<>();
    public static HashMap<String, Block> self_drop = new HashMap<>();
    public static HashMap<String, Block> axe_effective = new HashMap<>();
    public static HashMap<String, Block> any_pickaxe_effective = new HashMap<>();
    public static HashMap<String, Block> pillars = new HashMap<>();
    public static HashMap<String, Block> one_texture_blocks = new HashMap<>();
    public static HashMap<String, Block> side_top_bottom = new HashMap<>();

    public static HashMap<Block, String> manual = new HashMap<>();

    public static HashMap<String, Block> auto_name = new HashMap<>();

    public static HashMap<String, Block> doors = new HashMap<>();
    public static HashMap<String, Block> fences = new HashMap<>();
    public static HashMap<String, Block> fence_gates = new HashMap<>();
    public static HashMap<String, Block> slabs = new HashMap<>();
    public static HashMap<String, Block> stairs = new HashMap<>();

    public static HashMap<Block, Block> door_recipe = new HashMap<>();
    public static HashMap<Block, Block> fence_recipe = new HashMap<>();
    public static HashMap<Block, Block> fence_gate_recipe = new HashMap<>();
    public static HashMap<Block, Block> slab_recipe = new HashMap<>();
    public static HashMap<Block, Block> stair_recipe = new HashMap<>();


    public static BlockEntityType<ModelTestEntity> MODEL_TEST_ENT;
    public static BlockEntityType<LuminatorEntity> LUMINATOR_BLOCK_ENTITY;
    //endregion
    //region Registry
    public static void register() {
        var wood_settings = AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f);
        var leaf_settings = AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).nonOpaque().burnable().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never).strength(0.2f);
        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "glitch_block"), GLITCH_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "glitch_block"), new BlockItem(GLITCH_BLOCK, new Item.Settings()));
        register_one_texture_block("silhouetted_soil", AbstractBlock.Settings.create().sounds(ModBlockSoundGroups.LIMBO_SOIL).strength(0.5f));
        register_side_top_bottom("silhouetted_grass_block", AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).strength(0.6f));
        register_one_texture_block("silhouetted_stone", AbstractBlock.Settings.create().sounds(ModBlockSoundGroups.LIMBO_STONE).requiresTool().requiresTool().strength(1.5F, 6.0F));
        register_leaves("tenebra_leaves", leaf_settings);
        register_one_texture_block("tenebra_planks", wood_settings);

        create_wood_blocks("tenebra");

        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "tenebra_crafting_table"), TENEBRA_CRAFTING_TABLE);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "tenebra_crafting_table"), new BlockItem(TENEBRA_CRAFTING_TABLE, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "illuminum_light"), ILLUMINUM_LIGHT);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "illuminum_light"), new BlockItem(ILLUMINUM_LIGHT, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "tenebra_sapling"), TENEBRA_SAPLING);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "tenebra_sapling"), new BlockItem(TENEBRA_SAPLING, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "model_test"), MODEL_TEST);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "model_test"), new BlockItem(MODEL_TEST, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier(Limbo.id, "luminator"), LUMINATOR_BLOCK);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, "luminator"), new BlockItem(LUMINATOR_BLOCK, new Item.Settings()));

        add_manual(TENEBRA_CRAFTING_TABLE, "side_top_bottom");

        GLITCH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Limbo.id + ":" + "glitch_block_entity", BlockEntityType.Builder.create(GlitchBlockEntity::new, GLITCH_BLOCK).build(null));

        if (Limbo.datagenmode) {
            return;
        }
        MODEL_TEST_ENT = Registry.register(Registries.BLOCK_ENTITY_TYPE, Limbo.id + ":" + "model_test", BlockEntityType.Builder.create(ModelTestEntity::new, MODEL_TEST).build(null));
        LUMINATOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Limbo.id + ":" + "luminator_block_entity", BlockEntityType.Builder.create(LuminatorEntity::new, LUMINATOR_BLOCK).build(null));

    }

    public static void add_manual(Block block, String type) {
        manual.put(block, type);
    }

    public static void render_layers() {
        set_cutout(TENEBRA_SAPLING);
        set_cutout(ILLUMINUM_LIGHT);
        set_cutout("tenebra_leaves");
    }

    public static void set_cutout(String block_name) {

        BlockRenderLayerMap.INSTANCE.putBlock(Registries.BLOCK.get(new Identifier(Limbo.id, block_name)), RenderLayer.getCutout());
    }

    public static void set_cutout(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }

    public static void renderer() {
        BlockEntityRendererFactories.register(GLITCH_BLOCK_ENTITY, GlitchBlockRenderer::new);
        BlockEntityRendererFactories.register(MODEL_TEST_ENT, ModelTestRenderer::new);
        BlockEntityRendererFactories.register(LUMINATOR_BLOCK_ENTITY, LuminatorRenderer::new);

    }
    //endregion
    //region Basic Autoreg
    public static void register_leaves(String name, AbstractBlock.Settings settings) {
        final LeavesBlock BLOCK = new LeavesBlock(settings);
        var id = new Identifier(Limbo.id, name);
        Registry.register(Registries.BLOCK, id, BLOCK);
        Registry.register(Registries.ITEM, id, new BlockItem(BLOCK, new Item.Settings()));
        one_texture_blocks.put(id.toString(), BLOCK);
        auto_name.put(name_from_snake(name), BLOCK);

    }

    public static void register_one_texture_block(String name, AbstractBlock.Settings settings) {
        final Block BLOCK = new Block(settings);
        var id = new Identifier(Limbo.id, name);
        Registry.register(Registries.BLOCK, id, BLOCK);
        Registry.register(Registries.ITEM, id, new BlockItem(BLOCK, new Item.Settings()));
        one_texture_blocks.put(id.toString(), BLOCK);
        auto_name.put(name_from_snake(name), BLOCK);
    }

    public static void register_pillar_block(String name, AbstractBlock.Settings settings) {
        final PillarBlock BLOCK = new PillarBlock(settings);
        var id = new Identifier(Limbo.id, name);
        Registry.register(Registries.BLOCK, id, BLOCK);
        Registry.register(Registries.ITEM, id, new BlockItem(BLOCK, new Item.Settings()));
        pillars.put(id.toString(), BLOCK);
        auto_name.put(name_from_snake(name), BLOCK);

    }


    public static void register_side_top_bottom(String name, AbstractBlock.Settings settings) {
        final Block BLOCK = new Block(settings);
        var id = new Identifier(Limbo.id, name);
        Registry.register(Registries.BLOCK, id, BLOCK);
        Registry.register(Registries.ITEM, id, new BlockItem(BLOCK, new Item.Settings()));
        side_top_bottom.put(id.toString(), BLOCK);
        auto_name.put(name_from_snake(name), BLOCK);

    }

    //endregion
    //region Complex Autoreg
    public static void create_wood_blocks(String prefix) {
        var door = new DoorBlock(BlockSetType.OAK, AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var log = new PillarBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var wood = new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var stripped_log = new PillarBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var stripped_wood = new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var fence = new FenceBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var fence_gate = new FenceGateBlock(WoodType.OAK, AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
        var log_id = new Identifier(Limbo.id, prefix + "_log");
        var stripped_log_id = new Identifier(Limbo.id, "stripped_" + prefix + "_log");
        var wood_id = new Identifier(Limbo.id, prefix + "_wood");
        var stripped_wood_id = new Identifier(Limbo.id, "stripped_" + prefix + "_wood");
        var fence_id = new Identifier(Limbo.id, prefix + "_fence");
        var fence_gate_id = new Identifier(Limbo.id, prefix + "_fence_gate");
        var door_id = new Identifier(Limbo.id, prefix + "_door");

        Registry.register(Registries.BLOCK, stripped_log_id, stripped_log);
        Registry.register(Registries.ITEM, stripped_log_id, new BlockItem(stripped_log, new Item.Settings()));

        Registry.register(Registries.BLOCK, stripped_wood_id, stripped_wood);
        Registry.register(Registries.ITEM, stripped_wood_id, new BlockItem(stripped_wood, new Item.Settings()));

        Registry.register(Registries.BLOCK, log_id, log);
        Registry.register(Registries.ITEM, log_id, new BlockItem(log, new Item.Settings()));

        Registry.register(Registries.BLOCK, wood_id, wood);
        Registry.register(Registries.ITEM, wood_id, new BlockItem(wood, new Item.Settings()));

        Registry.register(Registries.BLOCK, fence_id, fence);
        Registry.register(Registries.ITEM, fence_id, new BlockItem(fence, new Item.Settings()));

        Registry.register(Registries.BLOCK, fence_gate_id, fence_gate);
        Registry.register(Registries.ITEM, fence_gate_id, new BlockItem(fence_gate, new Item.Settings()));

        Registry.register(Registries.BLOCK, door_id, door);
        Registry.register(Registries.ITEM, door_id, new BlockItem(door, new Item.Settings()));
        auto_name.put(name_from_snake(prefix + "_log"), log);
        auto_name.put(name_from_snake("stripped_" + prefix + "_log"), stripped_log);
        auto_name.put(name_from_snake(prefix + "_wood"), wood);
        auto_name.put(name_from_snake("stripped_" + prefix + "_wood"), stripped_wood);
        auto_name.put(name_from_snake(prefix + "_door"), door);
        auto_name.put(name_from_snake(prefix + "_fence"), fence);
        auto_name.put(name_from_snake(prefix + "_fence_gate"), fence_gate);

        auto_reg.put(log_id.toString(), log);
        auto_reg.put(stripped_log_id.toString(), stripped_log);
        auto_reg.put(wood_id.toString(), wood);
        auto_reg.put(stripped_wood_id.toString(), stripped_wood);
        auto_reg.put(fence_gate_id.toString(), fence_gate);
        auto_reg.put(fence_id.toString(), fence);
        auto_reg.put(door_id.toString(), door);
        self_drop.put(log_id.toString(), log);
        self_drop.put(stripped_log_id.toString(), stripped_log);
        self_drop.put(wood_id.toString(), wood);
        self_drop.put(stripped_wood_id.toString(), stripped_wood);
        self_drop.put(fence_gate_id.toString(), fence_gate);
        self_drop.put(fence_id.toString(), fence);
        self_drop.put(door_id.toString(), door);
        axe_effective.put(log_id.toString(), log);
        axe_effective.put(stripped_log_id.toString(), stripped_log);
        axe_effective.put(wood_id.toString(), wood);
        axe_effective.put(stripped_wood_id.toString(), stripped_wood);
        axe_effective.put(fence_gate_id.toString(), fence_gate);
        axe_effective.put(fence_id.toString(), fence);
        axe_effective.put(door_id.toString(), door);
        pillars.put(log_id.toString(), log);
        pillars.put(stripped_log_id.toString(), stripped_log);
        one_texture_blocks.put(wood_id.toString(), wood);
        one_texture_blocks.put(stripped_wood_id.toString(), stripped_wood);
        fences.put(fence_id.toString(), fence);
        fence_gates.put(fence_gate_id.toString(), fence_gate);
        doors.put(door_id.toString(), door);
        door_recipe.put(Registries.BLOCK.get(new Identifier(Limbo.id, prefix + "_planks")), door);
        fence_recipe.put(Registries.BLOCK.get(new Identifier(Limbo.id, prefix + "_planks")), fence);
        fence_gate_recipe.put(Registries.BLOCK.get(new Identifier(Limbo.id, prefix + "_planks")), fence_gate);
        set_cutout(door);
        stairs_slabs(prefix, false);
    }



    public static void stairs_slabs(String prefix, boolean is_stone) {
        StairsBlock stair;
        SlabBlock slab;
        var stairs_id = new Identifier(Limbo.id, prefix + "_stairs");
        var slab_id = new Identifier(Limbo.id, prefix + "_slab");
        if (!is_stone) {
            stair = new StairsBlock(Blocks.OAK_STAIRS.getDefaultState(), AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
            slab = new SlabBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD).burnable().strength(2f));
            Registry.register(Registries.BLOCK, slab_id, slab);
            Registry.register(Registries.ITEM, slab_id, new BlockItem(slab, new Item.Settings()));
            Registry.register(Registries.BLOCK, stairs_id, stair);
            Registry.register(Registries.ITEM, stairs_id, new BlockItem(stair, new Item.Settings()));
            auto_reg.put(stairs_id.toString(), stair);
            auto_reg.put(slab_id.toString(), slab);
            auto_name.put(name_from_snake(prefix + "_slab"), slab);
            auto_name.put(name_from_snake(prefix + "_stairs"), stair);
            stair_recipe.put(Registries.BLOCK.get(new Identifier(Limbo.id, prefix + "_planks")), stair);
            slab_recipe.put(Registries.BLOCK.get(new Identifier(Limbo.id, prefix + "_planks")), slab);

            self_drop.put(stairs_id.toString(), stair);
            self_drop.put(slab_id.toString(), slab);
            axe_effective.put(stairs_id.toString(), stair);
            axe_effective.put(slab_id.toString(), slab);
        } else {
            stair = new StairsBlock(Blocks.STONE_STAIRS.getDefaultState(), AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).requiresTool().requiresTool().strength(1.5F, 6.0F));
            slab = new SlabBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).requiresTool().requiresTool().strength(1.5F, 6.0F));
            Registry.register(Registries.BLOCK, slab_id, slab);
            Registry.register(Registries.ITEM, slab_id, new BlockItem(slab, new Item.Settings()));
            Registry.register(Registries.BLOCK, stairs_id, stair);
            Registry.register(Registries.ITEM, stairs_id, new BlockItem(stair, new Item.Settings()));
            auto_reg.put(stairs_id.toString(), stair);
            auto_reg.put(slab_id.toString(), slab);
            auto_name.put(name_from_snake(prefix + "_slab"), slab);
            auto_name.put(name_from_snake(prefix + "_stairs"), stair);

            self_drop.put(stairs_id.toString(), stair);
            self_drop.put(slab_id.toString(), slab);
            any_pickaxe_effective.put(stairs_id.toString(), stair);
            any_pickaxe_effective.put(slab_id.toString(), slab);
        }
        stairs.put(stairs_id.toString(), stair);
        slabs.put(slab_id.toString(), slab);

    }
    //endregion
    //region Utility
    public static String name_from_snake(String snake_case_name) {
        var spaced = snake_case_name.replace("_", " ");
        StringBuilder result = new StringBuilder();
        var space_found = true;
        for (int i = 0; i < spaced.length(); ++i) {
            var let = String.valueOf(spaced.charAt(i));
            if (space_found && !let.equals(" ")) {
                result.append(let.toUpperCase());
                space_found = false;
            } else {
                result.append(let);
                if (let.equals(" ")) {
                    space_found = true;
                }
            }
        }
        return result.toString();
    }
    public static Block get_block(Identifier id) {
        return auto_reg.get(id.toString());
    }
    //endregion
}
