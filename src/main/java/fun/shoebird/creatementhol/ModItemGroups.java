package fun.shoebird.creatementhol;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MENTHOL_ITEMS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.COIN_PRESS.asItem()))
            .displayName(Text.translatable("itemGroup.create-menthol.menthol_items"))
            .entries((context, entries) -> {
                entries.add(ModBlocks.COIN_PRESS.asItem());
                entries.add(ModItems.BRASS_COIN);
                entries.add(ModItems.COPPER_COIN);
                entries.add(ModItems.GOLD_COIN);
                entries.add(ModItems.IRON_COIN);
                entries.add(ModItems.ZINC_COIN);
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of("create-menthol", "menthol_items"), MENTHOL_ITEMS);
    }
}
