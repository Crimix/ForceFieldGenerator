package com.black_dog20.forcefieldgenerator.client.render;

import com.black_dog20.bml.utils.color.Color4i;
import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ForceFieldLayerRender <T extends Player, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForceFieldGenerator.MOD_ID, "textures/layers/forcefield_layer.png");
    private final HumanoidModel<T> innerModel;
    private final HumanoidModel<T> outerModel;

    public ForceFieldLayerRender(RenderLayerParent<T, M> renderer, EntityModelSet entityModelSet, boolean smallArms) {
        super(renderer);
        innerModel = new HumanoidModel<>(entityModelSet.bakeLayer(smallArms ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR), RenderType::entityTranslucent);
        outerModel = new HumanoidModel<>(entityModelSet.bakeLayer(smallArms ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR), RenderType::entityTranslucent);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        prepareModels(innerModel, outerModel);
        if (shouldRender(entitylivingbase)) {
            render(innerModel, matrixStack, buffer, packedLight, entitylivingbase, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
            render(outerModel, matrixStack, buffer, packedLight, entitylivingbase, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }



    private boolean shouldRender(Player player) {
        return player.getPersistentData().getInt("forcefieldTicks") != 0;
    }

    private void prepareModels(HumanoidModel<T> innerModel, HumanoidModel<T> outerModel) {
        innerModel.hat.visible = false;
        innerModel.head.visible = false;
        innerModel.leftArm.visible = false;
        innerModel.rightArm.visible = false;
        outerModel.leftLeg.visible = false;
        outerModel.rightLeg.visible = false;

    }

    private void render(HumanoidModel<T> model, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T entitylivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        model.crouching = entitylivingbase.isCrouching();
        matrixStack.pushPose();
        matrixStack.scale(1.05F, 1.05F, 1.05F);
        this.getParentModel().copyPropertiesTo(model);
        model.setupAnim(entitylivingbase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.getParentModel().copyPropertiesTo(model); //Do it again because mods like Quark reset the rotateZ angle at every render tick see https://github.com/Vazkii/Quark/issues/2765
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(buffer, model.renderType(TEXTURE), false, false);
        Color4i color = getColor(entitylivingbase);
        model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        matrixStack.popPose();
    }

    private Color4i getColor(Player player) {
        int x = player.getPersistentData().getInt("forcefieldTicks");
        if (80 >= x && x >= 50) {
            return Color4i.of(255, 0, 0, 255);
        } else if (50 > x && x >= 20) {
            return Color4i.of(200, 100, 100, 255);
        } else if (20 > x) {
            return Color4i.of(0, 255, 255, 255);
        } else {
            return Color4i.of(0, 0, 0, 0);
        }
    }
}