package org.shadowmaster435.limbo.block.entity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.block.entity.GlitchBlockEntity;
import org.shadowmaster435.limbo.resource.ModResourceLoader;
import org.shadowmaster435.limbo.shader.CollapseOrbDepthFX;
import org.shadowmaster435.limbo.util.*;

public class GlitchBlockRenderer implements BlockEntityRenderer<GlitchBlockEntity> {


    public BlockEntityRenderDispatcher dispatcher;
    public GlitchBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        this.dispatcher = ctx.getRenderDispatcher();

    }
    public static final SpriteIdentifier spr = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye"));
    public static final SpriteIdentifier[] eyes = {
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye_anger_0")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye_anger_1")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye_anger_2")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye_anger_3")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("limbo:block/eye_anger_4")),
    };

    @Override
    public void render(GlitchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var amp = 0.125;
        var bottom_amp = 0.125;
        var top_amp = (Math.sin(((entity.getWorld()).getTime() + tickDelta) * 0.15f) * 0.5 ) * 0.25;

        var vert_dist = 0.0625f;
        var tess = Tessellator.getInstance();
        var buff = vertexConsumers.getBuffer(RenderLayer.getDebugFilledBox());
        Vector3f prev_point;
        for (int i = 0; i < 33; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * Math.PI * 2f;
            var width_delta = (float) ((1f - ((Math.cos(pi_delta) * 0.5) + 0.5)) * 0.02f) + 0.02f;
            var sine = (Math.cos(pi_delta) * bottom_amp) + (1.0f - bottom_amp);
            var pos = new Vector3f(delta * 2f, (float) sine, 0);
            buff.vertex(matrices.peek().getPositionMatrix(),pos.x,pos.y + width_delta,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            if (i == 32) {
                buff.vertex(matrices.peek().getPositionMatrix(),pos.x,pos.y,pos.z).color(1f,0f,0f,1f).normal(0,0,0);

            } else {
                buff.vertex(matrices.peek().getPositionMatrix(),pos.x,pos.y,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();

            }
        }
        var v_ofs = 2f;
        for (int i = 0; i < 33; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * Math.PI * 2f;
            var width_delta = (float) ((1f - ((Math.cos(pi_delta) * 0.5) + 0.5)) * 0.02f) + 0.02f;
            var sine = (Math.cos(pi_delta) * top_amp) + (1.0f - top_amp);
            var pos = new Vector3f(delta * 2f, (float) -sine, 0);

            buff.vertex(matrices.peek().getPositionMatrix(),pos.x,(pos.y + width_delta) + v_ofs,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            if (i == 32) {
                buff.vertex(matrices.peek().getPositionMatrix(), pos.x, pos.y + v_ofs, pos.z).color(1f,0f,0f,1f).normal(0, 0, 0);

            } else {
                buff.vertex(matrices.peek().getPositionMatrix(), pos.x, pos.y + v_ofs, pos.z).color(1f,0f,0f,1f).normal(0, 0, 0).next();
            }
        }
        var pupul_offset = (0.0625f / 2f) + ((float) Math.max(top_amp, 0));
        for (int i = 0; i < 34; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * (Math.PI * 2);
            var sine = (Math.sin(pi_delta) * top_amp) + (1.0f - amp);
            var cosine = (Math.cos(pi_delta) * amp) + (1.0f - amp);

            var pos = new Vector3f((float) cosine, ((float) -sine), 0);

            buff.vertex(matrices.peek().getPositionMatrix(),pos.x + 0.125f, (float) (((((pos.y) + v_ofs) - 0.125f) + pupul_offset) - (top_amp)),pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            buff.vertex(matrices.peek().getPositionMatrix(),1f, (float) ((1f + pupul_offset) - top_amp),0).color(1f,0f,0f,1f).normal(0,0,0).next();
        }

    }
    public void render_eye(GlitchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        var player = MinecraftClient.getInstance().player;
        var cpos = entity.getPos().toCenterPos();
        var ppos = MinecraftClient.getInstance().player.getLerpedPos(tickDelta).add(0, MinecraftClient.getInstance().player.getStandingEyeHeight(), 0);
        var pitch = player.getPitch(tickDelta);
        var yaw = player.getYaw(tickDelta);
        var rot_vec = ModMathHelper.get_rotation_vector(yaw - 90, pitch  );
        var subbed_pos = ppos.subtract(cpos).toVector3f().add(rot_vec.toVector3f());

        var eased = 1f - Easing.SINE_IN_OUT.ease(MathHelper.clamp(entity.eye_delta, 0f, 60f), 0f, 1f, 60f);
        var lerped = ppos.lerp(dispatcher.camera.getPos(), eased);
        // System.out.println(rot_vec);
        RenderSystem.disableDepthTest();
        matrices.push();
        var max_ofs = 0.0625f / 2;
        var randofs = new Vector3f(MathHelper.nextFloat(Random.createLocal(), -max_ofs, max_ofs),MathHelper.nextFloat(Random.createLocal(), -max_ofs, max_ofs),MathHelper.nextFloat(Random.createLocal(), -max_ofs, max_ofs)).mul(eased);
        matrices.translate(randofs.x,randofs.y,randofs.z);
        matrices.translate(0.5,0.5,0.5);
        RenderHelper.face_matrix_towards(cpos, lerped, matrices);
        matrices.scale(1 -entity.orb_scale,1 -entity.orb_scale,1 -entity.orb_scale);
        var eye_tex_delta = Easing.SINE_IN_OUT.ease(MathHelper.clamp(entity.eye_delta, 0f, 60f), 0f, 1f, 60f);
        var acutal_spr = (entity.eye_saw_you) ? eyes[Math.min((int) Math.floor(eased * 5), 4)] : spr;
        RenderHelper.textured_quad(new Vector3f(-0.5f, 0, -0.5f), new Vector2f(1,1), acutal_spr, 162000, matrices, vertexConsumers);
        matrices.pop();
        CollapseOrbDepthFX.INSTANCE.static_influence.set(entity.static_influence);
        CollapseOrbDepthFX.INSTANCE.floor_tex.set(ModResourceLoader.shader_textures.get("limbo:textures/shader/portal_floor.png"));
        CollapseOrbDepthFX.INSTANCE.uniformCenter.set(entity.getPos().toCenterPos().toVector3f());
        CollapseOrbDepthFX.INSTANCE.orb_scale.set(entity.orb_scale);
        CollapseOrbDepthFX.INSTANCE.eye_delta.set(eased);

    }

}