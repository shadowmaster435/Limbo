package org.shadowmaster435.limbo.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;

public class ModSounds {

    public static final Identifier PORTAL_OPEN = new Identifier(Limbo.id, "portal_open");
    public static SoundEvent PORTAL_OPEN_EVENT = SoundEvent.of(PORTAL_OPEN);
    public static final Identifier PORTAL_CLOSE = new Identifier(Limbo.id, "portal_close");
    public static SoundEvent PORTAL_CLOSE_EVENT = SoundEvent.of(PORTAL_CLOSE);
    public static final Identifier EYE_SAW_YOU = new Identifier(Limbo.id, "eye_saw_you");
    public static SoundEvent EYE_SAW_YOU_EVENT = SoundEvent.of(EYE_SAW_YOU);
    public static final Identifier LIMBO_SOIL_STEP = new Identifier(Limbo.id, "limbosoil_step");
    public static SoundEvent LIMBO_SOIL_STEP_EVENT = SoundEvent.of(LIMBO_SOIL_STEP);
    public static final Identifier LIMBO_SOIL_BREAK = new Identifier(Limbo.id, "limbosoil_break");
    public static SoundEvent LIMBO_SOIL_BREAK_EVENT = SoundEvent.of(LIMBO_SOIL_BREAK);
    public static final Identifier LIMBO_STONE_BREAK = new Identifier(Limbo.id, "limbostone_break");
    public static SoundEvent LIMBO_STONE_BREAK_EVENT = SoundEvent.of(LIMBO_STONE_BREAK);
    public static final Identifier LIMBO_STONE_STEP = new Identifier(Limbo.id, "limbostone_step");
    public static SoundEvent LIMBO_STONE_STEP_EVENT = SoundEvent.of(LIMBO_STONE_STEP);
    public static final Identifier MONOLITH_HURT = new Identifier(Limbo.id, "monolith_hurt");
    public static SoundEvent MONOLITH_HURT_EVENT = SoundEvent.of(MONOLITH_HURT);
    public static final Identifier MONOLITH_STARE = new Identifier(Limbo.id, "monolith_stare");
    public static SoundEvent MONOLITH_STARE_EVENT = SoundEvent.of(MONOLITH_STARE);



    public static void register() {
        Registry.register(Registries.SOUND_EVENT, PORTAL_OPEN, PORTAL_OPEN_EVENT);
        Registry.register(Registries.SOUND_EVENT, PORTAL_CLOSE, PORTAL_CLOSE_EVENT);
        Registry.register(Registries.SOUND_EVENT, EYE_SAW_YOU, EYE_SAW_YOU_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_HURT, MONOLITH_HURT_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_STARE, MONOLITH_STARE_EVENT);

    }

}
