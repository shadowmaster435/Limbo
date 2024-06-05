package org.shadowmaster435.limbo.init;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.shadowmaster435.limbo.data_gen.ModBlockTagGenerator;
import org.shadowmaster435.limbo.data_gen.ModLootTableGenerator;

public class ModDataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModLootTableGenerator::new);
        pack.addProvider(ModBlockTagGenerator::new);

    }

}