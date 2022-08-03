package com.cfishausen.worms.item;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.WEntityTypes;
import com.cfishausen.worms.item.custom.WFishingRodItem;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Worms.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> WORM_SPAWN_EGG = ITEMS.register("worm_spawn_egg",
            () -> new ForgeSpawnEggItem(WEntityTypes.WORM, 0xff9387, 0xffada3,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> WORM = ITEMS.register("worm", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<WFishingRodItem> WORM_FISHING_ROD = ITEMS.register("worm_fishing_rod", () -> new WFishingRodItem((new Item.Properties()).durability(64).tab(CreativeModeTab.TAB_TOOLS)));

    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
