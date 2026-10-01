package fun.shoebird.creatementhol.block.coinpress;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import fun.shoebird.creatementhol.CreateMenthol;
import net.createmod.catnip.math.AngleHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.util.function.Consumer;


public class CoinPressVisual extends ShaftVisual<CoinPressBlockEntity> implements SimpleDynamicVisual {
    private static final PartialModel COIN_PRESS_HEAD = PartialModel.of(new Identifier(CreateMenthol.MOD_ID, "block/coin_press/head"));
    private final OrientedInstance pressHead;

    public static void register() {}

    public CoinPressVisual(VisualizationContext context, CoinPressBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        pressHead = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(COIN_PRESS_HEAD))
                .createInstance();

        Quaternionf q = RotationAxis.POSITIVE_Y
                .rotationDegrees(AngleHelper.horizontalAngle(blockState.get(MechanicalPressBlock.HORIZONTAL_FACING)));

        pressHead.rotation(q);

        transformModels(partialTick);
    }

    private void transformModels(float pt) {
        float renderedHeadOffset = getRenderedHeadOffset(pt);

        pressHead.position(getVisualPosition())
                .translatePosition(0, -renderedHeadOffset, 0)
                .setChanged();
    }

    private float getRenderedHeadOffset(float pt) {
        PressingBehaviour pressingBehaviour = blockEntity.getPressingBehaviour();
        return pressingBehaviour.getRenderedHeadOffset(pt)
                * pressingBehaviour.mode.headOffset;
    }

    @Override
    public void beginFrame(DynamicVisual.Context context) {
        transformModels(context.partialTick());
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        relight(pressHead);
    }

    @Override
    protected void _delete() {
        super._delete();
        pressHead.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        consumer.accept(pressHead);
    }
}
