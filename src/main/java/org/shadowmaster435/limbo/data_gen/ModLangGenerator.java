package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryWrapper;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.init.ModItems;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModLangGenerator extends FabricLanguageProvider {


    public ModLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        var args = Arrays.stream(FabricLoader.getInstance().getLaunchArguments(true)).toList();
        if (args.contains("blockdgen") || args.contains("dgenall")) {
            ModBlocks.auto_name.forEach((s, block) -> translationBuilder.add(block, s));
        }
        if (args.contains("itemdgen") || args.contains("dgenall")) {
            ModItems.items.forEach((s, item) -> translationBuilder.add(item, name_from_snake(s)));
        }
    }

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



}