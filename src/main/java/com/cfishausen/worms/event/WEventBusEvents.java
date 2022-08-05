package com.cfishausen.worms.event;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.WEntityTypes;
import com.cfishausen.worms.entity.animal.custom.WormEntity;
import com.cfishausen.worms.entity.client.WormBobberRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Worms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(WEntityTypes.WORM.get(), WormEntity.setAttributes());
    }
}
