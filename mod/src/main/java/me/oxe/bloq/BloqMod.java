package me.oxe.bloq;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;

public class BloqMod implements ModInitializer {
  public static final Block X_GATE_BLOCK = new XGateBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
  public static BlockEntityType<XGateBlockEntity> X_GATE_BLOCK_ENTITY;

  @Override
  public void onInitialize() {
    System.out.println("Hello Fabric world!");
  }
}
