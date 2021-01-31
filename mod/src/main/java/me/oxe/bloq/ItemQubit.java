package me.oxe.bloq;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemQubit extends Item {

    public ItemQubit(Settings settings) {
        super(settings);
        // leedle loloo
    }



    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tooltip.add(tag.toText());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack currentStack = playerEntity.getStackInHand(hand);

        QuantumWatcher.appendQubitGate(currentStack, "X", 14);

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
