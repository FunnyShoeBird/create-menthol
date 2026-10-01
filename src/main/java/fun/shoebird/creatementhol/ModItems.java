package fun.shoebird.creatementhol;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import fun.shoebird.creatementhol.item.CoinItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    private static final CreateRegistrate REGISTRATE = CreateMenthol.REGISTRATE;

    public static final ItemEntry<CoinItem> BRASS_COIN = REGISTRATE.item("brass_coin", CoinItem::new)
            .register();

    public static final ItemEntry<CoinItem> COPPER_COIN = REGISTRATE.item("copper_coin", CoinItem::new)
            .register();

    public static final ItemEntry<CoinItem> GOLD_COIN = REGISTRATE.item("gold_coin", CoinItem::new)
            .register();

    public static final ItemEntry<CoinItem> IRON_COIN = REGISTRATE.item("iron_coin", CoinItem::new)
            .register();

    public static final ItemEntry<CoinItem> ZINC_COIN = REGISTRATE.item("zinc_coin", CoinItem::new)
            .register();

//    public static final Item COPPER_COIN = register("copper_coin", new Item(new Item.Settings()));
//
//    public static final ItemGroup CREATE_MENTHOL_GROUP = FabricItemGroup.builder()
//            .icon(() -> new ItemStack(COPPER_COIN))
//            .displayName(Text.translatable("itemGroup.create-menthol.item_group"))
//            .entries((context, entries) -> {
//                entries.add(ModBlocks.COIN_PRESS.asItem());
//                entries.add(COPPER_COIN);
//            })
//            .build();

    public static void register() { }
}
