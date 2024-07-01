package org.shadowmaster435.limbo.block.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.shadowmaster435.limbo.block.entity.LaserEmitterEntity;
import org.shadowmaster435.limbo.block.entity.LaserRedirectorEntity;
import org.shadowmaster435.limbo.util.render.geo.TexturedQuadStripPipe;

public class LaserEmitterRenderer implements BlockEntityRenderer<LaserEmitterEntity> {


    public TexturedQuadStripPipe laser = TexturedQuadStripPipe.create(4, new Vector3f(0.1875f, 1, 0.1875f), new Vector2f(0, 0.046875f), new Vector2f(0, 1));
    public LaserEmitterRenderer(BlockEntityRendererFactory.Context ctx) {

    }
    @Override
    public void render(LaserEmitterEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        var dir = entity.getCachedState().get(Properties.FACING);
        switch (dir) {

            case NORTH -> matrices.peek().getPositionMatrix().translate(0.5f + (0.1875f / 2), 0.5f + (0.1875f / 2), 0.5f);
            case EAST -> matrices.peek().getPositionMatrix().translate(0.5f, 0.5f + (0.1875f / 2), 0.5f + (0.1875f / 2));
            case SOUTH ->  matrices.peek().getPositionMatrix().translate(0.5f - (0.1875f / 2), 0.5f + (0.1875f / 2), 0.5f);
            case WEST -> matrices.peek().getPositionMatrix().translate(0.5f, 0.5f + (0.1875f / 2), 0.5f - (0.1875f / 2));
            case UP -> matrices.peek().getPositionMatrix().translate(0.5f - (0.1875f / 2), 0.5f, 0.5f - (0.1875f / 2));
            case DOWN -> matrices.peek().getPositionMatrix().translate(0.5f - (0.1875f / 2), 0.5f , 0.5f + (0.1875f / 2));

        }


        matrices.multiply(dir.getRotationQuaternion());

        for (int i = 0; i < entity.distance - 1; ++i) {
            laser.render(matrices, new Identifier("limbo:textures/block/illuminator_laser.png"), light,overlay,null);
            matrices.translate(0, 1,0);

        }
//        var rot = new Quaternionf();
//        matrices.peek().getPositionMatrix().getNormalizedRotation(rot);
//        var r = (entity.getWorld().getTime() / 40f);
//        var n = Math.toRadians(r - 90);
//        var p = Math.toRadians(r + 90);
//        var v1 = new Vector3f(0,0,0);
//        var v2 = new Vector3f(0,1,0);
//        rot.rotateLocalX(r);
//        rot.rotateLocalY(r);
//
//        v2.rotate(rot);
//        var tess = Tessellator.getInstance();
//        var buff = tess.getBuffer();
//        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
//        buff.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
//        buff.vertex(matrices.peek().getPositionMatrix(), v1.x, v1.y, v1.z).color(1f,0f,0f,1f);
//        buff.vertex(matrices.peek().getPositionMatrix(), v2.x, v2.y, v2.z).color(1f,0f,0f,1f);
//        BufferRenderer.drawWithGlobalProgram(buff.end());
    }



}
