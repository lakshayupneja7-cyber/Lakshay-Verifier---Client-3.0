package me.lakshay.verifierclient.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record VerifyRequestPayload() implements CustomPayload {

    public static final Id<VerifyRequestPayload> ID =
            new Id<>(Identifier.of("lakshay", "verify_request"));

    public static final PacketCodec<RegistryByteBuf, VerifyRequestPayload> CODEC =
            PacketCodec.ofStatic((buf, value) -> {}, buf -> new VerifyRequestPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
