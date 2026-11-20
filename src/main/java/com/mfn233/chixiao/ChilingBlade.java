package com.mfn233.chixiao;

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

    /** 由 SaRegistry 统一分配 id 并注册（旧实现会静默跳过冲突，见 SaRegistry 的注释）。 */
    public static void registerSpecialAttack() {
        SaRegistry.registerAll();
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
        ItemSlashBlade.SpecialAttackType.set(tag, SaRegistry.LIE_FENG_SHUANG);
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

        // 赤零是"中期目标"：4 颗耀魂宝珠、2 块耀魂铁锭、1 个钻石块，外加一把铁剑作刀胚、浮冰作"冽"的意象。
        SlashBlade.addRecipe(NAME, new ShapedOreRecipe(
                new ResourceLocation(Tags.MOD_ID, "chiling"),
                blade,
                "ABA",
                "CDC",
                "AEA",
                'A', sphereSoul,
                'B', "blockDiamond",
                'C', ingotSoul,
                'D', new ItemStack(Items.IRON_SWORD),
                'E', new ItemStack(Blocks.PACKED_ICE)));
    }
}
