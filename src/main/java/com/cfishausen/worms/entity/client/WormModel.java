package com.cfishausen.worms.entity.client;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.animal.custom.WormEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WormModel extends AnimatedGeoModel<WormEntity> {
    @Override
    public ResourceLocation getModelResource(WormEntity object) {
        return new ResourceLocation(Worms.MODID, "geo/worm.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WormEntity object) {
        return new ResourceLocation(Worms.MODID, "textures/entity/worm/worm.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WormEntity animatable) {
        return new ResourceLocation(Worms.MODID, "animations/worm.animation.json");
    }
}
