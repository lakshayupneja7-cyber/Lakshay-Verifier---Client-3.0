package me.lakshay.verifierclient;

import me.lakshay.verifierclient.payload.VerifyRequestPayload;
import me.lakshay.verifierclient.payload.VerifyResponsePayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

import java.util.stream.Collectors;

public class VerifierClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register payload receiver
        ClientPlayNetworking.registerGlobalReceiver(VerifyRequestPayload.ID, (payload, context) -> {
            String mods = FabricLoader.getInstance()
                    .getAllMods()
                    .stream()
                    .map(m -> m.getMetadata().getId())
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining(","));

            context.client().execute(() -> {
                ClientPlayNetworking.send(new VerifyResponsePayload(mods));
            });
        });
    }
}
