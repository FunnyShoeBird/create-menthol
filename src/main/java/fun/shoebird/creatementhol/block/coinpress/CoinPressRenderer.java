package fun.shoebird.creatementhol.block.coinpress;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class CoinPressRenderer extends KineticBlockEntityRenderer<CoinPressBlockEntity> {
    public CoinPressRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

//    @Override
//    public boolean shouldRenderOffScreen(CoinpressBlockEntity be) {
//        return true;
//    }

    @Override
    protected void renderSafe(CoinPressBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer,
                              int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getWorld()))
            return;

        BlockState blockState = be.getCachedState();
        PressingBehaviour pressingBehaviour = be.getPressingBehaviour();
        float renderedHeadOffset =
                pressingBehaviour.getRenderedHeadOffset(partialTicks) * pressingBehaviour.mode.headOffset;

        SuperByteBuffer headRender = CachedBuffers.partialFacing(AllPartialModels.MECHANICAL_PRESS_HEAD, blockState,
                blockState.get(HORIZONTAL_FACING));
        headRender.translate(0, -renderedHeadOffset, 0)
                .light(light)
                .renderInto(ms, buffer.getBuffer(RenderLayer.getSolid()));
    }

    //@Override
    //protected BlockState getRenderedBlockState(MechanicalPressBlockEntity be) {
   //     return shaft(getRotationAxisOf(be));
    //}

}
