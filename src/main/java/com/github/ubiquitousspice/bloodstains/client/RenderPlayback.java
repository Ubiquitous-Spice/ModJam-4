package com.github.ubiquitousspice.bloodstains.client;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;

public class RenderPlayback extends RenderPlayer
{
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9)
    {
		super.doRender(par1AbstractClientPlayer, par2, par4, par6, par8, par9);
	}
}
