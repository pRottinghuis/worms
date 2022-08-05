package com.cfishausen.worms.event;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.WEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

@Mod.EventBusSubscriber(modid = Worms.MODID)
public class WEvents {

    public static void spawnWorm(ServerLevel level, BlockPos pos) {
        WEntityTypes.WORM.get().spawn(level, null, null, pos,
                MobSpawnType.COMMAND, true, false);
    }

    @SubscribeEvent
    public static void wormSpawnOnBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().level.isClientSide()) {
            BlockState state = event.getState();
            ServerLevel level = (ServerLevel) event.getPlayer().level;
            if (state.is(Blocks.MUD) || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT_PATH) || state.is(Blocks.DIRT) || state.is(Blocks.PODZOL)) {
                if (level.getRandom().nextInt(100) == 1) {
                    BlockPos pos = event.getPos();
                    ServerLevel serverLevel = (ServerLevel) event.getPlayer().getLevel();
                    spawnWorm(serverLevel, pos);
                }
            }
            if (state.is(Blocks.ROOTED_DIRT)) {
                if (level.getRandom().nextInt(5) == 1) {
                    BlockPos pos = event.getPos();
                    ServerLevel serverLevel = (ServerLevel) event.getPlayer().getLevel();
                    spawnWorm(serverLevel, pos);
                }
            }
            if (state.is(Blocks.FARMLAND) || state.is(Blocks.MUDDY_MANGROVE_ROOTS)) {
                if (level.getRandom().nextInt(20) == 1) {
                    BlockPos pos = event.getPos();
                    ServerLevel serverLevel = (ServerLevel) event.getPlayer().getLevel();
                    spawnWorm(serverLevel, pos);
                }
            }
        }
    }
}
