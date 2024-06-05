package org.shadowmaster435.limbo.client;

import net.fabricmc.api.ClientModInitializer;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.init.ModEntities;
import org.shadowmaster435.limbo.init.ModShaders;

public class LimboClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        ModBlocks.renderer();
        ModBlocks.render_layers();
        ModShaders.register();
        ModEntities.client();
    }
}
