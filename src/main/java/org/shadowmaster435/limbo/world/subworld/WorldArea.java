package org.shadowmaster435.limbo.world.subworld;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.util.MiscUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.function.Predicate;

public class WorldArea {

    public NbtCompound unloaded_entities;
    public BlockArea area;
    private final BlockPos size;
    public Box area_bounds;

    public WorldArea(BlockPos min_pos, BlockPos max_pos, World world) {
        size = new BlockPos(Math.absExact(min_pos.getX() - max_pos.getX()), Math.absExact(min_pos.getY() - max_pos.getY()), Math.absExact(min_pos.getZ()- max_pos.getZ()));

        this.area = new BlockArea(min_pos, max_pos, world, false);
        this.area_bounds = MiscUtil.create_from_points(MiscUtil.to_vec3d(min_pos), MiscUtil.to_vec3d(max_pos));
        var start_compound = new NbtCompound();
        var start_list = new NbtList();
        start_compound.put("entries", start_list);
    }

    public WorldArea(NbtCompound unloaded_entities, BlockPos size, BlockArea area) {

        this.unloaded_entities = unloaded_entities;
        this.size = size;
        this.area = area;
        this.area_bounds = MiscUtil.box_from_size(MiscUtil.to_vec3d(size));
    }

    public List<Entity> collect_entities(Box area_box, BlockPos offset, World world) {
        return world.getEntitiesByType(TypeFilter.instanceOf(Entity.class), area_box.offset(offset), (t) -> {
            return !t.isPlayer();
        });
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

    public void swap(World world, BlockPos pos, BlockPos bepos) {
        List<Entity> to_swap = collect_entities(area_bounds, bepos, world);
        var compound = create_entity_compound(to_swap);
        System.out.println(area_bounds);

        if (unloaded_entities == null || unloaded_entities.get("entries") == null) {
            var start_compound = new NbtCompound();
            var start_list = new NbtList();
            start_compound.put("entries", start_list);
            unloaded_entities = start_compound.copy();
        }
        to_swap.forEach(entity -> {
            if (!(entity instanceof PlayerEntity)) {
                entity.remove(Entity.RemovalReason.KILLED);
            }
        });
        try {
            for (NbtElement spawned_compound : (NbtList) unloaded_entities.get("entries")) {
                var actual_compound = (NbtCompound) spawned_compound;
                var entity_id = new Identifier(Objects.requireNonNull(actual_compound.get("entity_name")).asString());
                var entity_type = Registries.ENTITY_TYPE.get(entity_id);
                var entity = entity_type.create(world);
                if (entity == null) {
                    continue;
                }
                if (entity.isPlayer()) {
                    continue;
                }
                entity.readNbt(actual_compound.getCompound("data"));
                world.spawnEntity(entity);
            }
            this.unloaded_entities = compound;
            area.swap(world, pos, bepos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public NbtCompound to_nbt() {
        var compound = new NbtCompound();
        compound.put("area", area.to_nbt());
        compound.put("size", NbtHelper.fromBlockPos(size));
        compound.put("entities", unloaded_entities);
        return compound;
    }

    public static WorldArea from_nbt(BlockPos min_pos, BlockPos max_pos, NbtCompound compound) {
        var size = NbtHelper.toBlockPos(compound, "size");
        var area = BlockArea.from_nbt(min_pos, max_pos, (NbtCompound) compound.get("area"));

        var entities = (NbtCompound) compound.get("entities");
        //noinspection OptionalGetWithoutIsPresent
        return new WorldArea(entities, size.get(), area);
    }

}
