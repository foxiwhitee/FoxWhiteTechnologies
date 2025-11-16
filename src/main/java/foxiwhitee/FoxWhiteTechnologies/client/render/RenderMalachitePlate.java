package foxiwhitee.FoxWhiteTechnologies.client.render;

import foxiwhitee.FoxWhiteTechnologies.blocks.BlockMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.block.mana.BlockTerraPlate;
import vazkii.botania.common.block.tile.TileTerraPlate;

public class RenderMalachitePlate extends TileEntitySpecialRenderer {
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        TileMalachitePlate plate = (TileMalachitePlate)tileentity;
        float max = 50000.0F;
        float alphaMod = Math.min(max, (float)plate.getCurrentMana()) / max;
        GL11.glPushMatrix();
        GL11.glTranslated(d0, d1, d2);
        GL11.glRotated((double)90.0F, (double)1.0F, (double)0.0F, (double)0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.1885F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3008);
        float alpha = (float)((Math.sin((double)((float) ClientTickHandler.ticksInGame + f) / (double)8.0F) + (double)1.0F) / (double)5.0F + 0.6) * alphaMod;
        if (ShaderHelper.useShaders()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        } else {
            int light = 15728880;
            int lightmapX = light % 65536;
            int lightmapY = light / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lightmapX, (float)lightmapY);
            GL11.glColor4f(0.6F + (float)((Math.cos((double)((float)ClientTickHandler.ticksInGame + f) / (double)6.0F) + (double)1.0F) / (double)5.0F), 0.1F, 0.9F, alpha);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        ShaderHelper.useShader(ShaderHelper.terraPlateRune);
        this.renderIcon(0, 0, BlockMalachitePlate.overlay, 1, 1, 240);
        ShaderHelper.releaseShader();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5, int brightness) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)0.0F, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)0.0F, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)0.0F, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)0.0F, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        tessellator.draw();
    }
}
