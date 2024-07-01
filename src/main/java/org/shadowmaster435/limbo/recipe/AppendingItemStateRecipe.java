package org.shadowmaster435.limbo.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.init.ModItems;
import org.shadowmaster435.limbo.init.ModRecipes;
import org.shadowmaster435.limbo.item.util.item_state.ItemStateComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class AppendingItemStateRecipe extends SpecialCraftingRecipe {


    public AppendingItemStateRecipe(CraftingRecipeCategory category) {
        super(CraftingRecipeCategory.MISC);

    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        var source_item_count = false;
        ArrayList<Item> current_modifiers = new ArrayList<>();
        for (Item source_item : ModRecipes.state_modifiers.keySet()) {
            var applicable = ModRecipes.state_modifiers.get(source_item);
            for (ItemStack stack : inventory.getHeldStacks()) {
                if (stack.getItem() == source_item) {
                    if (source_item_count) {
                        return false;
                    } else {
                        source_item_count = true;
                    }
                }
                if (applicable.containsKey(stack.getItem())) {
                    if (!current_modifiers.contains(stack.getItem())) {
                        current_modifiers.add(stack.getItem());
                    } else {
                        return false;
                    }
                }
            }
        }
        return !current_modifiers.isEmpty();
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack source = null;
        ArrayList<HashMap<String, Object>> to_append = new ArrayList<>();
        for (Item source_item : ModRecipes.state_modifiers.keySet()) {
            var applicable = ModRecipes.state_modifiers.get(source_item);
            for (ItemStack stack : inventory.getHeldStacks()) {
                if (stack.getItem() == source_item) {
                    source = stack;
                } else {
                    var current_modifier = applicable.getOrDefault(stack.getItem(), null);
                    if (current_modifier != null) {
                        current_modifier.forEach((key, value) -> to_append.add(current_modifier));
                    }
                }
            }
        }
        var builder = ItemStateComponent.Builder.start_with(source.get(ModItems.ITEM_STATE_COMPONENT_TYPE));
        ItemStack finalSource = source;
        to_append.forEach(modifier -> modifier.forEach((key, value) -> finalSource.set(ModItems.ITEM_STATE_COMPONENT_TYPE, builder.add(key, value).build())));
        return source;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ITEM_STATE;
    }
}
