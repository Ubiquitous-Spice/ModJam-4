package com.github.ubiquitousspice.bloodstains.client;

import com.github.ubiquitousspice.bloodstains.StainManager;
import com.github.ubiquitousspice.bloodstains.data.BloodStain;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class StainRenderer
{
    private static final double RADIUS = .7d;
    private static final double HEIGHT = .2d;

    @SubscribeEvent
    public void renderEvent(RenderWorldLastEvent e)
    {
        Tessellator tess = Tessellator.instance;
        //Tessellator.renderingWorldRenderer = true;
        
//        /LogManager.getLogger().info("CAlling!!!!!!!!! ");

        for (BloodStain stain : StainManager.getStains())
        {
            if (Minecraft.getMinecraft().theWorld.provider.dimensionId != stain.dimId)
                continue; // screw dat
            
            
            // SETUP
            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glTranslated(stain.x - RenderManager.renderPosX, stain.y - RenderManager.renderPosY, stain.z - RenderManager.renderPosZ);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glColor3f(255, 0, 0);

            // RENMDER
            renderBox(tess, RADIUS, HEIGHT);
            
            // CLEANUP
            //Tessellator.renderingWorldRenderer = false;
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }

    }

    private void renderBox(Tessellator tess, double radius, double height)
    {
        tess.startDrawingQuads();
        double halfH = height / 2;

        // bottom quad.
        tess.addVertex(-radius, -halfH, -radius);
        tess.addVertex(radius, -halfH, -radius);
        tess.addVertex(radius, -halfH, radius);
        tess.addVertex(-radius, -halfH, radius);

        // top quad
        tess.addVertex(-radius, halfH, -radius);
        tess.addVertex(-radius, halfH, radius);
        tess.addVertex(radius, halfH, radius);
        tess.addVertex(radius, halfH, -radius);

        // front
        tess.addVertex(radius, -halfH, -radius);
        tess.addVertex(radius, halfH, -radius);
        tess.addVertex(radius, halfH, radius);
        tess.addVertex(radius, -halfH, radius);

        // back
        tess.addVertex(-radius, -halfH, -radius);
        tess.addVertex(-radius, -halfH, radius);
        tess.addVertex(-radius, halfH, radius);
        tess.addVertex(-radius, halfH, -radius);

        // left
        tess.addVertex(-radius, -halfH, radius);
        tess.addVertex(radius, -halfH, radius);
        tess.addVertex(radius, halfH, radius);
        tess.addVertex(-radius, halfH, radius);

        // right
        tess.addVertex(-radius, -halfH, -radius);
        tess.addVertex(-radius, halfH, -radius);
        tess.addVertex(radius, halfH, -radius);
        tess.addVertex(radius, -halfH, -radius);
        
        tess.draw();
    }
}
