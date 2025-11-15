package foxiwhitee.FoxWhiteTechnologies.blocks.mechanic;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.gui.GuiMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(index = 1, tile = TileMechanicRuneAltar.class, gui = GuiMechanicRuneAltar.class, container = ContainerMechanicRuneAltar.class)
public class BlockMechanicRuneAltar extends FoxBaseBlock {
    public BlockMechanicRuneAltar(String name) {
        super(FoxWTCore.MODID, name);
        setTileEntityType(TileMechanicRuneAltar.class);
        setCreativeTab(FoxWTCore.TAB);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMechanicRuneAltar)
            FMLNetworkHandler.openGui(player, FoxLib.instance, 1, world, x, y, z);
        return true;
    }
}
