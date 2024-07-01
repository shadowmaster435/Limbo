package org.shadowmaster435.limbo.util.render.texture;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Collection;

public class DataBufferTexture extends ByteBufferTexture {

    public final ByteBuffer buffer;
    private boolean opened = false;

    public DataBufferTexture(int width, int height) {
        super(width, height);
        this.buffer = BufferUtils.createByteBuffer((width * height) * 4);
    }

    private void put_type(DataType type) {
        byte r = (byte) ((type.type>>24)&0xFF);
        byte g = (byte) ((type.length>>16)&0xFF);
        buffer.put(new byte[]{r, g, 0, 0});
    }

    private void put_array_type(DataType type, Collection<?> array) {
        byte r = (byte) ((type.type>>24)&0xFF);
        byte g = (byte) ((array.size()>>16)&0xFF);
        byte b = (byte) ((array.size()>>8)&0xFF);
        byte a = (byte) ((array.size())&0xFF);
        buffer.put(new byte[]{r,g,b,a});
    }

    public void put_int(int value) {
        if (!opened) {
            throw new RuntimeException("Buffer Not Opened");
        }
        Vector4i argb = from_argb_int(value);
        put_type(DataType.int_type);
        buffer.put(new byte[]{(byte) argb.w, (byte) argb.x, (byte) argb.y, (byte) argb.z});
    }

    public int rgba_to_argb(int value) {
        byte r = (byte) ((value>>24)&0xFF);
        byte g = (byte) ((value>>16)&0xFF);
        byte b = (byte) ((value>>8)&0xFF);
        byte a = (byte) ((value)&0xFF);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public int argb_to_rgba(int value) {
        byte a = (byte) ((value>>24)&0xFF);
        byte r = (byte) ((value>>16)&0xFF);
        byte g = (byte) ((value>>8)&0xFF);
        byte b = (byte) ((value)&0xFF);
        return (r << 24) | (g << 16) | (b << 8) | a;
    }

    public Vector4i from_argb_int(int value) {
        byte a = (byte) ((value>>24)&0xFF);
        byte r = (byte) ((value>>16)&0xFF);
        byte g = (byte) ((value>>8)&0xFF);
        byte b = (byte) ((value)&0xFF);
        return new Vector4i(a, r, g, b);
    }

    public int to_rgba_int(int r, int g, int b, int a) {
        return (r << 24) | (g << 16) | (b << 8) | a;
    }

    public int to_argb_int(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }




    public void open() {
        buffer.clear();
        opened = true;
    }

    public void close() {
        buffer.compact();
        write(buffer);
        opened = false;
    }
    public enum DataType {
        int_type(1, 1),
        float_type(2, 2),
        ivec3_type(3, 3),
        vec3_type(4, 6),
        ivec2_type(5, 2),
        vec2_type(6, 4),
        int_array_type(7, 0),
        float_array_type(8, 0),
        ivec3_array_type(9, 0),
        vec3_array_type(10, 0),
        ivec2_array_type(11, 0),
        vec2_array_type(12, 0);

        // 0 is a variable length
        final int type;
        final int length;

        DataType(int type, int length) {
            this.type = type;
            this.length = length;
        }

    }

//    public static void pos_to_rgba(BlockPos pos, int range) {
//        float actual_x = pos.getX() + (range / 2f);
//        float actual_y = pos.getY() + 64;
//
//        float actual_z = pos.getZ() + (range / 2f);
//
//        byte x_wrap_count = (byte) ((int) Math.floor(actual_x) / 255f);
//        byte y_wrap_count = (byte) ((int) Math.floor(actual_y) / 255f);
//
//        byte z_wrap_count = (byte) ((int) Math.floor(actual_z) / 255f);
//        byte r = (byte) ((actual_x) % 128);
//        byte g = (byte) ((actual_y ) % 128);
//        byte b = (byte) ((actual_z) % 128);
//        StringBuilder a_string = new StringBuilder();
//        StringBuilder x_wrap_string = new StringBuilder(Integer.toBinaryString(x_wrap_count % 8));
//        StringBuilder y_wrap_string = new StringBuilder(Integer.toBinaryString(y_wrap_count % 4));
//        StringBuilder z_wrap_string = new StringBuilder(Integer.toBinaryString(x_wrap_count % 8));
//        while (x_wrap_string.length() < 3) {
//            x_wrap_string.insert(0, "0");
//
//        }
//        while (y_wrap_string.length() < 2) {
//            y_wrap_string.insert(0, "0");
//        }
//        while (z_wrap_string.length() < 3) {
//
//            z_wrap_string.insert(0, "0");
//        }
//        StringBuilder str = new StringBuilder();
//        str.append(z_wrap_string).append(y_wrap_string).append(x_wrap_string);
//        byte[] arr = {r, g, b, (byte) Integer.parseInt(str.toString(), 2)};
//
//        buffer.put(arr);
//    }

}
