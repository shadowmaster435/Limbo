package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.shadowmaster435.limbo.init.ModBlocks;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {

    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        var args = Arrays.stream(FabricLoader.getInstance().getLaunchArguments(true)).toList();
        if (args.contains("recipedgen") || args.contains("dgenall")) {
            fences(exporter);
            stairs(exporter);
            fence_gates(exporter);
            doors(exporter);
            slabs(exporter);
        }
    }
    public static void fences(RecipeExporter exporter) {

        for (Block material : ModBlocks.fence_recipe.keySet()) {
            var block = ModBlocks.fence_recipe.get(material);

            FabricRecipeProvider.createFenceRecipe(block, Ingredient.ofItems(material)).criterion(FabricRecipeProvider.hasItem(material), FabricRecipeProvider.conditionsFromItem(material)).offerTo(exporter);
        }
    }

    public static void stairs(RecipeExporter exporter) {
        for (Block material : ModBlocks.stair_recipe.keySet()) {
            var block = ModBlocks.stair_recipe.get(material);

            FabricRecipeProvider.createStairsRecipe(block, Ingredient.ofItems(material)).criterion(FabricRecipeProvider.hasItem(material), FabricRecipeProvider.conditionsFromItem(material)).offerTo(exporter);

        }
    }

    public static void slabs(RecipeExporter exporter) {
        for (Block material : ModBlocks.slab_recipe.keySet()) {
            var block = ModBlocks.slab_recipe.get(material);
            FabricRecipeProvider.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, block, Ingredient.ofItems(material)).criterion(FabricRecipeProvider.hasItem(material), FabricRecipeProvider.conditionsFromItem(material)).offerTo(exporter);
        }
    }

    public static void fence_gates(RecipeExporter exporter) {
        for (Block material : ModBlocks.fence_gate_recipe.keySet()) {
            var block = ModBlocks.fence_gate_recipe.get(material);
            FabricRecipeProvider.createFenceGateRecipe(block, Ingredient.ofItems(material)).criterion(FabricRecipeProvider.hasItem(material), FabricRecipeProvider.conditionsFromItem(material)).offerTo(exporter);

        }
    }
    public static void doors(RecipeExporter exporter) {
        for (Block material : ModBlocks.door_recipe.keySet()) {
            var block = ModBlocks.door_recipe.get(material);
            FabricRecipeProvider.createDoorRecipe(block, Ingredient.ofItems(material)).criterion(FabricRecipeProvider.hasItem(material), FabricRecipeProvider.conditionsFromItem(material)).offerTo(exporter);

        }
    }


}