package foxiwhitee.FoxWhiteTechnologies.client.render;

import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileCustomManaPool;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.mana.IPoolOverlayProvider;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.common.block.mana.BlockPool;

public class RenderCustomManaPool<T extends TileCustomManaPool> extends TileEntitySpecialRendererObjWrapper<T> implements IItemRenderer {
    private final IModelCustom model;

    public RenderCustomManaPool(Class<T> tile, String texture) {
        super(tile, new ResourceLocation(FoxWTCore.MODID, "models/pool.obj"), new ResourceLocation(FoxWTCore.MODID, texture));
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxWTCore.MODID, "models/pool.obj"));
        this.createList("all");
    }

    public void renderAt(T tile, double x, double y, double z, double f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        this.bindTexture();
        GL11.glTranslated(0.5, 0.0, 0.5);
        this.renderPart("all");
        GL11.glPopMatrix();
        renderOverlay(tile, f);
        renderMana(tile);
        GL11.glPopMatrix();
    }

    private void renderOverlay(T pool, double partialTicks) {
        World world = pool.getWorldObj();
        if (world == null) return;

        Block below = world.getBlock(pool.xCoord, pool.yCoord - 1, pool.zCoord);
        if (!(below instanceof IPoolOverlayProvider)) return;

        IIcon overlay = ((IPoolOverlayProvider) below)
            .getIcon(world, pool.xCoord, pool.yCoord - 1, pool.zCoord);
        if (overlay == null) return;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(
            1F, 1F, 1F,
            (float) ((Math.sin((ClientTickHandler.ticksInGame + partialTicks) / 20F) + 1) * 0.3 + 0.2)
        );

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        GL11.glTranslatef(0.0F, 0.001F, 0.0F);
        GL11.glScalef(1F, 1F, 1F);

        renderIcon(overlay, 1.0);

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderMana(T pool) {
        int mana = pool.getCurrentMana();
        int cap = pool.getMaxMana();

        if (cap <= 0) return;

        float waterLevel = (float) mana / (float) cap * 0.40F;

        if (waterLevel <= 0) return;

        float s = 0.0546875F;
        float v = 0.125F;
        float w = -v * 3.5F;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(1F, 1F, 1F, 1F);

        GL11.glTranslatef(
            0.5F + w,
            0.07F + waterLevel,
            0.5F + w
        );

        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glScalef(s, s, s);


        ShaderHelper.useShader(ShaderHelper.manaPool);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        renderIcon(BlockPool.manaIcon, 16.0);
        ShaderHelper.releaseShader();

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void renderIcon(IIcon icon, double size) {
        Tessellator t = Tessellator.instance;
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        t.startDrawingQuads();

        t.addVertexWithUV(0, size, 0, minU, maxV);
        t.addVertexWithUV(size, size, 0, maxU, maxV);
        t.addVertexWithUV(size, 0, 0, maxU, minV);
        t.addVertexWithUV(0, 0, 0, minU, minV);

        t.draw();
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

