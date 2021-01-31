package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public abstract class GateBlockEntity extends LockableContainerBlockEntity implements SidedInventory, Tickable {

  private static final int[] IN_SLOT = new int[] { 0 };
  private static final int[] OUT_SLOT = new int[] { 1 };
  protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
  protected final PropertyDelegate propertyDelegate;
  // protected final PropertyDelegate propertyDelegate;
  private int loading;

  public GateBlockEntity(BlockEntityType<?> type) {
    super(type);
    this.propertyDelegate = new PropertyDelegate() {
      public int get(int index) {
        switch (index) {
          case 0:
            return GateBlockEntity.this.loading;
          default:
            return 0;
        }
      }

      public void set(int index, int value) {
        switch (index) {
          case 0:
            GateBlockEntity.this.loading = value;
            break;
        }
      }

      public int size() {
        return 1;
      }
    };
  }

  private boolean isLoading() {
    return loading == 1;
  }

  @Override
  protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
    return new GateBlockScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
  }

  public void tick() {
    boolean updated = false;
    if (!this.world.isClient) {
      ItemStack itemStack = this.inventory.get(1);

      if (!itemStack.isEmpty()) {
        Item item = itemStack.getItem();
        itemStack.decrement(1);
        if (itemStack.isEmpty()) {
          Item item2 = item.getRecipeRemainder();
          this.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
        }
      }
    }
    this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(GateBlock.LOADING, this.isLoading()), 3);

    if (updated) {
      this.markDirty();
    }

  }

  public int[] getAvailableSlots(Direction side) {
    if (side != Direction.DOWN)
      return IN_SLOT;
    return OUT_SLOT;
  }

  public boolean canInsert(int slot, ItemStack stack, Direction dir) {
    return this.isValid(slot, stack);
  }

  public boolean canExtract(int slot, ItemStack stack, Direction dir) {
    if (slot == 0)
      return false;
    return true;
  }

  @Override
  public int size() {
    return inventory.size();
  }

  public boolean isEmpty() {
    for (ItemStack stack : this.inventory) {
      if (!stack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public ItemStack getStack(int slot) {
    return this.inventory.get(slot);
  }

  public ItemStack removeStack(int slot, int amount) {
    return Inventories.splitStack(this.inventory, slot, amount);
  }

  public ItemStack removeStack(int slot) {
    return Inventories.removeStack(this.inventory, slot);
  }

  public void setStack(int slot, ItemStack stack) {
    ItemStack itemStack = this.inventory.get(slot);

    boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack)
        && ItemStack.areTagsEqual(stack, itemStack);
    this.inventory.set(slot, stack);
    if (stack.getCount() > this.getMaxCountPerStack()) {
      stack.setCount(this.getMaxCountPerStack());
    }

    if (slot == 0 && !bl) {
      this.markDirty();
    }
  }

  public boolean canPlayerUse(PlayerEntity player) {
    if (this.world.getBlockEntity(this.pos) != this) {
      return false;
    } else {
      return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
          (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }
  }

  public boolean isValid(int slot, ItemStack stack) {
    return slot == 0;
  }

  public void clear() {
    this.inventory.clear();
  }

  @Override
  public Text getDisplayName() {
    return new TranslatableText(getCachedState().getBlock().getTranslationKey());
  }

  @Override
  public void fromTag(BlockState state, CompoundTag tag) {
    super.fromTag(state, tag);
    Inventories.fromTag(tag, inventory);
  }

  @Override
  public CompoundTag toTag(CompoundTag tag) {
    Inventories.toTag(tag, inventory);
    return super.toTag(tag);
  }
}
