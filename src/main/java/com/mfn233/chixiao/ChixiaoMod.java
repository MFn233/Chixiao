package com.mfn233.chixiao;

import com.mfn233.chixiao.entity.ChixiaoDrive;
import com.mfn233.chixiao.proxy.CommonProxy;
import com.mfn233.chixiao.specialattack.ConsecutiveFireScheduler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,
        dependencies = "required-after:flammpfeil.slashblade")
public class ChixiaoMod {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @Mod.Instance(Tags.MOD_ID)
    public static ChixiaoMod instance;

    @SidedProxy(clientSide = "com.mfn233.chixiao.proxy.ClientProxy", serverSide = "com.mfn233.chixiao.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("{} is loading!", Tags.MOD_NAME);

        EntityRegistry.registerModEntity(new ResourceLocation(Tags.MOD_ID, "chixiao_drive"),
                ChixiaoDrive.class, "ChixiaoDrive", 11, instance, 250, 10, true);

        ChilingBlade.registerSpecialAttack();
        MinecraftForge.EVENT_BUS.register(ConsecutiveFireScheduler.getInstance());
        ChilingBlade.registerBlade();
        ChixiaoBlade.registerBlade();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();
        LOGGER.info("{} initialized!", Tags.MOD_NAME);
    }
}
