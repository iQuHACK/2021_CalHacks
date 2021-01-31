package me.oxe.bloq;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BloqMod implements ModInitializer {
  public static final String MOD_ID = "bloq";

  public static final ScreenHandlerType<GateBlockScreenHandler> GATE_BLOCK_SCREEN_HANDLER;

  public static final ItemGroup QUANTUM_GROUP;

  public static final ItemQubit ITEM_QUBIT;
  private static final Identifier QUBIT_IDENTIFIER = new Identifier("bloq", "qubit");

  public static final Block X_GATE_BLOCK;
  public static final BlockItem X_GATE_BLOCK_ITEM;
  public static final BlockEntityType<XGateBlockEntity> X_GATE_BLOCK_ENTITY;
  private static final Identifier X_GATE_BLOCK_IDENTIFIER = new Identifier(MOD_ID, "x_gate_block");

  static {
    GATE_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(X_GATE_BLOCK_IDENTIFIER,
        GateBlockScreenHandler::new);

    ITEM_QUBIT = Registry.register(Registry.ITEM, QUBIT_IDENTIFIER,
        new ItemQubit(new FabricItemSettings().maxCount(1)));

    QUANTUM_GROUP = FabricItemGroupBuilder.create(new Identifier("bloq", "quantum"))
        .icon(() -> new ItemStack(ITEM_QUBIT)).appendItems(stacks -> {
          stacks.add(new ItemStack(ITEM_QUBIT));
        }).build();

    X_GATE_BLOCK = Registry.register(Registry.BLOCK, X_GATE_BLOCK_IDENTIFIER,
        new XGateBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    X_GATE_BLOCK_ITEM = Registry.register(Registry.ITEM, X_GATE_BLOCK_IDENTIFIER,
        new BlockItem(X_GATE_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
    X_GATE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, X_GATE_BLOCK_IDENTIFIER,
        BlockEntityType.Builder.create(XGateBlockEntity::new, X_GATE_BLOCK).build(null));

  }

  @Override
  public void onInitialize() {
    System.out.println("Hello Fabric world!");
  }
}
