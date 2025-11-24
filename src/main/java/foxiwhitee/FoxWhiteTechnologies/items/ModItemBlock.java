package foxiwhitee.FoxWhiteTechnologies.items;

import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.blocks.BlockCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.blocks.BlockCustomSpreader;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ModItemBlock extends ItemBlock {
    private final Block blockType;

    public ModItemBlock(Block b) {
        super(b);
        this.blockType = b;
    }

    public String getUnlocalizedName() {
        return this.blockType.getUnlocalizedName();
    }

    public String getUnlocalizedName(ItemStack i) {
        return this.blockType.getUnlocalizedName();
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        if (WTConfig.enable_tooltips) {
            if (this.blockType.equals(ModBlocks.ASGARD_POOL) ||
                this.blockType.equals(ModBlocks.HELHEIM_POOL) ||
                this.blockType.equals(ModBlocks.VALHALLA_POOL) ||
                this.blockType.equals(ModBlocks.MIDGARD_POOL)) {
                list.add(LocalizationUtils.localize("tooltip.customManaPool", ((BlockCustomManaPool)blockType).getMaxMana() / 1000000));
            } else if (this.blockType.equals(ModBlocks.ASGARD_SPREADER) ||
                this.blockType.equals(ModBlocks.HELHEIM_SPREADER) ||
                this.blockType.equals(ModBlocks.VALHALLA_SPREADER) ||
                this.blockType.equals(ModBlocks.MIDGARD_SPREADER)) {
                list.add(LocalizationUtils.localize("tooltip.customManaSpreader", ((BlockCustomSpreader) blockType).getManaPerSec()));
            }
        }
    }
}

