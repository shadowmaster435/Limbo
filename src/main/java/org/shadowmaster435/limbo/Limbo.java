package org.shadowmaster435.limbo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.command.TitleCommand;
import org.shadowmaster435.limbo.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shadowmaster435.limbo.resource.ModResourceLoader;
import org.shadowmaster435.limbo.util.MiscUtil;

import java.util.Arrays;

public class Limbo implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final Logger LOGGER = LogManager.getLogger("Limbo");
    public static final String id = "limbo";
    public static boolean data_generator_mode = false;
    public static boolean dev_mode = false;

    @Override
    public void onInitialize() {
        dev_mode = Arrays.stream(FabricLoader.getInstance().getLaunchArguments(true)).toList().contains("limbodevmode");

        ModResourceLoader.register();
        ModBlocks.register();
        ModItems.register();
        ModSounds.register();
        ModFeatures.register();
        if (Arrays.asList(FabricLoader.getInstance().getLaunchArguments(true)).contains("dgen")) {
            return;
        }
        ModItemGroups.register();
        ModEntities.register();
        MiscUtil.init_globals();
    }
}
