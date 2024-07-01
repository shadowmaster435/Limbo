package org.shadowmaster435.limbo.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    private static final ItemGroup ITEMS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.APPLE))
            .displayName(Text.translatable("itemGroup.limbo.items"))
            .entries(ModItemGroups::add_items)
            .build();

    private static final ItemGroup BLOCKS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.STONE))
            .displayName(Text.translatable("itemGroup.limbo.blocks"))
            .entries(ModItemGroups::add_blocks)
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of("limbo", "blocks"), BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of("limbo", "items"), ITEMS);

    }
    public static void add_items(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        ModItems.items.forEach((name, item) -> entries.add(new ItemStack(item)));
    }
    public static void add_blocks(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        ModBlocks.blocks_in_item_group.forEach((block) -> entries.add(new ItemStack(block)));
    }

}
