package me.oxe.bloq;

import java.util.List;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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

  public static final Block RX_GATE_BLOCK;
  public static final BlockItem RX_GATE_BLOCK_ITEM;
  public static final BlockEntityType<RXGateBlockEntity> RX_GATE_BLOCK_ENTITY;
  private static final Identifier RX_GATE_BLOCK_IDENTIFIER = new Identifier(MOD_ID, "rx_gate_block");

  public static final Block RZ_GATE_BLOCK;
  public static final BlockItem RZ_GATE_BLOCK_ITEM;
  public static final BlockEntityType<RZGateBlockEntity> RZ_GATE_BLOCK_ENTITY;
  private static final Identifier RZ_GATE_BLOCK_IDENTIFIER = new Identifier(MOD_ID, "rz_gate_block");

  public static final Block CONTROL_GATE_BLOCK;
  public static final BlockItem CONTROL_GATE_BLOCK_ITEM;
  public static final BlockEntityType<ControlGateBlockEntity> CONTROL_GATE_BLOCK_ENTITY;
  private static final Identifier CONTROL_GATE_BLOCK_IDENTIFIER = new Identifier(MOD_ID, "control_gate_block");

  public static final Block MEASURE_BLOCK;
  public static final BlockItem MEASURE_BLOCK_ITEM;
  public static final BlockEntityType<MeasureBlockEntity> MEASURE_BLOCK_ENTITY;
  private static final Identifier MEASURE_BLOCK_IDENTIFIER = new Identifier(MOD_ID, "measure_block");

  private static ItemStack GenerateIconStack() {
    return new ItemStack(ITEM_QUBIT);
  }

  private static void GenerateGroupContents(List<ItemStack> stacks) {
    stacks.add(new ItemStack(ITEM_QUBIT));
    stacks.add(new ItemStack(X_GATE_BLOCK_ITEM));
    stacks.add(new ItemStack(RX_GATE_BLOCK_ITEM));
    stacks.add(new ItemStack(RZ_GATE_BLOCK_ITEM));
    stacks.add(new ItemStack(CONTROL_GATE_BLOCK_ITEM));
    stacks.add(new ItemStack(MEASURE_BLOCK_ITEM));
  }

  static {
    GATE_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(X_GATE_BLOCK_IDENTIFIER,
        GateBlockScreenHandler::new);
    QUANTUM_GROUP = FabricItemGroupBuilder.create(new Identifier("bloq", "quantum"))
        .icon(BloqMod::GenerateIconStack).appendItems(BloqMod::GenerateGroupContents).build();


    ITEM_QUBIT = Registry.register(Registry.ITEM, QUBIT_IDENTIFIER,
        new ItemQubit(new FabricItemSettings().maxCount(1).group(QUANTUM_GROUP)));

    X_GATE_BLOCK = Registry.register(Registry.BLOCK, X_GATE_BLOCK_IDENTIFIER,
        new XGateBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    X_GATE_BLOCK_ITEM = Registry.register(Registry.ITEM, X_GATE_BLOCK_IDENTIFIER,
        new BlockItem(X_GATE_BLOCK, new Item.Settings().group(QUANTUM_GROUP)));
    X_GATE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, X_GATE_BLOCK_IDENTIFIER,
        BlockEntityType.Builder.create(XGateBlockEntity::new, X_GATE_BLOCK).build(null));

    RX_GATE_BLOCK = Registry.register(Registry.BLOCK, RX_GATE_BLOCK_IDENTIFIER,
        new RXGateBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    RX_GATE_BLOCK_ITEM = Registry.register(Registry.ITEM, RX_GATE_BLOCK_IDENTIFIER,
        new BlockItem(RX_GATE_BLOCK, new Item.Settings().group(QUANTUM_GROUP)));
    RX_GATE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, RX_GATE_BLOCK_IDENTIFIER,
        BlockEntityType.Builder.create(RXGateBlockEntity::new, RX_GATE_BLOCK).build(null));

    RZ_GATE_BLOCK = Registry.register(Registry.BLOCK, RZ_GATE_BLOCK_IDENTIFIER,
        new RZGateBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    RZ_GATE_BLOCK_ITEM = Registry.register(Registry.ITEM, RZ_GATE_BLOCK_IDENTIFIER,
        new BlockItem(RZ_GATE_BLOCK, new Item.Settings().group(QUANTUM_GROUP)));
    RZ_GATE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, RZ_GATE_BLOCK_IDENTIFIER,
        BlockEntityType.Builder.create(RZGateBlockEntity::new, RZ_GATE_BLOCK).build(null));

    CONTROL_GATE_BLOCK = Registry.register(Registry.BLOCK, CONTROL_GATE_BLOCK_IDENTIFIER,
        new ControlGateBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    CONTROL_GATE_BLOCK_ITEM = Registry.register(Registry.ITEM, CONTROL_GATE_BLOCK_IDENTIFIER,
        new BlockItem(CONTROL_GATE_BLOCK, new Item.Settings().group(QUANTUM_GROUP)));
    CONTROL_GATE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, CONTROL_GATE_BLOCK_IDENTIFIER,
        BlockEntityType.Builder.create(ControlGateBlockEntity::new, CONTROL_GATE_BLOCK).build(null));

    MEASURE_BLOCK = Registry.register(Registry.BLOCK, MEASURE_BLOCK_IDENTIFIER, new MeasureBlock(FabricBlockSettings.copyOf(Blocks.HOPPER)));
    MEASURE_BLOCK_ITEM = Registry.register(Registry.ITEM, MEASURE_BLOCK_IDENTIFIER,
        new BlockItem(MEASURE_BLOCK, new Item.Settings().group(QUANTUM_GROUP)));
    MEASURE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "bloq:measure", BlockEntityType.Builder.create(MeasureBlockEntity::new, MEASURE_BLOCK).build(null));
  }

  @Override
  public void onInitialize() {
    System.out.println("Hello Fabric world!");
    ServerLifecycleEvents.SERVER_STARTED.register((world) -> {
        System.out.println(world.getName());
    });

  }
}
