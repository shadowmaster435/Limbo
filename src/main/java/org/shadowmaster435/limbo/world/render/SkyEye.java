package org.shadowmaster435.limbo.world.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.util.MiscUtil;

public class SkyEye {


    public static void render_sky_eye(MatrixStack matrices) {
        var amp = 0.125;
        var bottom_amp = 0.125;
        var top_amp = MathHelper.lerp(MiscUtil.eye_anger, -0.125, 0.125);
        var tess = Tessellator.getInstance();
        var buff = tess.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.enableDepthTest();
        buff.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        matrices.push();
        matrices.scale(256, 1,256); // beeg

        matrices.translate(-1,250,-1);

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
        matrices.pop();
    }

}
