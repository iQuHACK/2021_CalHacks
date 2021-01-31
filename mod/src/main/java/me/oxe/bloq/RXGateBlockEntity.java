package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class RXGateBlockEntity extends GateBlockEntity {

  private int dummy_number = 7;

  public RXGateBlockEntity() {
    super(BloqMod.RX_GATE_BLOCK_ENTITY);
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

  @Override
  public void setStack(int slot, ItemStack stack) {
    if (slot == 0) {
      if (!this.world.isClient) {
        QuantumWatcher watcher = QuantumWatcher.getInstance();
        watcher.appendQubitGate(stack, "rx", getParameter());
        this.inventory.set(1, stack.copy());
        stack.decrement(1);
        markDirty();
      }
    }
  }


  public int getParameter() { // @ETHAN Use this value as the parameter for the added X gate
   return world.getReceivedRedstonePower(this.pos) + 1;
  }

}
