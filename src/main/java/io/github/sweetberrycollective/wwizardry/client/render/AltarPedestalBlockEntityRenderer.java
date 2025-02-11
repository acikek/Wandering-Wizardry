package io.github.sweetberrycollective.wwizardry.client.render;

import io.github.sweetberrycollective.wwizardry.block.entity.AltarPedestalBlockEntity;
import io.github.sweetberrycollective.wwizardry.client.WanderingClient;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class AltarPedestalBlockEntityRenderer implements BlockEntityRenderer<AltarPedestalBlockEntity> {

	public AltarPedestalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

	@Override
	public void render(AltarPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (entity.isRemoved()) return;

		matrices.push();

		matrices.translate(0.5, 0.9509, 0.5);

		var blockState = entity.getWorld().getBlockState(entity.getPos());
		var dir = blockState.get(HorizontalFacingBlock.FACING);

		switch (dir) {
			case NORTH -> {
				matrices.translate(0, 0, 0.09335);
				matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(22.5f));
				matrices.translate(0, 0, -0.046875);
			}
			case SOUTH -> {
				matrices.translate(0, 0, -0.09335);
				matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(22.5f));
				matrices.translate(0, 0, 0.046875);
			}
			case EAST -> {
				matrices.translate(0.09335, 0, 0);
				matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(22.5f));
				matrices.translate(-0.125, 0.0859375, 0);

			}
			case WEST -> {
				matrices.translate(-0.09335, 0, 0);
				matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(22.5f));
				matrices.translate(0.125, 0.0859375, 0);
			}
		}

		matrices.translate(0, 0.25, 0);
		if (!entity.crafting) {
			matrices.translate(0, Math.sin((WanderingClient.ITEM_ROTATION + tickDelta) * 0.25 + entity.rand) * 0.03125, 0);
		} else {
			matrices.translate(0, (entity.craftingTick + tickDelta) / 25, 0);
		}
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(WanderingClient.ITEM_ROTATION));

		MinecraftClient.getInstance().getItemRenderer().renderItem(entity.heldItem, ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

		matrices.pop();
	}
}
