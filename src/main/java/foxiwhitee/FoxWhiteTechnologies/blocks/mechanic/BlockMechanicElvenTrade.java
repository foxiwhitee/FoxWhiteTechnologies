package foxiwhitee.FoxWhiteTechnologies.blocks.mechanic;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.gui.GuiMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.client.gui.GuiMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileMechanicElvenTrade.class, gui = GuiMechanicElvenTrade.class, container = ContainerMechanicElvenTrade.class)
public class BlockMechanicElvenTrade extends FoxBaseBlock {
    public BlockMechanicElvenTrade(String name) {
        super(FoxWTCore.MODID, name);
        setTileEntityType(TileMechanicElvenTrade.class);
        setCreativeTab(FoxWTCore.TAB);
        setLightLevel(1F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMechanicElvenTrade)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockMechanicElvenTrade.class), world, x, y, z);
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return super.renderAsNormalBlock();
    }

    @Override
    public int getRenderType() {
        return RenderIDs.MECHANIC_ELVEN_TRADE.getId();
    }
}
