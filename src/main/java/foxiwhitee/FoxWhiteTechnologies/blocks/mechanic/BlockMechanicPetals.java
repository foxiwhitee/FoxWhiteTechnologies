package foxiwhitee.FoxWhiteTechnologies.blocks.mechanic;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.gui.GuiMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileMechanicPetals.class, gui = GuiMechanicPetals.class, container = ContainerMechanicPetals.class)
public class BlockMechanicPetals extends FoxBaseBlock {
    public BlockMechanicPetals(String name) {
        super(FoxWTCore.MODID, name);
        setTileEntityType(TileMechanicPetals.class);
        setCreativeTab(FoxWTCore.TAB);
        setLightLevel(1F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMechanicPetals)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockMechanicPetals.class), world, x, y, z);
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
        return RenderIDs.MECHANIC_PETALS.getId();
    }
}
