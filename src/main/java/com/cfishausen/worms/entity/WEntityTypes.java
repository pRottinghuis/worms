package com.cfishausen.worms.entity;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.animal.custom.WormEntity;
import com.cfishausen.worms.entity.projectile.custom.WFishingBobberEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Worms.MODID);

    public static final RegistryObject<EntityType<WormEntity>> WORM =
            ENTITY_TYPES.register("worm",
                    () -> EntityType.Builder.of(WormEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(Worms.MODID, "worm").toString()));

    public static final RegistryObject<EntityType<WFishingBobberEntity>> WORM_FISHING_BOBBER = ENTITY_TYPES.register("worm_fishing_bobber", () -> {
        return ((EntityType.Builder)EntityType.Builder.createNothing(MobCategory.MISC)
                .noSave()
                .noSummon()
                .sized(0.25F, 0.25F)
                .setTrackingRange(4)
                .setUpdateInterval(5)
                .setCustomClientFactory(WFishingBobberEntity::new))
                .build((new ResourceLocation(Worms.MODID, "worm_fishing_bobber")).toString());
    });

    public static void register(IEventBus eventbus) {
        ENTITY_TYPES.register(eventbus);
    }

}
