package fun.shoebird.creatementhol.block.coinpress;

import com.simibubi.create.AllPackets;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import fun.shoebird.creatementhol.CreateMenthol;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ConfigureCoinPressPacket extends BlockEntityConfigurationPacket<CoinPressBlockEntity> {
    private String currencyName;
    private String currencySecurity;

    public static void register() {
        AllPackets.getChannel().registerC2SPacket(ConfigureCoinPressPacket.class, 1000, ConfigureCoinPressPacket::new);
    }

    public ConfigureCoinPressPacket(BlockPos pos, String currencyName, String currencySecurity) {
        super(pos);

        this.currencyName = currencyName;
        this.currencySecurity = currencySecurity;
    }

    public ConfigureCoinPressPacket(PacketByteBuf buffer) {
        super(buffer);
    }

    @Override
    protected void writeSettings(PacketByteBuf buffer) {
        buffer.writeString(currencyName);
        buffer.writeString(currencySecurity);
    }

    @Override
    protected void readSettings(PacketByteBuf buffer) {
        currencyName = buffer.readString();
        currencySecurity = buffer.readString();
    }

    @Override
    protected void applySettings(CoinPressBlockEntity coinpressBlockEntity) {
        coinpressBlockEntity.setCurrency(currencyName, currencySecurity);
    }
}
