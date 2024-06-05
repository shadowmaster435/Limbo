package org.shadowmaster435.limbo.init;

import net.minecraft.sound.BlockSoundGroup;

public class ModBlockSoundGroups {



    public static final BlockSoundGroup LIMBO_SOIL;
    public static final BlockSoundGroup LIMBO_STONE;

    static {
        LIMBO_SOIL = new BlockSoundGroup(0.65F, 1.25F, ModSounds.LIMBO_SOIL_BREAK_EVENT, ModSounds.LIMBO_SOIL_STEP_EVENT, ModSounds.LIMBO_SOIL_BREAK_EVENT, ModSounds.LIMBO_SOIL_STEP_EVENT, ModSounds.LIMBO_SOIL_BREAK_EVENT);
        LIMBO_STONE = new BlockSoundGroup(1F, 1F, ModSounds.LIMBO_STONE_BREAK_EVENT, ModSounds.LIMBO_STONE_STEP_EVENT, ModSounds.LIMBO_STONE_BREAK_EVENT, ModSounds.LIMBO_STONE_STEP_EVENT, ModSounds.LIMBO_STONE_BREAK_EVENT);
    }
}
