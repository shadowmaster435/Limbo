package org.shadowmaster435.limbo;

import net.fabricmc.api.ModInitializer;
import org.shadowmaster435.limbo.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shadowmaster435.limbo.resource.ModResourceLoader;
import org.shadowmaster435.limbo.util.MiscUtil;

public class Limbo implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Logger LOGGER = LogManager.getLogger("Limbo");
    public static final String id = "limbo";
    public static boolean datagenmode = false;


    @Override
    public void onInitialize() {
        ModResourceLoader.register();
        ModBlocks.register();
        ModItems.register();
        ModSounds.register();
        ModFeatures.register();
        if (datagenmode) {
            return;
        }
        ModEntities.register();
        MiscUtil.init_globals();
    }
}
