package org.shadowmaster435.limbo.init;

import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.SamplerUniform;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.shader.*;

import java.util.HashMap;

public class ModShaders {

    public static HashMap<String, RenderLayerShader> shaders = new HashMap<>();

    public static final ManagedCoreShader testShader = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Limbo.id, "test"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT);
    public static final SamplerUniform ttr = testShader.findSampler("SamplerTest");

    public static void register() {


        register_depth_shaders();
    }
    public static RenderLayerShader get(String name) {
        return shaders.get(name);
    }
    public static ShaderProgram p() {
        return testShader.getProgram();
    }
    public static void register_depth_shaders() {
        ClientTickEvents.END_CLIENT_TICK.register(CollapseOrbDepthFX.INSTANCE);
        ShaderEffectRenderCallback.EVENT.register(CollapseOrbDepthFX.INSTANCE);
        PostWorldRenderCallbackV2.EVENT.register(CollapseOrbDepthFX.INSTANCE);
        PostWorldRenderCallbackV2.EVENT.register(BelowWorldShader.INSTANCE);
        ClientTickEvents.END_CLIENT_TICK.register(BelowWorldShader.INSTANCE);



        VanishingBlockShader.INSTANCE.register_core();
        LimboSkyShader.INSTANCE.register_core();
        VanishingBlockShader.INSTANCE.register_auto_render(VanishingBlockShader.INSTANCE);
        LimboSkyShader.INSTANCE.register_auto_render(LimboSkyShader.INSTANCE);



    }

}
