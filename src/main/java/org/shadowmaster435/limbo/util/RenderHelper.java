package org.shadowmaster435.limbo.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class RenderHelper {

    public static void face_matrix_towards(Vec3d center, Vec3d to_face, MatrixStack matrices) {
        double dX = center.getX() - to_face.getX();
        double dY = center.getY() - to_face.getY();
        double dZ = center.getZ() - to_face.getZ();
        float yaw = (float) -Math.toDegrees(Math.atan2(dZ, dX));
        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI);
        matrices.multiply(new Quaternionf().rotationYXZ(yaw * (float) (Math.PI / 180.0),(float) Math.PI, pitch * (float) (Math.PI / 180.0)));

    }

    public static void render_sky_eye(MatrixStack matrices) {
        var amp = 0.125;
        var bottom_amp = 0.125;
        var top_amp = MathHelper.lerp(MiscUtil.eye_anger, -0.125, 0.125);
        var tess = Tessellator.getInstance();
        var buff = tess.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();
        buff.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        matrices.scale(512, 1, 512); // beeg

        matrices.translate(-1,500,-1);

        matrices.multiply(RotationAxis.NEGATIVE_X.rotation((float) Math.toRadians(270)));
        for (int i = 0; i < 33; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * Math.PI * 2f;
            var width_delta = (float) ((1f - ((Math.cos(pi_delta) * 0.5) + 0.5)) * 0.02f) + 0.02f;
            var sine = (Math.cos(pi_delta) * bottom_amp) + (1.0f - bottom_amp);
            var pos = new Vector3f(delta * 2f, (float) sine, 0);
            buff.vertex(matrices.peek().getPositionMatrix(),pos.x,pos.y + width_delta,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            buff.vertex(matrices.peek().getPositionMatrix(),pos.x,pos.y,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();

        }
        BufferRenderer.drawWithGlobalProgram(buff.end());
        buff.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

        var v_ofs = 2f;
        for (int i = 0; i < 33; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * Math.PI * 2f;
            var width_delta = (float) ((1f - ((Math.cos(pi_delta) * 0.5) + 0.5)) * 0.02f) + 0.02f;
            var sine = (Math.cos(pi_delta) * top_amp) + (1.0f - top_amp);
            var pos = new Vector3f(delta * 2f, (float) -sine, 0);

            buff.vertex(matrices.peek().getPositionMatrix(),pos.x,(pos.y + width_delta) + v_ofs,pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            buff.vertex(matrices.peek().getPositionMatrix(), pos.x, pos.y + v_ofs, pos.z).color(1f,0f,0f,1f).normal(0, 0, 0).next();

        }
        BufferRenderer.drawWithGlobalProgram(buff.end());
        buff.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        var pupul_offset = (0.0625f / 2f) + ((float) Math.max(top_amp, 0));
        for (int i = 0; i < 34; ++i) {

            var delta = i / 32f;
            var pi_delta = delta * (Math.PI * 2);
            var sine = (Math.sin(pi_delta) * (Math.max(top_amp, 0))) + (1.0f - amp);
            var cosine = (Math.cos(pi_delta) * amp) + (1.0f - amp);

            var pos = new Vector3f((float) cosine, ((float) -sine), 0);

            buff.vertex(matrices.peek().getPositionMatrix(),pos.x + 0.125f, (float) (((((pos.y) + v_ofs) - 0.125f) + pupul_offset) - (top_amp)),pos.z).color(1f,0f,0f,1f).normal(0,0,0).next();
            buff.vertex(matrices.peek().getPositionMatrix(),1f, (float) ((1f + pupul_offset) - top_amp),0).color(1f,0f,0f,1f).normal(0,0,0).next();
        }
        BufferRenderer.drawWithGlobalProgram(buff.end());
        RenderSystem.disableDepthTest();

    }

    public static void textured_quad(Vector3f pos, Vector2f size, SpriteIdentifier spr, int light, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
   }

    public static void textured_quad(Vector3f pos, Vector2f size, Identifier spr, int light, Matrix4f mat) {
        var tess = Tessellator.getInstance();
        var consumer = tess.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, spr);
        var k = 5f;
        consumer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        consumer.vertex(mat, pos.x, 0, pos.z).color(1f,1f,1f,1f).texture(1, 1).next();
        consumer.vertex(mat, pos.x + size.x, 0, pos.z).color(1f,1f,1f,1f).texture(1, 0).next();
        consumer.vertex(mat, pos.x + size.x, 0, pos.z + size.y).color(1f,1f,1f,1f).texture(0, 0).next();
        consumer.vertex(mat, pos.x,0, pos.z + size.y).color(1f,1f,1f,1f).texture(0, 1).next();
        BufferRenderer.drawWithGlobalProgram(consumer.end());
    }
}
