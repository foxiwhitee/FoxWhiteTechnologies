package foxiwhitee.FoxWhiteTechnologies.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileCustomSpreader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.model.ModelSpreader;
import vazkii.botania.client.render.item.RenderLens;

import java.awt.*;
import java.util.Random;

public class RenderCustomSpreader<T extends TileCustomSpreader>  extends TileEntitySpecialRendererObjWrapper<T> implements IItemRenderer {
    private final IModelCustom model;

    public RenderCustomSpreader(Class<T> tile, String texture) {
        super(tile,
            new ResourceLocation(FoxWTCore.MODID, "models/spreader.obj"),
            new ResourceLocation(FoxWTCore.MODID, texture));
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxWTCore.MODID, "models/spreader.obj"));
        createList("body");
        createList("cube");
    }

    @Override
    public void renderAt(T spreader, double x, double y, double z, double partialTicks) {
        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
        GL11.glScalef(1f, -1f, -1f);

        float modelOffsetY = -0.5f;

        GL11.glTranslatef(0, -modelOffsetY, 0);
        GL11.glRotatef(90 - spreader.rotationX, 0, 1, 0);
        float f = spreader.rotationY;
        GL11.glRotatef(-f, 1, 0, 0);
        GL11.glTranslatef(0, +modelOffsetY, 0);

        double time = ClientTickHandler.ticksInGame + partialTicks;

        bindTexture();
        renderPart("body");

        GL11.glPushMatrix();
        double worldTicks = time;

        GL11.glRotatef((float) (worldTicks % 360), 0, 1, 0);
        GL11.glTranslatef(0, (float) Math.sin(worldTicks / 20.0) * 0.05f, 0);

        renderPart("cube");
        GL11.glPopMatrix();

        GL11.glColor3f(1f, 1f, 1f);

        ItemStack lensStack = spreader.getStackInSlot(0);
        if (lensStack != null && lensStack.getItem() instanceof ILens lens) {

            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);

            GL11.glTranslatef(-0.4f, 0.1f, -0.4375f);
            GL11.glScalef(0.8f, 0.8f, 0.8f);

            RenderLens.render(lensStack, lens.getLensColor(lensStack));

            GL11.glPopMatrix();
        }

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
        GL11.glRotatef(-90, 0, 1, 0);
        GL11.glScaled(1.0F, 1.0F, 1.0F);
        switch (type) {
            case ENTITY:
                GL11.glScaled(1, 1, 1);
                GL11.glTranslated(0, 0, 0);
                break;
            case EQUIPPED:
                GL11.glScaled(1, 1, 1);
                GL11.glTranslated(0.5, 0.5, -0.5);
                break;
            case EQUIPPED_FIRST_PERSON:
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
