package org.shadowmaster435.limbo.init;

import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.shadowmaster435.limbo.shader.CollapseOrbDepthFX;
import org.shadowmaster435.limbo.shader.RenderLayerShader;

import java.util.HashMap;

public class ModShaders {

    public static HashMap<String, RenderLayerShader> shaders = new HashMap<>();


    public static void register() {


        register_depth_shaders();
    }
    public static boolean areShadersDisabled() {
        return false;
    }
    public static RenderLayerShader get(String name) {
        return shaders.get(name);
    }


    public static void register_depth_shaders() {
        ClientTickEvents.END_CLIENT_TICK.register(CollapseOrbDepthFX.INSTANCE);
        ShaderEffectRenderCallback.EVENT.register(CollapseOrbDepthFX.INSTANCE);
        PostWorldRenderCallbackV2.EVENT.register(CollapseOrbDepthFX.INSTANCE);
//        ClientTickEvents.END_CLIENT_TICK.register(VanishingBlockDepthFX.INSTANCE);
//        ShaderEffectRenderCallback.EVENT.register(VanishingBlockDepthFX.INSTANCE);
//        PostWorldRenderCallbackV2.EVENT.register(VanishingBlockDepthFX.INSTANCE);
/*        var test = new CollapseOrbShader();
        test.register();*/
        //shaders.put("collapse_orb", test);
//        var vanishing_block = new VanishingBlockShader();
//        vanishing_block.register();
//        shaders.put("vanishing_block", vanishing_block);
//
//
//        ModBlocks.shader_render_layers();
    }

}
