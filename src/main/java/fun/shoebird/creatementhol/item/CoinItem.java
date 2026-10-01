package fun.shoebird.creatementhol.item;

import fun.shoebird.creatementhol.CreateMenthol;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoinItem extends Item {
    public CoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!stack.getOrCreateNbt().getBoolean("IsMinted"))
            tooltip.add(Text.translatable(CreateMenthol.MOD_ID + ".blank_coin").setStyle(Style.EMPTY.withColor(Formatting.GRAY)));
    }
}
