package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;

public class ControlGateBlockEntity extends GateBlockEntity {

  private int dummy_number = 7;

  public ControlGateBlockEntity() {
    super(BloqMod.CONTROL_GATE_BLOCK_ENTITY);
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    tag.putInt("dummy_number", dummy_number);
    return super.toTag(tag);
  }

  // Deserialize the BlockEntity
  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    dummy_number = tag.getInt("dummy_number");
  }

}
