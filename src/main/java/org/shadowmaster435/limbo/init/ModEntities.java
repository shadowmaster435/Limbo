package org.shadowmaster435.limbo.init;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.shadowmaster435.limbo.Limbo;
import org.shadowmaster435.limbo.entity.MonolithEntity;
import org.shadowmaster435.limbo.entity.model.MonolithModel;
import org.shadowmaster435.limbo.entity.renderer.MonolithEntityRenderer;

public class ModEntities {
    public static final EntityModelLayer MODEL_MONOLITH_LAYER = new EntityModelLayer(new Identifier("limbo", "monolith"), "main");

    public static final EntityType<MonolithEntity> MONOLITH = Registry.register(Registries.ENTITY_TYPE, new Identifier(Limbo.id, "monolith"), EntityType.Builder.create(MonolithEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build());


    public static void register() {
        FabricDefaultAttributeRegistry.register(MONOLITH, MonolithEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1000).add(EntityAttributes.GENERIC_SCALE, 1.5).build());

    }



    public static void client() {
        EntityRendererRegistry.register(MONOLITH, MonolithEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_MONOLITH_LAYER, MonolithModel::getTexturedModelData);

    }

}
