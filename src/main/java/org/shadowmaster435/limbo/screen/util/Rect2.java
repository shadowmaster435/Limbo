package org.shadowmaster435.limbo.screen.util;

import org.joml.Vector2i;
import org.joml.Vector4i;

import java.awt.*;

public class Rect2 extends Rectangle {

    //region Initializers
    public Rect2() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rect2(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public Rect2(Vector2i pos, Vector2i size) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = size.x;
        this.height = size.y;
    }
    public Rect2(Vector2i pos, int w, int h) {
        this.x = pos.x;
        this.y = pos.y;
        this.width = w;
        this.height = h;
    }
    public Rect2(int x, int y, Vector2i size) {
        this.x = x;
        this.y = y;
        this.width = size.x;
        this.height = size.y;
    }
    public Rect2(Vector4i bounds) {
        this.x = bounds.x;
        this.y = bounds.y;
        this.width = bounds.z;
        this.height = bounds.w;
    }
    //endregion


}
