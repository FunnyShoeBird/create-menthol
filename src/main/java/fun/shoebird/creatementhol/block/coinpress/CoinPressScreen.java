package fun.shoebird.creatementhol.block.coinpress;

import com.mojang.datafixers.TypeRewriteRule;
import com.simibubi.create.AllPackets;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import fun.shoebird.creatementhol.CreateMenthol;
import fun.shoebird.creatementhol.ModBlocks;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CoinPressScreen extends AbstractSimiScreen {
    private final ItemStack renderedItem = ModBlocks.COIN_PRESS.asStack();
    private final Identifier background = Identifier.of(CreateMenthol.MOD_ID, "textures/gui/coin_press.png");
    private final int backgroundWidth = 193, backgroundHeight = 105;
    private CoinPressBlockEntity be;

    private IconButton confirmButton;
    private TextFieldWidget currencyNameField;
    private TextFieldWidget currencySecurityCodeField;

    public CoinPressScreen(CoinPressBlockEntity be) {
        super(Text.translatable(CreateMenthol.MOD_ID + ".gui.coin_press.title"));
        this.be = be;
    }

    @Override
    protected void init() {
        setWindowSize(backgroundWidth, backgroundHeight);
        setWindowOffset(-20, 0);
        super.init();

        confirmButton = new IconButton(guiLeft + backgroundWidth - 33, guiTop + backgroundHeight - 24, AllIcons.I_CONFIRM);
        confirmButton.withCallback(this::saveAndClose);
        addDrawableChild(confirmButton);

        currencyNameField = new TextFieldWidget(textRenderer, guiLeft + 19, guiTop + 28, 147, 9, Text.translatable(CreateMenthol.MOD_ID + ".gui.coin_press.currencynamefield"));
        currencyNameField.setDrawsBackground(false);
        currencyNameField.setPlaceholder(Text.translatable(CreateMenthol.MOD_ID + ".gui.coin_press.currencynamefield").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
        currencyNameField.setText(be.currencyName);
        addSelectableChild(currencyNameField);
        addDrawableChild(currencyNameField);

        currencySecurityCodeField = new TextFieldWidget(textRenderer, guiLeft + 19, guiTop + 54, 147, 9, Text.translatable(CreateMenthol.MOD_ID + ".gui.coin_press.currencysecurityfield"));
        currencySecurityCodeField.setDrawsBackground(false);
        currencySecurityCodeField.setPlaceholder(Text.translatable(CreateMenthol.MOD_ID + ".gui.coin_press.currencysecurityfield").setStyle(Style.EMPTY.withItalic(true).withColor(Formatting.GRAY)));
        currencySecurityCodeField.setText(be.currencySecurity);
        addSelectableChild(currencySecurityCodeField);
        addDrawableChild(currencySecurityCodeField);
    }

    private void saveAndClose() {
        AllPackets.getChannel()
                .sendToServer(new ConfigureCoinPressPacket(be.getPos(), currencyNameField.getText(), currencySecurityCodeField.getText()));
        close();
    }

    @Override
    protected void renderWindow(DrawContext graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.drawTexture(background, guiLeft, guiTop, 0, 0, 208, 112);

        graphics.drawText(textRenderer, title, guiLeft + (backgroundWidth - 8) / 2 - textRenderer.getWidth(title) / 2, guiTop + 4, 0x592424, false);
        GuiGameElement.of(renderedItem).<GuiGameElement
                        .GuiRenderBuilder>at(guiLeft + backgroundWidth + 6, guiTop + backgroundHeight - 56, 100)
                .scale(5)
                .render(graphics);
    }
}
