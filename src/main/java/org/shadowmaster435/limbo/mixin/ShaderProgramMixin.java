package org.shadowmaster435.limbo.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.gl.EffectShaderStage;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PathUtil;
import net.minecraft.util.StringHelper;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.resource.ModResourceLoader;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.MatchResult;
@Debug(export = true) // Enables exporting for the targets of this mixin
@Mixin(JsonEffectShaderProgram.class)
public class ShaderProgramMixin {

//    @Inject(
//            method = "loadEffect",
//            at = @At(
//                    value = "INVOKE_ASSIGN",
//                    target = "Lnet/minecraft/resource/Resource;getInputStream()Ljava/io/InputStream;",
//                    shift = At.Shift.AFTER
//            ),
//            locals = LocalCapture.CAPTURE_FAILHARD
//    )
//
//
//    private static void load_include(ResourceFactory resourceFactory, ShaderStage.Type type, String name, CallbackInfoReturnable<EffectShaderStage> cir, ShaderStage shaderStage, Identifier identifier, Resource resource, @Local LocalRef<InputStream> inputStream) {
//
//        try {
//            var str = new String(resource.getInputStream().readAllBytes());
//
//            StringBuilder result_string = new StringBuilder();
//            if (str.contains("//#import ")) {
//
//
//                Scanner reader = new Scanner(str);
//                while (reader.hasNextLine()) {
//                    var line = reader.nextLine();
//                    if (line.contains("//#import ")) {
//                        var res = resourceFactory.getResourceOrThrow(new Identifier(Limbo.id +":shaders/include/" + line.replace("//#import ", "") +".glsl"));
//                        var include_reader = new Scanner(new String(res.getInputStream().readAllBytes()));
//                        while (include_reader.hasNextLine()) {
//                            var include_line = include_reader.nextLine();
//                            result_string.append(include_line).append("\n");
//                        }
//                    } else {
//                        result_string.append(line).append("\n");
//                    }
//                }
//                InputStream output = new ByteArrayInputStream(result_string.toString().getBytes());
//                inputStream.set(output);
//                System.out.println(new String(inputStream.get().readAllBytes()));
//            }
//
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

}
