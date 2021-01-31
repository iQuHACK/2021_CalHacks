package me.oxe.bloq;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MeasureBlock extends GateBlock {
    public MeasureBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new MeasureBlockEntity();
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
     }
  
    @Override
     public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
         BlockEntity localEntity = world.getBlockEntity(pos);
         if (localEntity instanceof MeasureBlockEntity) {
             MeasureBlockEntity measureEntity = (MeasureBlockEntity) localEntity;
             return measureEntity.redstoneOutput();
         }
        return 0;
     }
}
