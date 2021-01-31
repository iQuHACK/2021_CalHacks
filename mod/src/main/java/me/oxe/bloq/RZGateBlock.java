package me.oxe.bloq;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class RZGateBlock extends GateBlock {

  public RZGateBlock(Settings settings) {
    super(settings);
  }

  @Override
  public BlockEntity createBlockEntity(BlockView world) {
    return new RZGateBlockEntity();
  }

}
