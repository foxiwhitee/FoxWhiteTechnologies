package foxiwhitee.FoxWhiteTechnologies.client.render.mechanic;

import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderMechanicPetals extends TileEntitySpecialRendererObjWrapper<TileMechanicPetals> implements IItemRenderer {
    private final IModelCustom model;

    public RenderMechanicPetals() {
        super(TileMechanicPetals.class, new ResourceLocation(FoxWTCore.MODID, "models/mechanicPetals.obj"), new ResourceLocation(FoxWTCore.MODID, "textures/blocks/mechanic/mechanicPetals.png"));
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxWTCore.MODID, "models/mechanicPetals.obj"));
        createList("all");
        //createList("body");
        //createList("water");
    }

    public void renderAt(TileMechanicPetals tileEntity, double x, double y, double z, double f) {
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        bindTexture();
        //renderPart("body");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        renderPart("all");
        //renderPart("water");
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glTranslated(-x - 0.5, -y, -z - 0.5);
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
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
