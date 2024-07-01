package org.shadowmaster435.limbo.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JavaOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.shadowmaster435.limbo.init.ModBlocks;
import org.shadowmaster435.limbo.util.callable.Callable;
import org.shadowmaster435.limbo.world.subworld.SubWorldArea;
import org.shadowmaster435.limbo.world.subworld.SubWorldLoadZone;
import org.shadowmaster435.limbo.world.subworld.WorldArea;

public class FeatureTestEntity extends BlockEntity {

    public SubWorldArea subWorldArea;
    public SubWorldLoadZone loadZone;
    public boolean area_created = false;
    public boolean made_from_nbt = false;
    public Codec<String> testcodec;

    public FeatureTestEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.FEATURE_TEST_ENTITY, pos, state);
        if (!area_created) {
            create_default_area();

        }
    }


    public void create_area_from_nbt(NbtCompound compound) {

        var area = new SubWorldArea(pos.subtract(new Vec3i(3, 3, 3)), pos.add(new Vec3i(3, 3, 3)), compound);
        subWorldArea = area;
        loadZone = new SubWorldLoadZone(pos.add(0,1,0), pos.add(new Vec3i(1, 3, 1)), world, area, pos, pos.add(1, 2, 1));
        area_created = true;
    }

    public void create_default_area() {

        subWorldArea = new SubWorldArea(pos.subtract(new Vec3i(3, 3, 3)), pos.add(new Vec3i(3, 3, 3)), world);
        loadZone = new SubWorldLoadZone(pos.add(0,1,0), pos.add(new Vec3i(1, 3, 1)), world, subWorldArea, pos, pos.add(1, 2, 1));
        area_created = true;
    }
    public int callcount = 0;
    public NbtCompound stored_tag;
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("area")) {

            create_area_from_nbt(nbt);
            stored_tag = nbt;
            made_from_nbt = nbt.getBoolean("made_from_nbt");

        } else {
            callcount += 1;
            //System.out.println("?");
           //. create_default_area();
        }
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

        if (subWorldArea != null) {
            if (!((NbtList) subWorldArea.area.area.to_nbt().get("entries")).isEmpty()) {
                nbt.copyFrom(subWorldArea.area.to_nbt());
            }


        }

        super.writeNbt(nbt, registryLookup);

    }

    public boolean loaded_nbt = false;

    public static void tick(World world, BlockPos pos, BlockState state, FeatureTestEntity be) {
//        if (be.subWorldArea == null && !be.made_from_nbt) {
//            be.subWorldArea = new SubWorldArea(pos.subtract(new Vec3i(3, 3, 3)), pos.add(new Vec3i(3, 3, 3)), world);
//            be.loadZone = new SubWorldLoadZone(pos.add(0,1,0), pos.add(new Vec3i(1, 3, 1)), world, be.subWorldArea, pos, pos.add(1, 2, 1));
//            be.area_created = true;
//        }
        //  world.breakBlock(pos, false);
       // world.breakBlock(pos, false);
        if (be.loadZone == null) {
            return;
        }
        if (be.subWorldArea != null) {
            be.subWorldArea.world = world;

            if (be.stored_tag != null && !be.loaded_nbt) {
                be.create_area_from_nbt(be.stored_tag);
                be.loaded_nbt = true;
            }
        }
        be.loadZone.world = world;
        be.loadZone.update();
        be.markDirty();

    }


}
