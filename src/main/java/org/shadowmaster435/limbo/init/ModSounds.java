package org.shadowmaster435.limbo.init;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
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
    public static final Identifier MONOLITH_DEACTIVATE = new Identifier(Limbo.id, "monolith_deactivate");
    public static SoundEvent MONOLITH_DEACTIVATE_EVENT = SoundEvent.of(MONOLITH_DEACTIVATE);
    public static final Identifier MONOLITH_DEATH = new Identifier(Limbo.id, "monolith_death");
    public static SoundEvent MONOLITH_DEATH_EVENT = SoundEvent.of(MONOLITH_DEATH);
    public static final Identifier MONOLITH_GROUND_HIT = new Identifier(Limbo.id, "monolith_ground_hit");
    public static SoundEvent MONOLITH_GROUND_HIT_EVENT = SoundEvent.of(MONOLITH_GROUND_HIT);

    // Music
    public static final Identifier SOMEWHERE = new Identifier(Limbo.id, "somewhere");
    public static SoundEvent SOMEWHERE_EVENT = SoundEvent.of(SOMEWHERE);


    public static void register() {

        Registry.register(Registries.SOUND_EVENT, PORTAL_OPEN, PORTAL_OPEN_EVENT);
        Registry.register(Registries.SOUND_EVENT, PORTAL_CLOSE, PORTAL_CLOSE_EVENT);
        Registry.register(Registries.SOUND_EVENT, EYE_SAW_YOU, EYE_SAW_YOU_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_HURT, MONOLITH_HURT_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_STARE, MONOLITH_STARE_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_DEATH, MONOLITH_DEATH_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_DEACTIVATE, MONOLITH_DEACTIVATE_EVENT);
        Registry.register(Registries.SOUND_EVENT, MONOLITH_GROUND_HIT, MONOLITH_GROUND_HIT_EVENT);
        Registry.register(Registries.SOUND_EVENT, SOMEWHERE, SOMEWHERE_EVENT);

    }

}
