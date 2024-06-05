package org.shadowmaster435.limbo.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
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
        for (int x = 0; x < trunk_size * 16; ++x) {
            for (int y = 0; y < trunk_height; y++) {
                for (int z = 0; z < trunk_size * 16; ++z) {
                    var pos = new BlockPos(x, y, z).add(origin);

                    if (!world.isAir(pos)) continue;

                    var sine = (Math.sin(y + rand_ofs)) * trunk_size;
                    var cosine = (Math.cos(y + rand_ofs)) * trunk_size;
                    var center = new BlockPos((int) Math.floor(cosine), y, (int) Math.floor(sine)).add(origin).add(trunk_size,0,trunk_size);
                    var dist = pos.toCenterPos().subtract(0.5,0.5,0.5).distanceTo(center.toCenterPos().subtract(0.5,0.5,0.5));
                    if (dist < trunk_size) {
                        if (y < trunk_height - 2) {
                            world.setBlockState(pos,log_state,2);

                        } else {
                            world.setBlockState(pos,leaf_state,2);

                        }
                    }

                    if (world.isAir(pos) && y > trunk_height / 3 && dist < (trunk_size * 2) - Random.create((origin.getX() + y + origin.getZ())).nextBetween(0, 4)) {
                        world.setBlockState(pos, leaf_state, 2);
                    }
                }
            }
        }
        return true;
    }
}
