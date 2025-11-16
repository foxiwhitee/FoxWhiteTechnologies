package foxiwhitee.FoxWhiteTechnologies.blocks;

import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;

public class StaticBlock extends FoxBaseBlock {
    public StaticBlock(String name) {
        super(FoxWTCore.MODID, name);
        setCreativeTab(FoxWTCore.TAB);
        setBlockTextureName(FoxWTCore.MODID + ":static/" + name);
    }
}
