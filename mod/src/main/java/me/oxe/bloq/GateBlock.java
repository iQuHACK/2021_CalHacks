package me.oxe.bloq;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class GateBlock extends BlockWithEntity {
  public static final DirectionProperty FACING;
  public static final BooleanProperty LOADING;

  static {
    FACING = HorizontalFacingBlock.FACING;
    LOADING = Properties.TRIGGERED;
  }

  public GateBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LOADING, false));
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockHitResult hit) {
    if (world.isClient) {
      return ActionResult.SUCCESS;
    } else {
      this.openScreen(world, pos, player);
      return ActionResult.CONSUME;
    }
  }

  protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof GateBlockEntity) {
      player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
      // player.incrementStat(Stats.INTERACT_WITH_FURNACE);
    }
  }

  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    if (itemStack.hasCustomName()) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof GateBlockEntity) {
        ((GateBlockEntity) blockEntity).setCustomName(itemStack.getName());
      }
    }
  }

  // This method will drop all items onto the ground when the block is broken
  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    if (!state.isOf(newState.getBlock())) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof GateBlockEntity) {
        ItemScatterer.spawn(world, pos, (GateBlockEntity) blockEntity);
        // update comparators
        world.updateComparators(pos, this);
      }
      super.onStateReplaced(state, world, pos, newState, moved);
    }
  }

  public boolean hasComparatorOutput(BlockState state) {
    return true;
  }

  public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
    return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, LOADING);
  }
}
