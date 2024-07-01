package org.shadowmaster435.limbo.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.shadowmaster435.limbo.world.feature.config.TenebraTreeFeatureConfig;

public class TenebraTreeFeature extends Feature<TenebraTreeFeatureConfig> {
    public TenebraTreeFeature(Codec<TenebraTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    // this method is what is called when the game tries to generate the feature. it is where the actual blocks get placed into the world.
    @Override
    public boolean generate(FeatureContext<TenebraTreeFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();

        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        TenebraTreeFeatureConfig config = context.getConfig();

        int trunk_height = config.trunk_height().get(random);
        int trunk_size = config.trunk_size().get(random);


        BlockState log_state = config.logs().get(random, origin);
        BlockState leaf_state = config.leaves().get(random, origin);

        if (log_state == null) throw new IllegalStateException(" could not be parsed to a valid block identifier!");
        if (leaf_state == null) throw new IllegalStateException(" could not be parsed to a valid block identifier!");
        var rand_ofs = random.nextDouble() * 100;
        for (int x = trunk_size * -6; x < trunk_size * 12; ++x) {
            for (int y = 0; y < trunk_height; y++) {
                for (int z = trunk_size * -6; z < trunk_size * 12; ++z) {
                    var pos = new BlockPos(x, y, z).add(origin);
                    var offset_pos = pos.withY(0).toCenterPos().add(new Vec3d(Math.cos(y + origin.getY() + random.nextDouble() * 2), 0, Math.sin(y + origin.getY() +  random.nextDouble() * 2)));

                    if (!world.isAir(pos)) continue;
                    if (offset_pos.distanceTo(origin.withY(0).toCenterPos()) < 3 - Math.cos(y + origin.getY() + random.nextDouble() * 1.1) && y > trunk_height / 4) {
                        world.setBlockState(pos, leaf_state, 2);
                    }
                    if (offset_pos.distanceTo(origin.withY(0).toCenterPos()) < trunk_size / 1.2f  && y < trunk_height - 1) {
                        world.setBlockState(pos, log_state, 2);
                    }

                }
            }
        }
        return true;
    }
}
