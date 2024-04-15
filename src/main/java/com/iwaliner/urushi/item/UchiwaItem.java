package com.iwaliner.urushi.item;

import com.iwaliner.urushi.block.FallenLeavesBlock;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UchiwaItem extends Item implements ElementItem {
    private final int consumeAmount=1;
    public UchiwaItem(Properties p_41383_) {
        super(p_41383_);
    }
    public int getUseDuration(ItemStack p_43107_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
       BlockPos pos=player.blockPosition();
        ItemStack magatama= ElementUtils.getMagatamaInInventory(player, ElementType.WoodElement);
        int w=3;
        if(magatama == ItemStack.EMPTY){
            return InteractionResultHolder.fail(itemstack);
        }
        if(!ElementUtils.willBeInDomain(magatama, -consumeAmount)){
            return InteractionResultHolder.fail(itemstack);
        }
        useUchiwaMethod(pos, level, player, hand, magatama, w);
        return InteractionResultHolder.consume(itemstack);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack=context.getItemInHand();
        BlockPos pos=context.getClickedPos();
        Level level=context.getLevel();
        Player player=context.getPlayer();
        InteractionHand hand=context.getHand();
        assert player != null;
        ItemStack magatama= ElementUtils.getMagatamaInInventory(player, ElementType.WoodElement);
        int w=3;
        if(magatama == ItemStack.EMPTY){
            return InteractionResult.FAIL;
        }
        if(!ElementUtils.willBeInDomain(magatama, -consumeAmount)){
            return InteractionResult.FAIL;
        }
        useUchiwaMethod(pos, level, player, hand, magatama, w);
        return InteractionResult.SUCCESS;
    }

    private void useUchiwaMethod(BlockPos pos, Level level, @NotNull Player player, InteractionHand hand,
                                 ItemStack magatama, int w) {
        player.swing(hand, true);
        for (int i = -w; i <= w; i++) {
            for (int j = -w; j <= w; j++) {
                for (int k = -w; k <= w; k++) {
                    if (level.getBlockState(pos.offset(i, j, k)).getBlock() instanceof BushBlock ||
                            level.getBlockState(pos.offset(i, j, k)).getBlock() instanceof LeavesBlock ||
                            level.getBlockState(pos.offset(i, j, k)).getBlock() instanceof FallenLeavesBlock) {

                        level.destroyBlock(pos.offset(i, j, k), true);
                        ElementUtils.increaseStoredReiryokuAmount(magatama,-consumeAmount);
                        player.getItemInHand(hand).hurtAndBreak(1, player, (x) -> {
                            x.broadcastBreakEvent(hand);
                        });
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"uchiwa");
        UrushiUtils.setInfo(list,"uchiwa2");
        UrushiUtils.setInfo(list,"uchiwa3");
    }

    @Override
    public ElementType getElementType() {
        return ElementType.WoodElement;
    }


}
