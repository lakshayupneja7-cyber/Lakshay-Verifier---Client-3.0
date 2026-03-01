package me.lakshay.verifierclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import io.netty.buffer.Unpooled;

import java.util.stream.Collectors;

public class VerifierClient implements ClientModInitializer {

    // Legacy Networking IDs (works up to 1.20.4 style, and still compiles on many newer with legacy project)
    private static final Identifier VERIFY_REQUEST  = new Identifier("lakshay", "verify_request");
    private static final Identifier VERIFY_RESPONSE = new Identifier("lakshay", "verify_response");

    @Override
    public void onInitializeClient() {

        ClientPlayNetworking.registerGlobalReceiver(VERIFY_REQUEST, (client, handler, buf, responseSender) -> {
            // Server asked for mod list -> respond
            String mods = FabricLoader.getInstance()
                    .getAllMods()
                    .stream()
                    .map(m -> m.getMetadata().getId())
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining(","));

            PacketByteBuf out = new PacketByteBuf(Unpooled.buffer());
            out.writeString(mods, 32767);

            ClientPlayNetworking.send(VERIFY_RESPONSE, out);
        });
    }
}
