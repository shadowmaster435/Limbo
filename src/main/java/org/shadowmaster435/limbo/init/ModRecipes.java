package org.shadowmaster435.limbo.init;

import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.recipe.AppendingItemStateRecipe;

import java.util.HashMap;

public class ModRecipes {
    public static final RecipeSerializer<AppendingItemStateRecipe> ITEM_STATE = register(
              Limbo.id + ":" + "crafting_special_item_state_recipe", new SpecialRecipeSerializer<>(AppendingItemStateRecipe::new)
    );
    public static HashMap<Item, HashMap<Item, HashMap<String, Object>>> state_modifiers = new HashMap<>();


    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        //		ComplexRecipeJsonBuilder.create(ShulkerBoxColoringRecipe::new).offerTo(exporter, "shulker_box_coloring");
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(id), serializer);

    }

    public static void register_providers() {
    }

    public static <T> void register_state_modifier(Item item, Item modifier_item, String modifier_name, T modifier_value) {
        HashMap<Item, HashMap<String, Object>> modifier_item_layer = new HashMap<>();
        HashMap<String, Object> modifier_layer = new HashMap<>();
        state_modifiers.putIfAbsent(item, modifier_item_layer);
        state_modifiers.get(item).putIfAbsent(modifier_item, modifier_layer);
        state_modifiers.get(item).get(modifier_item).putIfAbsent(modifier_name, modifier_value);
    }

}
