package com.cfishausen.worms.entity.client;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.WEntityTypes;
import com.cfishausen.worms.item.WItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = Worms.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler {

    public static void setupClient() {
        registerFishingRodModelProperties(WItems.WORM_FISHING_ROD.get());
    }

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WEntityTypes.WORM_FISHING_BOBBER.get(), WormBobberRenderer::new);
    }

    public static void registerFishingRodModelProperties(Item fishingRod) {
        ItemProperties.register(fishingRod, new ResourceLocation("cast"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                boolean isMainhand = entity.getMainHandItem() == stack;
                boolean isOffHand = entity.getOffhandItem() == stack;
                if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                    isOffHand = false;
                }
                return (isMainhand || isOffHand) && entity instanceof Player && ((Player) entity).fishing != null ? 1.0F : 0.0F;
            }
        });
    }
}
