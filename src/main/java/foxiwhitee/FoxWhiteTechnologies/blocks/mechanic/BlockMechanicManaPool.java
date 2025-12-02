package foxiwhitee.FoxWhiteTechnologies.blocks.mechanic;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic.GuiMechanicManaPool;
import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicManaPool;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileMechanicManaPool.class, gui = GuiMechanicManaPool.class, container = ContainerMechanicManaPool.class)
public class BlockMechanicManaPool extends FoxBaseBlock {
    public BlockMechanicManaPool(String name) {
        super(FoxWTCore.MODID, name);
        setTileEntityType(TileMechanicManaPool.class);
        setCreativeTab(FoxWTCore.TAB);
        setLightLevel(1F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMechanicManaPool)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockMechanicManaPool.class), world, x, y, z);
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
        return RenderIDs.MECHANIC_MANA_POOL.getId();
    }
}
