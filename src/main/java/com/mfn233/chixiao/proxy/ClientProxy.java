package com.mfn233.chixiao.proxy;

import com.mfn233.chixiao.client.TooltipHandler;
import com.mfn233.chixiao.client.renderer.RenderChiXiaoDrive;
import com.mfn233.chixiao.entity.ChixiaoDrive;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        MinecraftForge.EVENT_BUS.register(new TooltipHandler());
        RenderingRegistry.registerEntityRenderingHandler(ChixiaoDrive.class, new IRenderFactory<ChixiaoDrive>() {
            @Override
            public Render<? super ChixiaoDrive> createRenderFor(RenderManager manager) {
                return new RenderChiXiaoDrive(manager);
            }
        });
    }
}
