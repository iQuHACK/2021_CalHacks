package me.oxe.bloq;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class ControlGateBlock extends GateBlock {
  public static final DirectionProperty FACING;

  public ControlGateBlock(Settings settings) {
    super(settings);
    this.setDefaultState((BlockState)this.stateManager.getDefaultState().with(FACING, Direction.DOWN));
  }

  @Override
  public BlockEntity createBlockEntity(BlockView world) {
    return new ControlGateBlockEntity();
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
    stateManager.add(FACING);
  }

  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
  }

  static {
    FACING = FacingBlock.FACING;
  }

}
