package me.oxe.bloq;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class MeasureBlock extends GateBlock {
    public MeasureBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new MeasureBlockEntity();
    }
}
