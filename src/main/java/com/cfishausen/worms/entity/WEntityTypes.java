package com.cfishausen.worms.entity;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.animal.custom.WormEntity;
import com.cfishausen.worms.entity.projectile.custom.WormFishingHook;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Worms.MODID);

    public static final RegistryObject<EntityType<WormEntity>> WORM =
            ENTITY_TYPES.register("worm",
                    () -> EntityType.Builder.of(WormEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(Worms.MODID, "worm").toString()));


    public static final RegistryObject<EntityType<WormFishingHook>> WORM_FISHING_BOBBER = ENTITY_TYPES.register("worm_fishing_bobber", () -> EntityType.Builder.of(new EntityType.EntityFactory<WormFishingHook>() {
        @Override
        public WormFishingHook create(EntityType<WormFishingHook> p_20722_, Level p_20723_) {
            return new WormFishingHook(p_20722_, p_20723_);
        }
    }, MobCategory.MISC).noSave().noSummon().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5).build(new ResourceLocation(Worms.MODID, "worm_fishing_bobber").toString()));


    public static void register(IEventBus eventbus) {
        ENTITY_TYPES.register(eventbus);
    }

}
