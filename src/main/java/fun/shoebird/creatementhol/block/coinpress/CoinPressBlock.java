package fun.shoebird.creatementhol.block.coinpress;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.AdventureUtil;
import com.tterrag.registrate.fabric.EnvExecutor;
import fun.shoebird.creatementhol.ModBlockEntities;
import net.createmod.catnip.gui.ScreenOpener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CoinPressBlock extends HorizontalKineticBlock implements IBE<CoinPressBlockEntity> {
    public CoinPressBlock(Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
                              BlockHitResult hit) {
        if (AdventureUtil.isAdventure(player))
            return ActionResult.PASS;
        ItemStack held = player.getMainHandStack();
        if (AllItems.WRENCH.isIn(held))
            return ActionResult.PASS;
        if (held.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof KineticBlock && hasShaftTowards(worldIn, pos, state, hit.getSide()))
                return ActionResult.PASS;
        }

        EnvExecutor.runWhenOn(EnvType.CLIENT,
                () -> () -> withBlockEntityDo(worldIn, pos, be -> this.displayScreen(be, player)));
        return ActionResult.SUCCESS;
    }

    @Environment(value = EnvType.CLIENT)
    protected void displayScreen(CoinPressBlockEntity be, PlayerEntity player) {
        if (player instanceof ClientPlayerEntity)
            ScreenOpener.open(new CoinPressScreen(be));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext
                && ((EntityShapeContext) context).getEntity() instanceof PlayerEntity)
            return AllShapes.CASING_14PX.get(Direction.DOWN);

        return AllShapes.MECHANICAL_PROCESSOR_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction prefferedSide = getPreferredHorizontalFacing(context);
        if (prefferedSide != null)
            return getDefaultState().with(HORIZONTAL_FACING, prefferedSide);
        return super.getPlacementState(context);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.get(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.get(HORIZONTAL_FACING)
                .getAxis();
    }

    @Override
    public Class<CoinPressBlockEntity> getBlockEntityClass() {
        return CoinPressBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CoinPressBlockEntity> getBlockEntityType() {
        return ModBlockEntities.COIN_PRESS.get();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView reader, BlockPos pos, NavigationType type) {
        return false;
    }
}
