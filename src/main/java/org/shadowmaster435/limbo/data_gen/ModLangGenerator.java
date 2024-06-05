package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.shadowmaster435.limbo.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLangGenerator extends FabricLanguageProvider {


    public ModLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        blocks(translationBuilder);
    }

    public void blocks(TranslationBuilder translationBuilder) {
        for (String name : ModBlocks.auto_name.keySet()) {
            var entry = ModBlocks.auto_name.get(name);
            translationBuilder.add(entry, name);
        }
    }
}