package com.mfn233.chixiao;

import com.mfn233.chixiao.specialattack.JueYing;
import com.mfn233.chixiao.specialattack.LieFengShuang;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ChilingBlade {

    public static final String NAME = Tags.BLADE_KEY_CHILING;

    public static void registerSpecialAttack() {
        if (!ItemSlashBlade.specialAttacks.containsKey(LieFengShuang.SA_TYPE)) {
            ItemSlashBlade.specialAttacks.put(LieFengShuang.SA_TYPE, new LieFengShuang());
        }
        if (!ItemSlashBlade.specialAttacks.containsKey(JueYing.SA_TYPE)) {
            ItemSlashBlade.specialAttacks.put(JueYing.SA_TYPE, new JueYing());
        }
    }

    public static void registerBlade() {
        SlashBlade.InitEventBus.register(new ChilingBlade());
    }

    @SubscribeEvent
    public void init(LoadEvent.InitEvent event) {
        ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

        ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
        ItemSlashBladeNamed.TrueItemName.set(tag, NAME);
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 72);

        ItemSlashBlade.setBaseAttackModifier(tag, 4.0f + Item.ToolMaterial.DIAMOND.getAttackDamage());
        ItemSlashBlade.TextureName.set(tag, "chiling/chiling");
        ItemSlashBlade.ModelName.set(tag, "chiling/chiling");
        ItemSlashBlade.SpecialAttackType.set(tag, LieFengShuang.SA_TYPE);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);

        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

        blade.addEnchantment(Enchantments.UNBREAKING, 3);
        blade.addEnchantment(Enchantments.SHARPNESS, 5);
        blade.addEnchantment(Enchantments.LOOTING, 2);

        SlashBlade.registerCustomItemStack(NAME, blade);
        ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + NAME);
    }

    @SubscribeEvent
    public void postInit(LoadEvent.PostInitEvent event) {
        ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid, NAME);
        ItemStack soul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.ProudSoulStr, 1);
        ItemStack ingotSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
        ItemStack sphereSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
        ItemStack tinySoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.TinyBladeSoulStr, 1);

        SlashBlade.addRecipe(NAME, new ShapedOreRecipe(
                new ResourceLocation(Tags.MOD_ID, "chiling"),
                blade,
                "ABC",
                "DEF",
                "GHI",
                'A', new ItemStack(Blocks.PACKED_ICE),
                'B', ingotSoul,
                'C', "blockIron",
                'D', sphereSoul,
                'E', "blockIron",
                'F', soul,
                'G', new ItemStack(Items.IRON_SWORD),
                'H', tinySoul,
                'I', "gemDiamond"));
    }
}
