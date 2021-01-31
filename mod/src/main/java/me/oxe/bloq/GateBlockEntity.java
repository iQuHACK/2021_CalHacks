package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public abstract class GateBlockEntity extends BlockEntity implements GateBlockInventory, NamedScreenHandlerFactory {

  protected final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
  // protected final PropertyDelegate propertyDelegate;
  private int loading;

  public GateBlockEntity(BlockEntityType<?> type) {
    super(type);
    // this.propertyDelegate = new PropertyDelegate() {
    // public int get(int index) {
    // switch (index) {
    // case 0:
    // return GateBlockEntity.this.loading;
    // default:
    // return 0;
    // }
    // }

    // public void set(int index, int value) {
    // switch (index) {
    // case 0:
    // GateBlockEntity.this.loading = value;
    // break;
    // }
    // }

    // public int size() {
    // return 1;
    // }
    // };
  }

  @Override
  public DefaultedList<ItemStack> getItems() {
    return inventory;
  }

  @Override
  public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
    // We provide *this* to the screenHandler as our class Implements Inventory
    // Only the Server has the Inventory at the start, this will be synced to the
    // client in the ScreenHandler
    return new GateBlockScreenHandler(syncId, inv, this);
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

  public int[] getAvailableSlots(Direction side) {
    if (side != Direction.DOWN)
      return new int[] { 0 };
    return new int[] { 1 };
  }

  public boolean canPlayerUse(PlayerEntity player) {
    if (loading == 1)
      return false;
    return GateBlockInventory.super.canPlayerUse(player);
  }

  public boolean canInsert(int slot, ItemStack stack, Direction dir) {
    if (slot == 1)
      return false;
    return true;
  }

  public boolean canExtract(int slot, ItemStack stack, Direction dir) {
    if (slot == 0)
      return false;
    return true;
  }

  @Override
  public ItemStack getStack(int slot) {
    // TODO Auto-generated method stub
    return GateBlockInventory.super.getStack(slot);
  }
}
