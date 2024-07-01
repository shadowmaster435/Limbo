package org.shadowmaster435.limbo.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;

public class PlantBlockBase extends PlantBlock {
    public PlantBlockBase(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
    }
}
