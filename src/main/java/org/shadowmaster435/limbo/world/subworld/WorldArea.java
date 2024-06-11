package org.shadowmaster435.limbo.world.subworld;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class WorldArea {

    public NbtCompound unloaded_entities;
    public BlockArea area;
    private final BlockPos size;

    public WorldArea(BlockPos min_pos, BlockPos max_pos, World world, boolean autoswap) {
        var p = min_pos.add(max_pos);
        size = new BlockPos(Math.absExact(p.getX()), Math.absExact(p.getY()), Math.absExact(p.getZ()));
        List<Entity> unloaded_entities = collect_entities(min_pos, max_pos, world);
        this.area = new BlockArea(min_pos, max_pos, world, false);
        this.unloaded_entities = create_entity_compound(unloaded_entities);
        if (autoswap) {
            swap(world, min_pos);
        }
    }

    public WorldArea(NbtCompound unloaded_entities, BlockPos size, BlockArea area) {
        this.unloaded_entities = unloaded_entities;
        this.size = size;
        this.area = area;
    }

    public List<Entity> collect_entities(BlockPos min_pos, BlockPos max_pos, World world) {
        List<Entity> unloaded_entities = new ArrayList<>();
        var area_box = new Box(min_pos.getX(), min_pos.getY(), min_pos.getZ(), max_pos.getX(), max_pos.getY(), max_pos.getZ());
        world.collectEntitiesByType(TypeFilter.instanceOf(Entity.class), area_box, Predicate.not(t -> t instanceof PlayerEntity), unloaded_entities);
        return unloaded_entities;
    }

    public List<Entity> collect_entities(Box area_box, World world) {
        List<Entity> unloaded_entities = new ArrayList<>();
        world.collectEntitiesByType(TypeFilter.instanceOf(Entity.class), area_box, Predicate.not(t -> t instanceof PlayerEntity), unloaded_entities);
        return unloaded_entities;
    }

    public NbtCompound create_entity_compound(List<Entity> entities) {
        var result = new NbtCompound();
        entities.forEach(entity -> {
            var list = new NbtList();
            var compound = new NbtCompound();
            compound.putString("entity_name", Registries.ENTITY_TYPE.getId(entity.getType()).toString());
            compound.put("data", entity.writeNbt(compound));
            list.add(0, compound);
            result.put("entries", list);
        });
        return result;
    }

    public void swap(World world, BlockPos pos) {
        var area_box = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.add(size).getX(), pos.add(size).getY(), pos.add(size).getZ());
        List<Entity> to_swap = collect_entities(area_box, world);
        var compound = create_entity_compound(to_swap);
        to_swap.forEach(entity -> entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK));
        for (NbtElement spawned_compound : (NbtList) Objects.requireNonNull(unloaded_entities.get("entries"))) {
            var actual_compound = (NbtCompound) spawned_compound;
            var entity_id = new Identifier(Objects.requireNonNull(actual_compound.get("entity_name")).asString());
            var entity_type = Registries.ENTITY_TYPE.get(entity_id);
            var entity = entity_type.create(world);
            assert entity != null;
            entity.readNbt(actual_compound.getCompound("data"));
            world.spawnEntity(entity);
        }
        this.unloaded_entities = compound;
        area.swap(world, pos);
    }

    public NbtCompound to_nbt() {
        var compound = new NbtCompound();
        compound.put("area", area.to_nbt());
        compound.put("size", NbtHelper.fromBlockPos(size));
        compound.put("entities", unloaded_entities);
        return compound;
    }

    public static WorldArea from_nbt(NbtCompound compound) {
        var size = NbtHelper.toBlockPos(compound, "size");
        var area = BlockArea.from_nbt((NbtCompound) compound.get("area"));
        var entities = (NbtCompound) compound.get("entities");
        //noinspection OptionalGetWithoutIsPresent
        return new WorldArea(entities, size.get(), area);
    }

}
