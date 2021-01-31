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

public class GateBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {

  private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

  public GateBlockEntity(BlockEntityType<?> type) {
    super(type);
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
}
