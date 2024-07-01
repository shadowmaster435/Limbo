package org.shadowmaster435.limbo.screen.elements;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class SlotElement extends NinePatchTexture {

    public String slot_name;
    public Slot slot;
    public final Inventory inventory;

    public SlotElement(Inventory inventory, int x, int y, int w, int h, int edge_thickness) {
        super(x, y, w, h, edge_thickness, new Identifier("limbo:slot"));
        this.slot = new Slot(inventory, inventory.size(), x, y);
        this.slot_name = String.valueOf(inventory.size());
        this.inventory = inventory;
    }

    public SlotElement(Inventory inventory, int x, int y, int w, int h, int edge_thickness, Identifier texture) {
        super(x, y, w, h, edge_thickness, texture);
        this.slot_name = String.valueOf(inventory.size());
        this.inventory = inventory;
    }

    public SlotElement(Inventory inventory, String slot_name, int x, int y, int w, int h, int edge_thickness) {
        super(x, y, w, h, edge_thickness, new Identifier("limbo:slot"));
        this.slot = new Slot(inventory, inventory.size(), x, y);
        this.slot_name = slot_name;
        this.inventory = inventory;
    }

    public SlotElement(Inventory inventory, String slot_name, int x, int y, int w, int h, int edge_thickness, Identifier texture) {
        super(x, y, w, h, edge_thickness, texture);
        this.slot_name = slot_name;
        this.inventory = inventory;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
