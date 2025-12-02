package foxiwhitee.FoxWhiteTechnologies.blocks.mechanic;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic.GuiMechanicPureDaisy;
import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicPureDaisy;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPureDaisy;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileMechanicPureDaisy.class, gui = GuiMechanicPureDaisy.class, container = ContainerMechanicPureDaisy.class)
public class BlockMechanicPureDaisy extends FoxBaseBlock {
    public BlockMechanicPureDaisy(String name) {
        super(FoxWTCore.MODID, name);
        setTileEntityType(TileMechanicPureDaisy.class);
        setCreativeTab(FoxWTCore.TAB);
        setLightLevel(1F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMechanicPureDaisy)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockMechanicPureDaisy.class), world, x, y, z);
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
        return RenderIDs.MECHANIC_PURE_DAISY.getId();
    }
}
