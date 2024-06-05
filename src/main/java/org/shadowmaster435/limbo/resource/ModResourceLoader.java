package org.shadowmaster435.limbo.resource;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ModResourceLoader {

    public static HashMap<String, NativeImageBackedTexture> shader_textures = new HashMap<>();
    public static void register() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(Limbo.id, "shader_texture");
            }
            @Override
            public void reload(ResourceManager manager) {
                for (Identifier id : manager.findResources("textures/shader", path -> path.getPath().endsWith(".png")).keySet().stream().toList()) {
                    try (InputStream stream = manager.getResource(id).orElseThrow().getInputStream()) {
                        register_shader_textures(stream, id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        });
    }


    public static void register_shader_textures(InputStream stream, Identifier id) throws IOException {
        var img = new NativeImageBackedTexture(NativeImage.read(stream));
        shader_textures.put(id.toString(), img);
    }
}
