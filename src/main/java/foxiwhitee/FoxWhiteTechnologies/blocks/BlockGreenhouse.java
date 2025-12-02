package foxiwhitee.FoxWhiteTechnologies.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.blocks.mechanic.BlockMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.client.gui.GuiGreenhouse;
import foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic.GuiMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerGreenhouse;
import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.tile.TileGreenhouse;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicElvenTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileGreenhouse.class, gui = GuiGreenhouse.class, container = ContainerGreenhouse.class)
public class BlockGreenhouse extends FoxBaseBlock {
    public BlockGreenhouse(String name) {
        super(FoxWTCore.MODID, name);
        setCreativeTab(FoxWTCore.TAB);
        setTileEntityType(TileGreenhouse.class);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileGreenhouse)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockGreenhouse.class), world, x, y, z);
        return true;
    }
}
