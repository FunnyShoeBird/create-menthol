package fun.shoebird.creatementhol;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import fun.shoebird.creatementhol.block.coinpress.CoinPressVisual;
import fun.shoebird.creatementhol.block.coinpress.ConfigureCoinPressPacket;
import io.github.fabricators_of_create.porting_lib.event.common.ModsLoadedCallback;
import net.createmod.catnip.lang.FontHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateMenthol implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "create-menthol";

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
			.defaultCreativeTab((RegistryKey<ItemGroup>) null)
			.setTooltipModifierFactory(item ->
					new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
							.andThen(TooltipModifier.mapNull(KineticStats.create(item)))
			);

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModsLoadedCallback.EVENT.register(envType -> { ConfigureCoinPressPacket.register(); });

		ModBlocks.register();
		ModItems.register();
		ModItemGroups.register();

		ModBlockEntities.register();

		REGISTRATE.register();

		LOGGER.info("Hello Twitter world!");
	}

	@Override
	public void onInitializeClient() {
		ModsLoadedCallback.EVENT.register(envType -> { CoinPressVisual.register(); });

//		ModBlocks.register();
//		ModItems.register();
//		ModItemGroups.register();
//
//		ModBlockEntities.register();
//
//		REGISTRATE.register();

		LOGGER.info("Hello Client world!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

}
