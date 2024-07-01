package org.shadowmaster435.limbo.util.render.texture;

import net.minecraft.client.gl.Framebuffer;

public class ReadableFramebuffer extends Framebuffer {


    public ReadableFramebuffer(boolean useDepth) {
        super(useDepth);
    }
}
