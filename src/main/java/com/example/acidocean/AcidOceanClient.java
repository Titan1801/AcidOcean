package com.example.acidocean;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class AcidOceanClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(AcidPayload.ID, (payload, context) -> {
            AcidState.clientAcid = payload.acid();
            // Re-draw all chunks so the water colour updates immediately
            context.client().execute(() -> {
                if (context.client().worldRenderer != null) {
                    context.client().worldRenderer.reload();
                }
            });
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> AcidState.clientAcid = false);
    }
}
