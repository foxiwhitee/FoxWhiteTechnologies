package foxiwhitee.FoxWhiteTechnologies.client.render.mechanic;

import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderMechanicRuneAltar extends TileEntitySpecialRendererObjWrapper<TileMechanicRuneAltar> implements IItemRenderer {
    private final IModelCustom model;

    public RenderMechanicRuneAltar() {
        super(TileMechanicRuneAltar.class, new ResourceLocation(FoxWTCore.MODID, "models/mechanicRuneAltar.obj"), new ResourceLocation(FoxWTCore.MODID, "textures/blocks/mechanic/mechanicRuneAltar.png"));
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxWTCore.MODID, "models/mechanicRuneAltar.obj"));
        createList("Body");
        createList("Crystal");
    }

    public void renderAt(TileMechanicRuneAltar tileEntity, double x, double y, double z, double f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        bindTexture();
        renderPart("Body");
        GL11.glPushMatrix();
        double time = (tileEntity.getWorldObj().getTotalWorldTime() + f) / 10.0D;
        GL11.glTranslated(0.0D, Math.cos(time) * 0.1D, 0.0D);
        GL11.glRotated(-((tileEntity.getWorldObj().getTotalWorldTime() * 4L % 360L)), 0.0D, 1.0D, 0.0D);
        renderPart("Crystal");
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(0.0F, -0.5F, 0.0F);
        GL11.glScaled(1.0F, 1.0F, 1.0F);
        switch (type) {
            case ENTITY:
                GL11.glScaled(1.35, 1.35, 1.35);
                GL11.glTranslated(0, 0, 0);
                break;
            case EQUIPPED, EQUIPPED_FIRST_PERSON:
                GL11.glScaled(1, 1, 1);
                GL11.glTranslated(0.5, 0.5, 0.5);
                break;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(this.getTexture());
        this.model.renderAll();
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
