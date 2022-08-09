package com.cfishausen.worms.entity.client;

import com.cfishausen.worms.Worms;
import com.cfishausen.worms.entity.projectile.custom.WFishingBobberEntity;
import com.cfishausen.worms.item.WItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;

public class WormBobberRenderer extends EntityRenderer<WFishingBobberEntity> {

    private static final ResourceLocation BOBBER_TEXTURE = new ResourceLocation(Worms.MODID, "textures/entity/worm_fish_hook.png");
    private static final RenderType BOBBER_RENDER = RenderType.entityCutout(BOBBER_TEXTURE);

    public WormBobberRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * Essentially vanilla code with change to use mod's bobber render.
     */
    @Override
    public void render(WFishingBobberEntity bobber, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int i) {
        Player angler = bobber.getPlayerOwner();
        if (angler != null) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            PoseStack.Pose bobberMatrix = poseStack.last();
            Matrix4f posMatrix = bobberMatrix.pose();
            Matrix3f matrix3f = bobberMatrix.normal();
            VertexConsumer bobberOverlayVertex = buffer.getBuffer(BOBBER_RENDER); // Change

            vertex(bobberOverlayVertex, posMatrix, matrix3f, i, 0.0F, 0, 0, 1);
            vertex(bobberOverlayVertex, posMatrix, matrix3f, i, 1.0F, 0, 1, 1);
            vertex(bobberOverlayVertex, posMatrix, matrix3f, i, 1.0F, 1, 1, 0);
            vertex(bobberOverlayVertex, posMatrix, matrix3f, i, 0.0F, 1, 0, 0);

            poseStack.popPose();

            int hand = angler.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            ItemStack heldMain = angler.getMainHandItem();
            if (!heldMain.is(WItems.WORM_FISHING_ROD.get())) {
                hand = -hand;
            }

            float swingProgress = angler.getAttackAnim(partialTicks);
            float swingProgressSqrt = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
            float yawOffset = Mth.lerp(partialTicks, angler.yBodyRotO, angler.yBodyRot) * 0.017453292F;
            double sin = Mth.sin(yawOffset);
            double cos = Mth.cos(yawOffset);
            double handOffset = (double) hand * 0.35D;
            double anglerX;
            double anglerY;
            double anglerZ;
            float anglerEye;
            double fov;
            if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && angler == Minecraft.getInstance().player) {
                fov = 960.0D / this.entityRenderDispatcher.options.fov;
                Vec3 rod = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) hand * 0.525F, -0.1F);
                rod = rod.scale(fov);
                rod = rod.yRot(swingProgressSqrt * 0.5F);
                rod = rod.xRot(-swingProgressSqrt * 0.7F);
                anglerX = Mth.lerp(partialTicks, angler.xo, angler.getX()) + rod.x;
                anglerY = Mth.lerp(partialTicks, angler.yo, angler.getY()) + rod.y;
                anglerZ = Mth.lerp(partialTicks, angler.zo, angler.getZ()) + rod.z;
                anglerEye = angler.getEyeHeight();
            } else {
                anglerX = Mth.lerp(partialTicks, angler.xo, angler.getX()) - cos * handOffset - sin * 0.8D;
                anglerY = angler.yo + (double) angler.getEyeHeight() + (angler.getY() - angler.yo) * (double) partialTicks - 0.45D;
                anglerZ = Mth.lerp(partialTicks, angler.zo, angler.getZ()) - sin * handOffset + cos * 0.8D;
                anglerEye = angler.isCrouching() ? -0.1875F : 0.0F;
            }

            fov = Mth.lerp(partialTicks, bobber.xo, bobber.getX());
            double bobberY = Mth.lerp(partialTicks, bobber.yo, bobber.getY()) + 0.25D;
            double bobberZ = Mth.lerp(partialTicks, bobber.zo, bobber.getZ());
            float startX = (float) (anglerX - fov);
            float startY = (float) (anglerY - bobberY) + anglerEye;
            float startZ = (float) (anglerZ - bobberZ);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose pose = poseStack.last();


            for (int size = 0; size < 16; ++size) {
                stringVertex(startX, startY, startZ, vertexConsumer, pose, fraction(size, 15), fraction(size + 1, 16));
            }
            poseStack.popPose();
            super.render(bobber, entityYaw, partialTicks, poseStack, buffer, i);
        }
    }


    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

    private static void vertex(VertexConsumer p_114712_, Matrix4f p_114713_, Matrix3f p_114714_, int p_114715_, float p_114716_, int p_114717_, int p_114718_, int p_114719_) {
        p_114712_.vertex(p_114713_, p_114716_ - 0.5F, (float)p_114717_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)p_114718_, (float)p_114719_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114715_).normal(p_114714_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float p_174119_, float p_174120_, float p_174121_, VertexConsumer p_174122_, PoseStack.Pose p_174123_, float p_174124_, float p_174125_) {
        float f = p_174119_ * p_174124_;
        float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = p_174121_ * p_174124_;
        float f3 = p_174119_ * p_174125_ - f;
        float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = p_174121_ * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        p_174122_.vertex(p_174123_.pose(), f, f1, f2).color(0, 0, 0, 255).normal(p_174123_.normal(), f3, f4, f5).endVertex();
    }

    @Override
    @NonNull
    public ResourceLocation getTextureLocation(@NonNull WFishingBobberEntity fishHook) {
        return BOBBER_TEXTURE;
    }
}
