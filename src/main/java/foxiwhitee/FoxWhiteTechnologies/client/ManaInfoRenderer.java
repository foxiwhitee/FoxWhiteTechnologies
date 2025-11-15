package foxiwhitee.FoxWhiteTechnologies.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.blocks.BlockCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicManaBlock;
import foxiwhitee.FoxWhiteTechnologies.util.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import vazkii.botania.common.block.tile.mana.TilePool;

import java.awt.*;
import java.util.HashMap;

public class ManaInfoRenderer {
    public int ticks = 0;

    public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0.0D, y + height, zLevel, 0.0D, 1.0D);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, 1.0D, 1.0D);
        tessellator.addVertexWithUV(x + width, y + 0.0D, zLevel, 1.0D, 0.0D);
        tessellator.addVertexWithUV(x + 0.0D, y + 0.0D, zLevel, 0.0D, 0.0D);
        tessellator.draw();
    }

    public static HashMap<Integer, String> name = new HashMap<>();

    static {
        name.put(0, LocalizationUtils.localize("tile.botania:pool0"));
        name.put(1, LocalizationUtils.localize("tile.botania:pool1"));
        name.put(2, LocalizationUtils.localize("tile.botania:pool2"));
    }

    public static final ResourceLocation info_texture = new ResourceLocation(FoxWTCore.MODID.toLowerCase(), "textures/gui/info.png");

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityClientPlayerMP thePlayer = minecraft.thePlayer;
        double n = thePlayer.lastTickPosX + (thePlayer.posX - thePlayer.lastTickPosX) * renderWorldLastEvent.partialTicks;
        double n2 = thePlayer.lastTickPosY + (thePlayer.posY - thePlayer.lastTickPosY) * renderWorldLastEvent.partialTicks;
        double n3 = thePlayer.lastTickPosZ + (thePlayer.posZ - thePlayer.lastTickPosZ) * renderWorldLastEvent.partialTicks;
        GL11.glPushMatrix();
        GL11.glTranslated(-n, -n2, -n3);
        MovingObjectPosition objectMouseOver = minecraft.objectMouseOver;
        if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos loc = new BlockPos((World)minecraft.theWorld, objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
            TileEntity tileEntity = minecraft.theWorld.getTileEntity(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
            if (Loader.isModLoaded("Botania") && tileEntity instanceof TilePool) {
                String name = startRender(loc, tileEntity, 0.3);
                if (tileEntity.getWorldObj().getBlock(loc.getX(), loc.getY(), loc.getZ()) instanceof vazkii.botania.common.block.mana.BlockPool)
                    name = switch (tileEntity.getBlockMetadata()) {
                        case 0 -> LocalizationUtils.localize("tile.botania:pool0.name");
                        case 1 -> LocalizationUtils.localize("tile.botania:pool1.name");
                        case 2 -> LocalizationUtils.localize("tile.botania:pool2.name");
                        case 3 -> LocalizationUtils.localize("tile.botania:pool3.name");
                        default -> name;
                    };
                if (tileEntity.getWorldObj().getBlock(loc.getX(), loc.getY(), loc.getZ()) instanceof BlockCustomManaPool)
                    GL11.glTranslated(-12.0D, 0.0D, 0.0D);
                int manaStorage = ((TilePool)tileEntity).manaCap;
                int currentMana = ((TilePool)tileEntity).getCurrentMana();
                endRender(minecraft, manaStorage, currentMana, "§6" + name, name);
            } else if (tileEntity instanceof TileMechanicManaBlock<?>) {
                String name = startRender(loc, tileEntity, 0.65);
                int manaStorage = ((TileMechanicManaBlock<?>)tileEntity).getMaxMana();
                int currentMana = ((TileMechanicManaBlock<?>)tileEntity).getCurrentMana();
                endRender(minecraft, manaStorage, currentMana, "§6"+ name, name);
            }
        }
        GL11.glPopMatrix();
    }

    private void endRender(Minecraft minecraft, int manaStorage, int currentMana, String s, String name) {
        String mana = "§7Mana: §6"+ currentMana + "/" + manaStorage;
        minecraft.fontRenderer.drawString(s, -45, 0, 245);
        minecraft.fontRenderer.drawString(mana, -45, 10, Color.ORANGE.getRGB());
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    private String startRender(BlockPos loc, TileEntity tileEntity, double y) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(false);
        GL11.glTranslated((loc.getX() + 0.5F), (loc.getY() + y), (loc.getZ() + 0.5F));
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F + (Minecraft.getMinecraft()).thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glTranslated(-0.05D, -0.75D, 0.0D);
        Minecraft.getMinecraft().getTextureManager().bindTexture(info_texture);
        drawTexturedQuadFit(-0.6D, -0.03D, 1.4D, 0.3D, 0.0D);
        GL11.glScalef(0.008F, 0.008F, 0.008F);
        GL11.glTranslatef(-12.0F, 6.0F, 0.0F);
        return LocalizationUtils.localize(tileEntity.getWorldObj().getBlock(loc.getX(), loc.getY(), loc.getZ()).getUnlocalizedName() + ".name");
    }
}
