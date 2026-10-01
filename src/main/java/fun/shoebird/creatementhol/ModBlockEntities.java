package fun.shoebird.creatementhol;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import fun.shoebird.creatementhol.block.coinpress.CoinPressRenderer;
import fun.shoebird.creatementhol.block.coinpress.CoinPressVisual;
import fun.shoebird.creatementhol.block.coinpress.CoinPressBlockEntity;

public class ModBlockEntities {
    private static final CreateRegistrate REGISTRATE = CreateMenthol.REGISTRATE;

    public static final BlockEntityEntry<CoinPressBlockEntity> COIN_PRESS = REGISTRATE
            .blockEntity("coin_press_be", CoinPressBlockEntity::new)
            .visual(() -> CoinPressVisual::new)
            .validBlocks(ModBlocks.COIN_PRESS)
            .renderer(() -> CoinPressRenderer::new)
            .register();

    /*public static final BlockEntityType<CoinpressBlockEntity> COIN_PRESS_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CreateMenthol.MOD_ID, "coin_press_be"),
                    CreateBlockEntityBuilder.create(CoinpressBlockEntity::new,
                            ModBlocks.COIN_PRESS).build());*/

    public static void register() { }
}
