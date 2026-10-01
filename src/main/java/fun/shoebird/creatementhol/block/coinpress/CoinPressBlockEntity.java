package fun.shoebird.creatementhol.block.coinpress;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import fun.shoebird.creatementhol.CreateMenthol;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class CoinPressBlockEntity extends KineticBlockEntity implements PressingBehaviour.PressingBehaviourSpecifics {
    private static final TagKey<Item> COIN_PRESSABLE = TagKey.of(RegistryKeys.ITEM, new Identifier(CreateMenthol.MOD_ID, "coin_pressable"));
    public PressingBehaviour pressingBehaviour;

    public String currencyName = "";
    public String currencySecurity = "";

    public CoinPressBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        currencyName = tag.getString("CurrencyName");
        currencySecurity = tag.getString("CurrencySecurity");
    }

    @Override
    public void writeSafe(NbtCompound tag) {
        super.writeSafe(tag);
        tag.putString("CurrencyName", currencyName);
        tag.putString("CurrencySecurity", currencySecurity);
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putString("CurrencyName", currencyName);
        tag.putString("CurrencySecurity", currencySecurity);
    }

    @Override
    protected Box createRenderBoundingBox() {
        return new Box(pos).stretch(0, -1.5, 0)
                .stretch(0, 1, 0);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        pressingBehaviour = new PressingBehaviour(this);
        behaviours.add(pressingBehaviour);
    }

    public PressingBehaviour getPressingBehaviour() {
        return pressingBehaviour;
    }

    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        return false;
    }

    @Override
    public boolean tryProcessInWorld(ItemEntity itemEntity, boolean simulate) {
        ItemStack item = itemEntity.getStack();
        boolean canPressItem = canPressItem(item);

        if (!canPressItem)
            return false;
        else if (simulate)
            return true;

        pressingBehaviour.particleItems.add(item);

        if (item.getCount() == 1) {
            itemEntity.setStack(pressItem(item));
        } else {
            ItemStack result = pressItem(ItemHandlerHelper.copyStackWithSize(item, 1));
            ItemEntity created = new ItemEntity(world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
            created.setToDefaultPickupDelay();
            created.setVelocity(VecHelper.offsetRandomly(Vec3d.ZERO, world.random, .05f));
            world.spawnEntity(created);
            item.decrement(1);
        }

        return true;
    }

    @Override
    public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate) {
        boolean canPressItem = canPressItem(input.stack);
        if (!canPressItem)
            return false;
        else if (simulate)
            return true;

        pressingBehaviour.particleItems.add(input.stack);

        outputList.add(pressItem(ItemHandlerHelper.copyStackWithSize(input.stack, 1)));

        return true;
    }

    @Override
    public void onPressingCompleted() { }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }

    @Override
    public boolean canProcessInBulk() {
        return false;
    }

    @Override
    public int getParticleAmount() {
        return 15;
    }

    private boolean canPressItem(ItemStack item) {
        return item.isIn(COIN_PRESSABLE) && !item.getOrCreateNbt().getBoolean("IsMinted");
    }

    private ItemStack pressItem(ItemStack item) {
        if (!currencyName.isBlank()) item.setCustomName(Text.literal(currencyName).setStyle(Style.EMPTY.withItalic(false)));
        if (!currencySecurity.isBlank()) item.setSubNbt("CurrencySecurity", NbtString.of(currencySecurity));
        item.setSubNbt("IsMinted", NbtByte.of(true));

        return item;
    }

    public void setCurrency(String currencyName, String currencySecurity) {
        this.currencyName = !currencyName.isBlank() ? currencyName : this.currencyName;
        this.currencySecurity = !currencySecurity.isBlank() ? currencySecurity : this.currencySecurity;
        this.markDirty();
        this.sendData();
    }
}
