package com.Moyashi.nekokamiko.entity.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class HunterModels<T extends Entity> extends SegmentedModel<T> implements IHasHead, IHeadToggle {
	private final ModelRenderer Head;
	private final ModelRenderer Body;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer bb_main;

	public HunterModels() {
		this(64, 64);
	}

	public HunterModels(int w, int h) {

		Head = new ModelRenderer(this).setTexSize(w, h);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Head.texOffs(1, 0).addBox(-4.0F, -8.0F, -3.0F, 9.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this).setTexSize(w, h);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.texOffs(19, 16).addBox(-4.0F, 0.0F, -2.0F, 9.0F, 12.0F, 4.0F, 0.0F, false);

		RightArm = new ModelRenderer(this).setTexSize(w, h);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);
		RightArm.texOffs(46, 16).addBox(-4.0F, -2.0F, -2.0F, 5.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this).setTexSize(w, h);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);
		LeftArm.texOffs(46, 16).addBox(0.0F, -2.0F, -2.0F, 5.0F, 12.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this).setTexSize(w, h);
		RightLeg.setPos(-1.9F, 12.0F, 0.0F);
		RightLeg.texOffs(1, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this).setTexSize(w, h);
		LeftLeg.setPos(1.9F, 12.0F, 0.0F);
		LeftLeg.texOffs(1, 16).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		bb_main = new ModelRenderer(this).setTexSize(w, h);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-1.0F, -36.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.getHead(), this.Body, this.RightArm, this.LeftArm, this.RightLeg, this.LeftLeg);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		this.bb_main.xRot = -0.3054F;
		this.LeftLeg.xRot = -1.5F * MathHelper.triangleWave(p_225597_2_, 13.0F) * p_225597_3_;
		this.RightLeg.xRot = 1.5F * MathHelper.triangleWave(p_225597_2_, 13.0F) * p_225597_3_;
		this.LeftLeg.yRot = 0.0F;
		this.RightLeg.yRot = 0.0F;
	}

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}

	@Override
	public void hatVisible(boolean p_217146_1_) {

	}
}