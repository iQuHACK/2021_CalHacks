package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class XGateBlockEntity extends GateBlockEntity {

  private int dummy_number = 7;

  public XGateBlockEntity() {
    super(BloqMod.X_GATE_BLOCK_ENTITY);
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
        this.inventory.set(1, stack.copy());
        stack.decrement(1);
        markDirty();
      }
    }
  }

  @Override
  protected Text getContainerName() {
    return new TranslatableText("bloq.gate.x");
  }

}
