package fr.krishenk.castel.server.network;

import io.netty.handler.codec.EncoderException;
import net.minecraft.network.PacketBuffer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataStream {
    public static PacketBuffer writeString(PacketBuffer buf, String string) {
        byte[] abyte = string.getBytes(StandardCharsets.UTF_8);
        if (abyte.length > 32767) {
            throw new EncoderException("String too big (was " + abyte.length + " bytes encoded, max " + 32767 + ")");
        } else {
            buf.writeShort(abyte.length);
            buf.writeBytes(abyte);
            return buf;
        }
    }
}
