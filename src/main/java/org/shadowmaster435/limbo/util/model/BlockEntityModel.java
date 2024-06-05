package org.shadowmaster435.limbo.util.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.shadowmaster435.limbo.util.block_entity.AnimatedBlockEntity;

import java.util.Objects;
import java.util.function.Function;

public abstract class BlockEntityModel<T extends AnimatedBlockEntity> extends Model {
    public boolean child;
    private Identifier texture;

    protected BlockEntityModel(@Nullable Identifier texture) {
        this(RenderLayer::getEntityCutoutNoCull, texture);
    }

    protected BlockEntityModel(Function<Identifier, RenderLayer> function, @Nullable Identifier texture) {
        super(function);
        this.texture = Objects.requireNonNullElseGet(texture, () -> new Identifier("limbo:textures/block/missing.png"));
    }

    public <A extends AnimatedBlockEntity> void animateModel(A entity, float tickDelta) {

    }

    public Identifier get_texture() {
        return texture;
    }

    public void set_texture(Identifier texture) {
        this.texture = texture;
    }

    public void copyStateTo(BlockEntityModel<T> copy) {
        copy.child = this.child;
    }
}
