package com.cfishausen.worms.entity.projectile.custom;

import com.cfishausen.worms.entity.WEntityTypes;
import com.cfishausen.worms.item.WItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nonnull;


public class WFishingBobberEntity extends FishingHook implements IEntityAdditionalSpawnData {

    public WFishingBobberEntity(PlayMessages.SpawnEntity spawnPacket, Level world) {
        super(world.getPlayerByUUID(spawnPacket.getAdditionalData().readUUID()), world, 0, 0);

    }

    public WFishingBobberEntity(Player player, Level world, int luck, int lureSpeed) {
        super(player, world, luck, lureSpeed);

    }

    /**
     * Changed to use mod's fishing rod. Otherwise, remains vanilla.
     */
    @Override
    protected boolean shouldStopFishing(Player p_37137_) {
        ItemStack itemstack = p_37137_.getMainHandItem();
        ItemStack itemstack1 = p_37137_.getOffhandItem();
        Item wormRod = WItems.WORM_FISHING_ROD.get(); // change
        boolean flag = itemstack.is(wormRod); // change
        boolean flag1 = itemstack1.is(wormRod); // change
        if (!p_37137_.isRemoved() && p_37137_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_37137_) > 1024.0D)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    @Override
    public EntityType<?> getType() {
        return WEntityTypes.WORM_FISHING_BOBBER.get();
    }


    /**
     * Communicate spawn with server
     */
    @Override
    @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        Player player = this.getPlayerOwner();
        if (player != null) {
            buffer.writeUUID(player.getUUID());
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {

    }
}
