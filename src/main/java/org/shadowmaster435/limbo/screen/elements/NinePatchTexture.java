package org.shadowmaster435.limbo.screen.elements;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Scaling;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

public class NinePatchTexture extends GuiElement {

    public int edge_thickness;
    public final Sprite sprite;

    public NinePatchTexture(int x, int y, int w, int h, int edge_thickness, Identifier texture) {
        super(x, y);
        getRect().setBounds(x, y, w, h);
        this.edge_thickness = edge_thickness;
        this.sprite = MinecraftClient.getInstance().getGuiAtlasManager().getSprite(texture);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        var slice = new Scaling.NineSlice(getWidth(), getHeight(), new Scaling.NineSlice.Border(edge_thickness, edge_thickness, edge_thickness, edge_thickness));
        context.drawSprite(sprite, slice, getX(), getY(), layer, getWidth(), getHeight());
    }
}
