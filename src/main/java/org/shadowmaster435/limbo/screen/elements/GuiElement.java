package org.shadowmaster435.limbo.screen.elements;

import com.mojang.brigadier.Message;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.screen.util.Rect2;
import org.shadowmaster435.limbo.util.callable.Callable;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.function.Consumer;

public class GuiElement implements Drawable, Element, Widget {

    public int layer = 0;

    private double mouseX = 0;
    private double mouseY = 0;
    public int custom_min_size_x = 0;
    public int custom_min_size_y = 0;

    public enum ActionType {

        click(0, 2, ""),
        mouse_move(1, 2, "mouseMoved"),
        mouse_exit(2, 3, "on_mouse_exit"),
        mouse_enter(3, 3, "on_mouse_enter"),
        mouse_drag(4, 5, "mouseDragged"),
        mouse_scroll(5, 2, "mouseScrolled"),
        key_pressed(6, 3, "keyPressed"),
        key_released(7, 3, "keyReleased"),
        char_typed(8, 2, "charTyped"),
        unclick(9, 2, "mouseReleased"),
        rendered(10, 4, "render");
        int type;
        int arg_count;
        String method;

        ActionType(int type, int arg_count, String method) {
            this.type = type;
            this.arg_count = arg_count;
            this.method = method;

        }

    }

    public record TypedCall(Callable callable, ActionType type) {

    }

    private HashMap<String, TypedCall> action_map = new HashMap<>();

    private boolean focused = false;

    private Rect2 rect = new Rect2();

    public GuiElement(int x, int y) {
        this.rect.x = x;
        this.rect.y = y;
        this.rect.width = 0;
        this.rect.height = 0;
    }

    public GuiElement(Vector2i pos) {
        this.rect.x = pos.x;
        this.rect.y = pos.y;
        this.rect.width = 0;
        this.rect.height = 0;
    }

    public GuiElement(int x, int y, int w, int h) {
        this.rect.x = x;
        this.rect.y = y;
        this.rect.width = w;
        this.rect.height = h;
    }

    public GuiElement(Vector4i bounds) {
        this.rect.x = bounds.x;
        this.rect.y = bounds.y;
        this.rect.width = bounds.z;
        this.rect.height = bounds.w;
    }

    public void try_action(String action_name, Object... inputs) {
        if (action_map.containsKey(action_name)) {
            var action = action_map.get(action_name);
            if (action.type.arg_count == inputs.length) {
                action_map.get(action_name).callable.call(inputs);
            } else {
                System.out.println("Action '" + action_name + "' has '" + action.type.arg_count + "' inputs, but was called with '" + inputs.length + "' skipping...");
            }
        }
    }
    public <E extends GuiElement> void bind_action(ActionType type, E element, String action_name, String method_name, Class<?> args) {
        action_map.put(action_name, new TypedCall(new Callable(element, method_name, args), type));
    }

    public void bind_action(ActionType type, String action_name, String method_name, Class<?> args) {
        action_map.put(action_name, new TypedCall(new Callable(this, method_name, args), type));
    }

    public void add() {

    }

    @Override
    public void setX(int x) {
        rect.x = x;
    }

    @Override
    public void setY(int y) {
        rect.y = y;
    }

    public void setWidth(int w) {
        rect.width = w;
    }

    public void setHeight(int h) {
        rect.height = h;
    }

    public Rect2 getRect() {
        return this.rect;
    }

    @Override
    public int getX() {
        return rect.x;
    }

    @Override
    public int getY() {
        return rect.y;
    }

    @Override
    public int getWidth() {
        return rect.width;
    }

    @Override
    public int getHeight() {
        return rect.height;
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {

    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.rendered.type) {
                try_action(typedCall.type.method, context, mouseX, mouseY, delta);
            }
        });
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.mouse_move.type) {
                try_action(typedCall.type.method, mouseX, mouseY);
            }
        });
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        Element.super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.click.type) {
                try_action(typedCall.type.method, mouseX, mouseY, button);
            }
        });
        return Element.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.unclick.type) {
                try_action(typedCall.type.method, mouseX, mouseY, button);
            }
        });
        return Element.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.mouse_drag.type) {
                try_action(typedCall.type.method, mouseX, mouseY, button, deltaX, deltaY);
            }
        });
        return Element.super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.mouse_scroll.type) {
                try_action(typedCall.type.method, mouseX, mouseY, horizontalAmount, verticalAmount);
            }
        });
        return Element.super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.key_pressed.type) {
                try_action(typedCall.type.method, keyCode, scanCode, modifiers);
            }
        });
        return Element.super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.key_released.type) {
                try_action(typedCall.type.method, keyCode, scanCode, modifiers);
            }
        });
        return Element.super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        action_map.forEach((s, typedCall) -> {
            if (typedCall.type.type == ActionType.char_typed.type) {
                try_action(typedCall.type.method, chr, modifiers);
            }
        });
        return Element.super.charTyped(chr, modifiers);
    }

    @Nullable
    @Override
    public GuiNavigationPath getNavigationPath(GuiNavigation navigation) {
        return Element.super.getNavigationPath(navigation);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return rect.contains(mouseX, mouseY);
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Nullable
    @Override
    public GuiNavigationPath getFocusedPath() {
        return Element.super.getFocusedPath();
    }

    @Override
    public ScreenRect getNavigationFocus() {
        return Element.super.getNavigationFocus();
    }
}
