package me.oxe.bloq;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BloqMod implements ModInitializer {
  public static final Block X_GATE_BLOCK = new XGateBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
  public static BlockEntityType<XGateBlockEntity> X_GATE_BLOCK_ENTITY;
  public static final ItemQubit ITEM_QUBIT = new ItemQubit(new FabricItemSettings().maxCount(1));
  public static final ItemGroup QUANTUM_GROUP = FabricItemGroupBuilder.create(new Identifier("bloq","quantum")).icon(() -> new ItemStack(ITEM_QUBIT))
  .appendItems(stacks -> {
    stacks.add(new ItemStack(ITEM_QUBIT));
  }).build();

  @Override
  public void onInitialize() {
    System.out.println("|Hello> + |Goodbye> World!");
    Registry.register(Registry.ITEM, new Identifier("bloq", "qubit"), ITEM_QUBIT);
  }
}
