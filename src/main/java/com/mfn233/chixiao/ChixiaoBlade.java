package com.mfn233.chixiao;

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

    /** 觉醒所需的击杀数与耀魂。RecipeAwakeBlade 对这两个字段都是"大于等于"比较。 */
    public static final int REQUIRED_KILLS = 2000;
    public static final int REQUIRED_PROUD_SOUL = 5000;

    public static void registerBlade() {
        SlashBlade.InitEventBus.register(new ChixiaoBlade());
    }

    @SubscribeEvent
    public void init(LoadEvent.InitEvent event) {
        ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);

        ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
        ItemSlashBladeNamed.TrueItemName.set(tag, NAME);
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 90);

        // 赤霄比赤零高一档：基础攻击 +1
        ItemSlashBlade.setBaseAttackModifier(tag, 5.0f + Item.ToolMaterial.DIAMOND.getAttackDamage());
        ItemSlashBlade.TextureName.set(tag, "chixiao/chixiao");
        ItemSlashBlade.ModelName.set(tag, "chixiao/chixiao");
        ItemSlashBlade.SpecialAttackType.set(tag, SaRegistry.JUE_YING);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);

        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

        blade.addEnchantment(Enchantments.UNBREAKING, 3);
        blade.addEnchantment(Enchantments.SHARPNESS, 6);
        blade.addEnchantment(Enchantments.LOOTING, 3);
        blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);

        SlashBlade.registerCustomItemStack(NAME, blade);
        ItemSlashBladeNamed.NamedBlades.add(SlashBlade.modid + ":" + NAME);
    }

    @SubscribeEvent
    public void postInit(LoadEvent.PostInitEvent event) {
        ItemStack blade = SlashBlade.getCustomBlade(SlashBlade.modid, NAME);
        ItemStack ingotSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
        ItemStack sphereSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);

        ItemStack chilingReq = SlashBlade.getCustomBlade(SlashBlade.modid, ChilingBlade.NAME);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(chilingReq);
        ItemSlashBlade.KillCount.set(reqTag, REQUIRED_KILLS);
        ItemSlashBlade.ProudSoul.set(reqTag, REQUIRED_PROUD_SOUL);

        // 赤霄是"后期目标"：在赤零（100 击杀）之上再叠 2 颗耀魂宝珠、2 块耀魂铁锭、
        // 1 个钻石块、2 根烈焰棒与 1 个红石块。
        SlashBlade.addRecipe(NAME, new RecipeAwakeBlade(
                new ResourceLocation(Tags.MOD_ID, "chixiao"),
                blade,
                chilingReq,
                "ABA",
                "CDC",
                "EFE",
                'A', sphereSoul,
                'B', "blockRedstone",
                'C', ingotSoul,
                'D', chilingReq,
                'E', new ItemStack(Items.BLAZE_ROD),
                'F', "blockDiamond"));
    }
}
