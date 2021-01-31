package me.oxe.bloq;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class BloqModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ScreenRegistry.register(BloqMod.GATE_BLOCK_SCREEN_HANDLER, GateBlockScreen::new);
  }
}
