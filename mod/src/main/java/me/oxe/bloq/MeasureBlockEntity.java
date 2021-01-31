package me.oxe.bloq;

import net.minecraft.item.ItemStack;

public class MeasureBlockEntity extends GateBlockEntity {
    public MeasureBlockEntity() {
        super(BloqMod.MEASURE_BLOCK_ENTITY);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot == 0) {
        if (!this.world.isClient) {
            QuantumWatcher watcher = QuantumWatcher.getInstance();
            watcher.sendQubitData(stack);
            this.inventory.set(1, stack.copy());
            stack.decrement(1);
            markDirty();
        }
        }
    }
}
