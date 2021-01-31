package me.oxe.bloq;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ItemQubit extends Item {

    public ItemQubit(Settings settings) {
        super(settings);
        // TODO Auto-generated constructor stub
    }



    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.containsUuid("QUBIT_UUID")) {
            tag.putUuid("QUBIT_UUID", UUID.randomUUID());
        }
        tooltip.add(Text.of(tag.getUuid("QUBIT_UUID").toString()));
    }
}
