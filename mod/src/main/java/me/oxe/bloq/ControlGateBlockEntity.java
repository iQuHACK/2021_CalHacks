package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

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


  @Override
  public void setStack(int slot, ItemStack stack) {
    if (slot == 0) {
      if (!this.world.isClient) {
        this.world.blockEntities.forEach((block) -> {
          if (this.pos.getManhattanDistance(block.getPos()) == 1) {
            // block is adjacent, check if it is a GateBlockEntity
            if (block instanceof GateBlockEntity && !((GateBlockEntity)block).getStack(0).equals(ItemStack.EMPTY)) {
              ItemStack other_stack = ((GateBlockEntity)block).getStack(0);
              QuantumWatcher watcher = QuantumWatcher.getInstance();
              watcher.appendTwoQubitGate(stack, other_stack, "cx", 0);
            }
          }
        });
        this.inventory.set(1, stack.copy());
        stack.decrement(1);
        markDirty();
      }
    }
  }
}
