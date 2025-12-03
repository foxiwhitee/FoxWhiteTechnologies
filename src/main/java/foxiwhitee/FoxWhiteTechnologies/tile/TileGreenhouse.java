package foxiwhitee.FoxWhiteTechnologies.tile;

import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxLib.tile.FoxBaseInvTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.items.ItemResourceEfficiencyUpgrade;
import foxiwhitee.FoxWhiteTechnologies.items.ItemSpeedUpgrade;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;

import java.util.List;

public class TileGreenhouse extends FoxBaseInvTile implements ISparkAttachable, IManaPool {
    private static ItemStack tnt;
    private static ItemStack endoflame;
    private static ItemStack entropinnyum;
    private static ItemStack gourmaryllis;
    private static final int endoflameGenerating = 3;
    private static final int entropinnyumGenerating = 6500;
    private static final int gourmaryllisGenerating = 64;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 9);
    private final FoxInternalInventory flowers = new FoxInternalInventory(this, 27);
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3, 1);
    private int mana, speedBonus, productivity, tick, resourceEfficiency = 1;

    public TileGreenhouse() {
        if (endoflame == null || entropinnyum == null || gourmaryllis == null || tnt == null) {
            initStacks();
        }
    }

    private static void initStacks() {
        endoflame = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "endoflame");
        endoflame.setTagCompound(tag);
        entropinnyum = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        tag = new NBTTagCompound();
        tag.setString("type", "entropinnyum");
        entropinnyum.setTagCompound(tag);
        gourmaryllis = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        tag = new NBTTagCompound();
        tag.setString("type", "gourmaryllis");
        gourmaryllis.setTagCompound(tag);
        tnt = new ItemStack(Blocks.tnt);
    }

    @TileEvent(TileEventType.TICK)
    public void tick() {
        if (worldObj.isRemote) {
            return;
        }
        if (tick++ > getRealSpeed()) {
            tick = 0;
            int toConsumeByEndoflame = 0;
            int toConsumeByEntropinnyum = 0;
            int toConsumeByGourmaryllis = 0;
            for (int i = 0; i < flowers.getSizeInventory(); i++) {
                ItemStack stack = flowers.getStackInSlot(i);
                if (stack != null) {
                    if (stackEquals(stack, endoflame)) {
                        toConsumeByEndoflame += stack.stackSize;
                    } else if (stackEquals(stack, entropinnyum)) {
                        toConsumeByEntropinnyum += stack.stackSize;
                    } else if (stackEquals(stack, gourmaryllis)) {
                        toConsumeByGourmaryllis += stack.stackSize;
                    }
                }
            }
            int oldMana = this.mana;
            int temp = 0;
            int consumedByEndoflame = consumeFlammableMaterial(toConsumeByEndoflame > 0 && toConsumeByEndoflame / resourceEfficiency < 1 ? 1 : toConsumeByEndoflame / resourceEfficiency);
            int consumedByEntropinnyum = consumeTNT(toConsumeByEntropinnyum > 0 && toConsumeByEntropinnyum / resourceEfficiency < 1 ? 1 : toConsumeByEntropinnyum / resourceEfficiency);
            int consumedByGourmaryllis = consumeFood(toConsumeByGourmaryllis > 0 && toConsumeByGourmaryllis / resourceEfficiency < 1 ? 1 : toConsumeByGourmaryllis / resourceEfficiency);
            temp += consumedByEndoflame * endoflameGenerating;
            temp += consumedByEntropinnyum * entropinnyumGenerating;
            temp += consumedByGourmaryllis * gourmaryllisGenerating;
            int add = (int)(temp * (double)(productivity / 100));
            temp += add;
            int sub = (int)(temp * (double)(WTConfig.greenhouseGenerationLoss / 100));
            this.mana += temp - sub;
            if (oldMana != mana) {
                markForUpdate();
            }
        }
    }

    private double getRealSpeed() {
        return 20 * WTConfig.greenhouseSpeed * (1 - (double) speedBonus / 100);
    }

    private int consumeFood(int count) {
        int saturation = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemFood) {
                int c = Math.min(count, stack.stackSize);
                int val = ((ItemFood)stack.getItem()).func_150905_g(stack);
                saturation += val * val * c;
                count -= c;
                stack.stackSize -= c;
                if (stack.stackSize <= 0) {
                    inventory.setInventorySlotContents(i, null);
                }
                if (count <= 0) {
                    break;
                }
            }
        }
        return saturation * resourceEfficiency;
    }

    private int consumeFlammableMaterial(int count) {
        int ticks = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null) {
                int tick = TileEntityFurnace.getItemBurnTime(stack);
                if (tick > 0) {
                    int c = Math.min(count, stack.stackSize);
                    ticks += tick * c;
                    count -= c;
                    stack.stackSize -= c;
                    if (stack.stackSize <= 0) {
                        inventory.setInventorySlotContents(i, null);
                    }
                }
                if (count <= 0) {
                    break;
                }
            }
        }
        return ticks / 2 * resourceEfficiency;
    }

    private int consumeTNT(int count) {
        int consumed = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stackEquals(stack, tnt)) {
                int c = Math.min(count, stack.stackSize);
                consumed += c;
                count -= c;
                stack.stackSize -= c;
                if (stack.stackSize <= 0) {
                    inventory.setInventorySlotContents(i, null);
                }
                if (count <= 0) {
                    break;
                }
            }
        }
        return consumed  * resourceEfficiency;
    }

    private boolean stackEquals(ItemStack stack1, ItemStack stack2) {
        boolean equals = stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() &&
            stack1.getItemDamage() == stack2.getItemDamage();
        if (equals && stack1.getTagCompound() != null && stack2.getTagCompound() != null) {
            equals = stack1.getTagCompound().equals(stack2.getTagCompound());
        }
        return equals;
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        tick = 0;
        if (iInventory == upgrades) {
            this.speedBonus = 0;
            this.productivity = 0;
            this.resourceEfficiency = 1;
            for (int j = 0; j < upgrades.getSizeInventory(); j++) {
                ItemStack stack = upgrades.getStackInSlot(j);
                if (stack != null && stack.getItem() instanceof ItemSpeedUpgrade) {
                    this.speedBonus += switch (stack.getItemDamage()) {
                        case 0 -> WTConfig.speedUpgradeBonus1;
                        case 1 -> WTConfig.speedUpgradeBonus2;
                        case 2 -> WTConfig.speedUpgradeBonus3;
                        case 3 -> WTConfig.speedUpgradeBonus4;
                        default -> 0;
                    };
                }
                if (stack != null && stack.getItem() instanceof ItemProductivityCard) {
                    productivity += switch (stack.getItemDamage()) {
                        case 0 -> FoxLibConfig.productivityLvl1;
                        case 1 -> FoxLibConfig.productivityLvl2;
                        case 2 -> FoxLibConfig.productivityLvl3;
                        case 3 -> FoxLibConfig.productivityLvl4;
                        case 4 -> FoxLibConfig.productivityLvl5;
                        case 5 -> FoxLibConfig.productivityLvl6;
                        case 6 -> FoxLibConfig.productivityLvl7;
                        case 7 -> FoxLibConfig.productivityLvl8;
                        case 8 -> FoxLibConfig.productivityLvl9;
                        case 9 -> FoxLibConfig.productivityLvl10;
                        case 10 -> FoxLibConfig.productivityLvl11;
                        default -> 0;
                    };
                }
                if (stack != null && stack.getItem() instanceof ItemResourceEfficiencyUpgrade) {
                    this.resourceEfficiency = Math.max(this.resourceEfficiency, 2 + stack.getItemDamage());
                }
            }
            this.speedBonus = Math.min(speedBonus, WTConfig.greenhouseMaxSpeedBonus);
        }
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        flowers.readFromNBT(data, "flowers");
        upgrades.readFromNBT(data, "upgrades");
        mana = data.getInteger("mana");
        speedBonus = data.getInteger("speedBonus");
        productivity = data.getInteger("productivity");
        tick = data.getInteger("tick");
        resourceEfficiency = data.getInteger("resourceEfficiency");
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        flowers.writeToNBT(data, "flowers");
        upgrades.writeToNBT(data, "upgrades");
        data.setInteger("mana", mana);
        data.setInteger("speedBonus", speedBonus);
        data.setInteger("productivity", productivity);
        data.setInteger("tick", tick);
        data.setInteger("resourceEfficiency", resourceEfficiency);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        int oldMana = mana;
        mana = data.readInt();
        return oldMana != mana;
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        data.writeInt(mana);
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        return WTConfig.manaAsgardPool - mana;
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 1), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity)sparks.get(0);
            return (ISparkEntity)e;
        } else {
            return null;
        }
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return true;
    }

    @Override
    public boolean isFull() {
        return WTConfig.manaAsgardPool <= mana;
    }

    @Override
    public void recieveMana(int i) {
        if (i > 0) {
            return;
        }
        this.mana = Math.max(0, i + mana);
        markForUpdate();
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    public FoxInternalInventory getFlowersInventory() {
        return flowers;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        for(int l = 0; l < flowers.getSizeInventory(); ++l) {
            ItemStack is = flowers.getStackInSlot(l);
            if (is != null) {
                drops.add(is);
            }
        }
        for(int l = 0; l < upgrades.getSizeInventory(); ++l) {
            ItemStack is = upgrades.getStackInSlot(l);
            if (is != null) {
                drops.add(is);
            }
        }
    }

    @Override
    public boolean isOutputtingPower() {
        return false;
    }
}
