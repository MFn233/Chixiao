package com.mfn233.chixiao;

import com.mfn233.chixiao.specialattack.JueYing;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChixiaoBlade {

    public static final String NAME = Tags.BLADE_KEY_CHIXIAO;

    public static void registerBlade() {
        SlashBlade.InitEventBus.register(new ChixiaoBlade());
    }

    @SubscribeEvent
    public void init(LoadEvent.InitEvent event) {
        ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

        ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
        ItemSlashBladeNamed.TrueItemName.set(tag, NAME);
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 72);

        ItemSlashBlade.setBaseAttackModifier(tag, 4.0f + Item.ToolMaterial.DIAMOND.getAttackDamage());
        ItemSlashBlade.TextureName.set(tag, "chixiao/chixiao");
        ItemSlashBlade.ModelName.set(tag, "chixiao/chixiao");
        ItemSlashBlade.SpecialAttackType.set(tag, JueYing.SA_TYPE);
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
        ItemStack ingotSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
        ItemStack sphereSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);

        ItemStack chilingReq = SlashBlade.getCustomBlade(SlashBlade.modid, ChilingBlade.NAME);
        ItemSlashBlade.KillCount.set(ItemSlashBlade.getItemTagCompound(chilingReq), 100);

        SlashBlade.addRecipe(NAME, new RecipeAwakeBlade(
                new ResourceLocation(Tags.MOD_ID, "chixiao"),
                blade,
                chilingReq,
                "ABA",
                "CDE",
                "FGF",
                'A', ingotSoul,
                'B', sphereSoul,
                'C', "blockCoal",
                'D', chilingReq,
                'E', "blockRedstone",
                'F', new ItemStack(Items.BLAZE_ROD),
                'G', new ItemStack(Items.FLINT)));
    }
}
