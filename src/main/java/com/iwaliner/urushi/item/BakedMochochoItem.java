package com.iwaliner.urushi.item;

import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BakedMochochoItem extends Item {
    public BakedMochochoItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
       try {
           long gametime= level.getGameTime()%100;
           if (gametime<20) {
               UrushiUtils.setInfoWithColor(list, "obanyaki", ChatFormatting.WHITE);
           } else if(gametime<40){
               UrushiUtils.setInfoWithColor(list, "kaitenyaki", ChatFormatting.WHITE);
           }else if(gametime<60){
               UrushiUtils.setInfoWithColor(list, "imagawayaki", ChatFormatting.WHITE);
           }else if(gametime<80){
               UrushiUtils.setInfoWithColor(list, "oyaki", ChatFormatting.WHITE);
           }else {
               UrushiUtils.setInfoWithColor(list, "gozasourou", ChatFormatting.WHITE);
           }
       }catch (Exception e){

       }


    }
}
