package org.shadowmaster435.limbo.init;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.world.feature.TenebraTreeFeature;
import org.shadowmaster435.limbo.world.feature.config.TenebraTreeFeatureConfig;

public class ModFeatures {
    public static final Identifier TENEBRA_TREE_FEATURE_ID = new Identifier("limbo", "tenebra_tree");
    public static final TenebraTreeFeature TENEBRA_TREE_FEATURE = new TenebraTreeFeature(TenebraTreeFeatureConfig.CODEC);




    public static void register() {
        Registry.register(Registries.FEATURE, TENEBRA_TREE_FEATURE_ID, TENEBRA_TREE_FEATURE);

    }

}
