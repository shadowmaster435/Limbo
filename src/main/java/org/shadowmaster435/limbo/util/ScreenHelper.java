package org.shadowmaster435.limbo.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.level.storage.LevelStorage;
import org.shadowmaster435.limbo.shader.CollapseOrbDepthFX;

import java.io.IOException;
import java.util.Iterator;

public class ScreenHelper {




    public static void create_mod_title_screen(Screen screen) {
        var client = MinecraftClient.getInstance();
        boolean bl = canReadLimboData();

        screen.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.limbo"), (button) -> {
            if (bl) {
                client.createIntegratedServerLoader().start("LIMBO", () -> {
                    client.setScreen(screen);

                });
            } else {
                client.createIntegratedServerLoader().createAndStart("LIMBO", MinecraftServer.DEMO_LEVEL_INFO, GeneratorOptions.DEMO_OPTIONS, WorldPresets::createDemoOptions, screen);
            }

        }).dimensions(screen.width / 2 - 100, screen.height / 4 + 48, 200, 20).build());
        screen.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.limboquit"), (button) -> {
            MinecraftClient.getInstance().close();

        }).dimensions(screen.width / 2 - 100, (screen.height / 4 + 48) + 24, 200, 20).build());

    }

    public static void render_title_bg(TitleScreen screen, DrawContext context, int mouseX, int mouseY, float delta ) {
        screen.renderBackground(context, mouseX, mouseY, delta);
        Iterator var5 = screen.drawables.iterator();
        context.fill(0,0,context.getScaledWindowWidth(), context.getScaledWindowHeight(), ColorHelper.Argb.getArgb(255,0,0,0));
        CollapseOrbDepthFX.INSTANCE.eye_delta.set(0);
        CollapseOrbDepthFX.INSTANCE.orb_scale.set(0);

        while(var5.hasNext()) {
            Drawable drawable = (Drawable)var5.next();
            drawable.render(context, mouseX, mouseY, delta);
        }

    }

    public static boolean canReadLimboData() {
        try {
            boolean var2;
            try (LevelStorage.Session session = MinecraftClient.getInstance().getLevelStorage().createSessionWithoutSymlinkCheck("LIMBO")) {
                var2 = session.levelDatExists();
            }

            return var2;
        } catch (IOException var6) {
            SystemToast.addWorldAccessFailureToast(MinecraftClient.getInstance(), "LIMBO");
            return false;
        }
    }
}
