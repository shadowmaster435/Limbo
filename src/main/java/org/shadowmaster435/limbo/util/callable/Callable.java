package org.shadowmaster435.limbo.util.callable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JavaOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.entity.SignText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public record Callable(Object caller, String method_name, Class<?>... args) {
    public <T> T call(Object... inputs) {
        T result = null;
        try {
            var method = caller.getClass().getMethod(method_name,args);
            result = (T) ((args.length > 0) ? method.invoke(caller, inputs) : method.invoke(caller));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public <T> T call_static(Object... inputs) {
        T result = null;
        try {
            Class<?> caller = (Class<?>) this.caller;
            var method = caller.getMethod(method_name,args);
            result = (T) ((args.length > 0) ? method.invoke(caller, inputs) : method.invoke(caller));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public BoundCallable bind(Object... inputs) {
        return new BoundCallable(caller, method_name, args, inputs);
    }

    //untested
    public <T> Codec<T> to_codec() {
        return (Codec<T>) Codecs.fromOps(JavaOps.INSTANCE).validate((obj) -> codec_data_result(this));
    }

    private static <T> DataResult<Object> codec_data_result(@Nullable Callable callable) {
        return callable == null ? DataResult.error(() -> "Codec object is null") : DataResult.success(callable.call());
    }

}
