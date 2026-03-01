package me.lakshay.verifierclient.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record VerifyResponsePayload(String mods) implements CustomPayload {

    public static final Id<VerifyResponsePayload> ID =
            new Id<>(Identifier.of("lakshay", "verify_response"));

    public static final PacketCodec<RegistryByteBuf, VerifyResponsePayload> CODEC =
            PacketCodec.ofStatic(
                    (buf, value) -> buf.writeString(value.mods(), 32767),
                    buf -> new VerifyResponsePayload(buf.readString(32767))
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
