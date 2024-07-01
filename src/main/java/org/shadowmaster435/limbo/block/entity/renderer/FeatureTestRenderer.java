package org.shadowmaster435.limbo.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.block.entity.FeatureTestEntity;
import org.shadowmaster435.limbo.block.entity.GlitchBlockEntity;
import org.shadowmaster435.limbo.init.ModShaders;
import org.shadowmaster435.limbo.util.MiscUtil;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuad;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuadStrip;

public class FeatureTestRenderer implements BlockEntityRenderer<FeatureTestEntity> {

    public FeatureTestRenderer(BlockEntityRendererFactory.Context ctx) {
    }
    public static final AbstractTexture t = MinecraftClient.getInstance().getTextureManager().getTexture(new Identifier(Limbo.id, "textures/block/dim_ore.png"));

    public final TexturedQuadStrip s = TexturedQuadStrip.create_from_quad(16, TexturedQuad.create_horizontal(new Vector2f(1,1), new Vector2f(0.0f, 1f),new Vector2f(0f, 1f), 1));
    public final Identifier tex = new Identifier(Limbo.id, "textures/block/dim_ore.png");

    @Override
    public void render(FeatureTestEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        s.render(matrices, "", light, overlay, ModShaders::p, null);

        if (entity.getWorld() != null) {
            ModShaders.testShader.findUniform1f("time").set((entity.getWorld().getTime() + tickDelta) / 20f);
            ModShaders.testShader.findUniform3f("Center").set(MinecraftClient.getInstance().player.getLerpedPos(tickDelta).toVector3f());

        }

        var tess = Tessellator.getInstance();
        var buff = tess.getBuffer();
        //  buff.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//       // ModShaders.testShader.findUniformMat4("ModelMat").set(matrices.peek().getPositionMatrix());
//        RenderSystem.disableCull();
//        RenderSystem.enableDepthTest();
//        var m = matrices.peek().getPositionMatrix();
//        var lz = entity.loadZone;
//        if (lz == null) {
//            tess.draw();
//            return;
//        }
//        buff.vertex(m, (float) lz.minX - entity.getPos().getX(), (float) lz.minY - entity.getPos().getY(), (float) lz.minZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.maxX - entity.getPos().getX(), (float) lz.minY - entity.getPos().getY(), (float) lz.minZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.maxX - entity.getPos().getX(), (float) lz.minY - entity.getPos().getY(), (float) lz.maxZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.minX - entity.getPos().getX(), (float) lz.minY - entity.getPos().getY(), (float) lz.maxZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        tess.draw();
//        buff.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//
//        buff.vertex(m, (float) lz.minX - entity.getPos().getX(), (float) lz.maxY - entity.getPos().getY(), (float) lz.minZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.maxX - entity.getPos().getX(), (float) lz.maxY - entity.getPos().getY(), (float) lz.minZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.maxX - entity.getPos().getX(), (float) lz.maxY - entity.getPos().getY(), (float) lz.maxZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        buff.vertex(m, (float) lz.minX - entity.getPos().getX(), (float) lz.maxY - entity.getPos().getY(), (float) lz.maxZ - entity.getPos().getZ()).color(1f,1f,1f,1f).next();
//        tess.draw();
    }
}
