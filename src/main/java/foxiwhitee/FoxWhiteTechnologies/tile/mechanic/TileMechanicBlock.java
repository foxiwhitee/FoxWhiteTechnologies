package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.config.FoxLibConfig;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxLib.tile.FoxBaseInvTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.items.ItemSpeedUpgrade;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.IBotanyRecipe;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public abstract class TileMechanicBlock<T extends IBotanyRecipe> extends FoxBaseInvTile {
    private final FoxInternalInventory inv = new FoxInternalInventory(this, getInvSize());
    private final FoxInternalInventory output = new FoxInternalInventory(this, getInvOutSize());
    private final FoxInternalInventory upgrades = new FoxInternalInventory(this, 3, 1);
    private int progress, speedBonus, productivity;
    private final Map<T, Double> productivityHistory = new HashMap<>();

    protected T currentRecipe = null;

    protected final List<Integer> usedSlots = new ArrayList<>();

    @TileEvent(TileEventType.TICK)
    public void tick() {
        if (!worldObj.isRemote) {
            updateRecipeIfNeeded();
            if (hasProductivity()) {
                if (!productivityHistory.containsKey(currentRecipe)) {
                    productivityHistory.put(currentRecipe, 0.0);
                }
            }
            if (currentRecipe != null && additionallyConditionForCrafting()) {
                progress += 1;
                if (progress >= getRealSpeed()) {
                    int bonusCount = 0;
                    if (hasProductivity()) {
                        if (productivity > 0) {
                            productivityHistory.merge(currentRecipe, 1.0, Double::sum);
                        }
                        bonusCount = ProductivityUtil.check(productivityHistory, currentRecipe, productivity);
                    }
                    craftRecipe(bonusCount);
                    afterCrafting();
                    updateRecipeIfNeeded();
                }
            } else {
                progress = 0;
            }
            markForUpdate();
        }
    }

    protected boolean additionallyConditionForCrafting() {
        return true;
    }

    protected void afterCrafting() {
        progress = 0;
    }

    public double getRealSpeed() {
        return getSpeed() * (1 - (double) speedBonus / 100);
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        inventoryCheck(iInventory, i, invOperation, itemStack, itemStack1);
        markForUpdate();
    }

    protected void inventoryCheck(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (iInventory == upgrades) {
            this.speedBonus = 0;
            this.productivity = 0;
            for (int j = 0; j < upgrades.getSizeInventory(); j++) {
                ItemStack stack = upgrades.getStackInSlot(j);
                if (stack != null && stack.getItem() instanceof ItemSpeedUpgrade) {
                    productivityHistory.clear();
                    this.speedBonus += switch (stack.getItemDamage()) {
                        case 0 -> WTConfig.speedUpgradeBonus1;
                        case 1 -> WTConfig.speedUpgradeBonus2;
                        case 2 -> WTConfig.speedUpgradeBonus3;
                        case 3 -> WTConfig.speedUpgradeBonus4;
                        default -> 0;
                    };
                }
                if (stack != null && stack.getItem() instanceof ItemProductivityCard) {
                    productivityHistory.clear();
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
            }
            this.speedBonus = Math.min(speedBonus, getMaxSpeedBonus());
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        output.readFromNBT(data, "output");
        upgrades.readFromNBT(data, "upgrades");
        progress = data.getInteger("progress");
        speedBonus = data.getInteger("speedBonus");
        productivity = data.getInteger("productivity");
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        output.writeToNBT(data, "output");
        upgrades.writeToNBT(data, "upgrades");
        data.setInteger("progress", progress);
        data.setInteger("speedBonus", speedBonus);
        data.setInteger("productivity", productivity);
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        data.writeInt(progress);
        data.writeInt(speedBonus);
        data.writeInt(productivity);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        int oldProgress = progress, oldSpeedBonus = speedBonus, oldProductivity = productivity;
        progress = data.readInt();
        speedBonus = data.readInt();
        productivity = data.readInt();
        return oldProgress != progress || oldSpeedBonus != speedBonus || oldProductivity != productivity;
    }

    protected void craftRecipe(int bonus) {
        if (currentRecipe == null)
            return;

        for (int slot : usedSlots) {
            if (inv.getStackInSlot(slot) != null) {
                ItemStack stack = inv.getStackInSlot(slot);
                consumeItem(stack);
                if (stack.stackSize <= 0)
                    inv.setInventorySlotContents(slot, null);
            }
        }

        ItemStack out = currentRecipe.getOutput().copy();
        out.stackSize += out.stackSize * bonus;
        insertOutput(out);

        updateCountInStacks();
    }

    protected void updateCountInStacks() {
        List<InvEntry> entries = new LinkedList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                for (InvEntry entry : entries) {
                    if (entry.stackEquals(stack) && !entry.stack.hasTagCompound() && entry.stack.stackSize < 64) {

                        int need = 64 - entry.stack.stackSize;
                        entry.stack.stackSize += Math.min(need, stack.stackSize);
                        stack.stackSize -= Math.min(need, stack.stackSize);
                    }
                }
                if (stack.stackSize <= 0) {
                    inv.setInventorySlotContents(i, null);
                } else {
                    entries.add(new InvEntry(i, stack));
                }
            }
        }
    }

    protected void consumeItem(ItemStack stack) {
        for (Object o : currentRecipe.getInputs()) {
            if (o instanceof ItemStack temp) {
                if (temp.getItem() == stack.getItem() && temp.getItemDamage() == stack.getItemDamage()) {
                    stack.stackSize -= temp.stackSize;
                    break;
                }
            } else if (o instanceof String) {
                if (OreDictUtil.areStacksEqual(o, stack)) {
                    stack.stackSize--;
                }
            } else if (o instanceof StackOreDict temp) {
                if (OreDictUtil.areStacksEqual(temp.getOre(), stack)) {
                    stack.stackSize -= temp.getCount();
                }
            }
        }
    }

    protected void insertOutput(ItemStack stack) {
        for (int i = 0; i < getInvOutSize(); i++) {
            ItemStack out = output.getStackInSlot(i);
            if (out == null) {
                output.setInventorySlotContents(i, stack);
                return;
            }
            if (out.isItemEqual(stack) && out.getItemDamage() == stack.getItemDamage()) {
                if (out.getTagCompound() != null && stack.getTagCompound() != null) {
                    if (!out.getTagCompound().equals(stack.getTagCompound())) {
                        continue;
                    }
                }
                if (out.stackSize + stack.stackSize <= out.getMaxStackSize()) {
                    out.stackSize += stack.stackSize;
                } else {
                    int c = out.getMaxStackSize() - out.stackSize;
                    out.stackSize += c;
                    stack.stackSize -= c;
                    continue;
                }
                return;
            }
        }
    }

    protected void updateRecipeIfNeeded() {
        usedSlots.clear();

        List<InvEntry> inputs = new ArrayList<>();

        for (int i = 0; i < getInvSize(); i++) {
            ItemStack stack = getInternalInventory().getStackInSlot(i);
            if (stack != null) {
                inputs.add(new InvEntry(i, stack));
            }
        }

        currentRecipe = getRecipe(inputs);
        if (currentRecipe == null) {
            return;
        }
        boolean hasPlace = false;
        ItemStack out = currentRecipe.getOutput().copy();
        for (int i = 0; i < output.getSizeInventory(); i++) {
            ItemStack stack = output.getStackInSlot(i);
            if (stack == null) {
                hasPlace = true;
                break;
            } else {
                if (out.isItemEqual(stack) && out.getItemDamage() == stack.getItemDamage()) {
                    if (out.getTagCompound() != null && stack.getTagCompound() != null) {
                        if (!out.getTagCompound().equals(stack.getTagCompound())) {
                            continue;
                        }
                    }
                    if (out.stackSize + stack.stackSize <= out.getMaxStackSize()) {
                        hasPlace = true;
                        break;
                    }
                }
            }
        }
        if (!hasPlace) {
            currentRecipe = null;
        }
    }

    protected T getRecipe(List<InvEntry> entries) {

        usedSlots.clear();

        for (T r : getRecipes()) {
            List<Integer> matchedSlots = new ArrayList<>();
            List<ItemStack> matchedStacks = matchesStacks(new ArrayList<>(r.getInputs()), matchedSlots, entries);

            if (matchedStacks != null) {
                InventoryBasic inv = new InventoryBasic("null", false, matchedStacks.size());
                for (int i = 0; i < matchedStacks.size(); i++)
                    inv.setInventorySlotContents(i, matchedStacks.get(i));

                if (r.upgradedMatches(inv, true)) {

                    usedSlots.addAll(matchedSlots);
                    return r;
                }
            }
        }

        return null;
    }

    protected List<ItemStack> matchesStacks(List<Object> required, List<Integer> matchedSlots, List<InvEntry> entries) {
        List<ItemStack> matchedStacks = new ArrayList<>();

        List<InvEntry> available = new ArrayList<>(entries);

        matchLoop:
        for (Object req : required) {
            for (int i = 0; i < available.size(); i++) {

                InvEntry e = available.get(i);
                if (e == null) continue;

                if (matchesIngredient(req, e.stack)) {
                    matchedStacks.add(e.stack);
                    matchedSlots.add(e.slot);
                    available.set(i, null);
                    continue matchLoop;
                }
            }
            matchedStacks = null;
            matchedSlots = null;
            break;
        }
        return matchedStacks;
    }

    protected boolean matchesIngredient(Object req, ItemStack stack) {
        if (req instanceof ItemStack st) {
            return OreDictionary.itemMatches(st, stack, false);
        }
        if (req instanceof String ore) {
            int id = OreDictionary.getOreID(ore);
            for (ItemStack s : OreDictionary.getOres(id)) {
                if (OreDictionary.itemMatches(s, stack, false))
                    return true;
            }
        }
        if (req instanceof List<?> list) {
            for (Object o : list) {
                if (matchesIngredient(o, stack)) return true;
            }
        }
        if (req instanceof StackOreDict ore) {
            return ore.check(stack, false);
        }
        return false;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inv;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    @Override
    public int getSizeInventory() {
        return inv.getSizeInventory() + output.getSizeInventory();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot < inv.getSizeInventory();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot >= inv.getSizeInventory();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < inv.getSizeInventory()) {
            return inv.decrStackSize(slot, amount);
        }
        return output.decrStackSize(slot - inv.getSizeInventory(), amount);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < inv.getSizeInventory()) {
            inv.setInventorySlotContents(slot, stack);
        } else {
            output.setInventorySlotContents(slot - inv.getSizeInventory(), stack);
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < inv.getSizeInventory())
            return inv.getStackInSlot(slot);

        return output.getStackInSlot(slot - inv.getSizeInventory());
    }


    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        int[] sides = new int[getSizeInventory()];
        for (int i = 0; i < getSizeInventory(); i++) {
            sides[i] = i;
        }
        return sides;
    }

    public int getProgress() {
        return progress;
    }

    public FoxInternalInventory getUpgradesInventory() {
        return upgrades;
    }

    public int getProductivity() {
        return productivity;
    }

    public double[] getProgressProductivity() {
        if (currentRecipe == null) {
            return new double[] {0};
        }
        return new double[]{productivityHistory.get(currentRecipe)};
    }

    protected abstract List<T> getRecipes();

    protected abstract int getInvSize();

    protected abstract int getInvOutSize();

    protected abstract int getSpeed();

    protected abstract int getMaxSpeedBonus();

    protected abstract boolean hasProductivity();

    protected static class InvEntry {
        public final int slot;
        public final ItemStack stack;

        public InvEntry(int slot, ItemStack stack) {
            this.slot = slot;
            this.stack = stack;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof InvEntry entry) {
                return slot == entry.slot && stack.getItem() == entry.stack.getItem() && stack.getItemDamage() == entry.stack.getItemDamage();
            }
            return false;
        }

        public boolean stackEquals(ItemStack stack) {
            return this.stack.getItem() == stack.getItem() && this.stack.getItemDamage() == stack.getItemDamage();
        }
    }
}
