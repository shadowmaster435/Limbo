package org.shadowmaster435.limbo.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.item.util.item_state.ItemStateComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class ModItems {
    public static HashMap<String, Item> items = new HashMap<>();
    public static HashMap<String, Item> generic_items = new HashMap<>();
    public static final Item ONYX_BERRIES = new Item(new Item.Settings().food(new FoodComponent(3, 0.6f,false, 1.2f, new ArrayList<>())));

    public static final ItemStateComponent ITEM_STATE_COMPONENT = new ItemStateComponent.Builder().build();
    public static DataComponentType<ItemStateComponent> ITEM_STATE_COMPONENT_TYPE;

    public static final String[] generic_item_names = {
            "tenebra_twig", "illuminum", "monolith_fragment", "dim_shard"
    };

    public static void register() {

        Arrays.stream(generic_item_names).forEach(ModItems::register_generic);
        register_manual("onyx_berries", ONYX_BERRIES);
        register_tool_set(ToolMaterials.WOOD, "tenebra");

        ITEM_STATE_COMPONENT_TYPE = register_component("item_state", ITEM_STATE_COMPONENT);

    }
//    public static final DataComponentType<ItemStateComponent> ITEM_STATE = register(
//            "bundle_contents", builder -> builder.codec(BundleContentsComponent.CODEC).packetCodec(BundleContentsComponent.PACKET_CODEC).cache()
//    );

    public static <T> DataComponentType<T> register_component(String component_name, T type) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(Limbo.id, component_name), new DataComponentType.Builder<T>().codec((Codec<T>) Codecs.BASIC_OBJECT).build());
    }

    public static void register_manual(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, name), item);
        items.put(name, item);
        generic_items.put(name, item);
    }
    public static void register_generic(String name) {
        final Item new_item = new Item(new Item.Settings());
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, name), new_item);
        items.put(name, new_item);
        generic_items.put(name, new_item);
    }
    
    public static void register_tool_set(ToolMaterial material, String prefix) {
        var sword = new SwordItem(material, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.WOOD, 3, -2.4F)));
        var pickaxe = new PickaxeItem(material, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.WOOD, 2, -2.8F)));
        var axe = new AxeItem(material, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.WOOD, 6, -3.2F)));
        var shovel = new ShovelItem(material, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.WOOD, 1.5f, -3F)));
        var hoe = new HoeItem(material, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.WOOD, 0, -3F)));
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, prefix + "_sword"), sword);
        items.put(prefix + "_sword", sword);
        generic_items.put(prefix + "_sword", sword);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, prefix + "_pickaxe"), pickaxe);
        items.put(prefix + "_pickaxe", pickaxe);
        generic_items.put(prefix + "_pickaxe", pickaxe);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, prefix + "_axe"), axe);
        items.put(prefix + "_axe", axe);
        generic_items.put(prefix + "_axe", axe);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, prefix + "_shovel"), shovel);
        items.put(prefix + "_shovel", shovel);
        generic_items.put(prefix + "_shovel", shovel);
        Registry.register(Registries.ITEM, new Identifier(Limbo.id, prefix + "_hoe"), hoe);
        items.put(prefix + "_hoe", hoe);
        generic_items.put(prefix + "_hoe", hoe);
    }

    public static void register_layer_predicate(String item_name, String predicate_name, Supplier<ClampedModelPredicateProvider> func) {
        ModelPredicateProviderRegistry.register(items.get(item_name), new Identifier(Limbo.id + ":" + predicate_name), func.get());
    }


}
