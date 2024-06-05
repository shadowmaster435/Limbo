package org.shadowmaster435.limbo.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {

    public ModLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }



    @Override
    public void generate() {
        gen_self_drop();
    }

    public void gen_self_drop() {

        for (String id_str : ModBlocks.self_drop.keySet()) {
            var id = new Identifier(id_str);
            var entry = ModBlocks.self_drop.get(id_str);
            addDrop(entry, drops(entry));
        }
    }

}