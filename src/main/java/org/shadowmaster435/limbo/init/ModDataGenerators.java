package org.shadowmaster435.limbo.init;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import org.shadowmaster435.limbo.data_gen.*;

import java.util.Arrays;

public class ModDataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var args = Arrays.stream(FabricLoader.getInstance().getLaunchArguments(true)).toList();
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(ModModelGenerator::new);
        if (args.contains("blockdgen") || args.contains("dgenall")) {
            pack.addProvider(ModLootTableGenerator::new);
            pack.addProvider(ModBlockTagGenerator::new);
        }
        if (args.contains("recipedgen") || args.contains("dgenall")) {
            pack.addProvider(ModRecipeGenerator::new);
        }
        pack.addProvider(ModLangGenerator::new);
    }

}