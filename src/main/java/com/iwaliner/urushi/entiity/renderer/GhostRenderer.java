package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.entiity.GhostEntity;
import com.iwaliner.urushi.entiity.model.GhostModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;


public class GhostRenderer extends HumanoidMobRenderer<GhostEntity, GhostModel<GhostEntity>> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("urushi:textures/entity/ghost.png");

    public GhostRenderer(EntityRendererProvider.Context p_174380_) {
        this(p_174380_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public GhostRenderer(EntityRendererProvider.Context p_174382_, ModelLayerLocation p_174383_, ModelLayerLocation p_174384_, ModelLayerLocation p_174385_) {
        super(p_174382_, new GhostModel<>(p_174382_.bakeLayer(p_174383_)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new GhostModel<>(p_174382_.bakeLayer(p_174384_)), new GhostModel<>(p_174382_.bakeLayer(p_174385_))));
    }

    public ResourceLocation getTextureLocation(GhostEntity p_115941_) {
        return TEXTURE_LOCATION;
    }

}
