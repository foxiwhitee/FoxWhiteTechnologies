package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.items.ItemInfinityWaterUpgrade;
import foxiwhitee.FoxWhiteTechnologies.items.ItemStorageUpgrade;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipePetals;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.util.RecipeInitializer;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.List;

public class TileMechanicPetals extends TileMechanicBlock<CustomRecipePetals> implements IFluidHandler {
    private static final List<CustomRecipePetals> recipes = new ArrayList<>();
    public static final int TANK_CAPACITY = 10000;
    private FluidTank tank = new FluidTank(TANK_CAPACITY);
    private boolean infinityWater;

    public TileMechanicPetals() {
        if (recipes.isEmpty()) {
            RecipeInitializer.initRecipesMechanicPetals(recipes);
        }
    }

    @Override
    protected boolean additionallyConditionForCrafting() {
        return tank.getFluidAmount() >= 1000 || infinityWater;
    }

    @Override
    protected void afterCrafting() {
        super.afterCrafting();
        if (!infinityWater) {
            tank.drain(1000, true);
        }
    }

    @Override
    protected void inventoryCheck(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        super.inventoryCheck(iInventory, i, invOperation, itemStack, itemStack1);
        if (iInventory == getUpgradesInventory()) {
            this.infinityWater = false;
            for (int j = 0; j < getUpgradesInventory().getSizeInventory(); j++) {
                ItemStack stack = getUpgradesInventory().getStackInSlot(j);
                if (stack != null && stack.getItem() instanceof ItemInfinityWaterUpgrade) {
                    this.infinityWater = true;
                }
            }
        }
    }

    protected void craftRecipe(int bonus) {
        if (currentRecipe == null)
            return;

        for (int slot : usedSlots) {
            if (getInternalInventory().getStackInSlot(slot) != null) {
                ItemStack stack = getInternalInventory().getStackInSlot(slot);
                if (stack != null && stack.getItem() != Items.wheat_seeds)
                    consumeItem(stack);
                if (stack.stackSize <= 0)
                    getInternalInventory().setInventorySlotContents(slot, null);
            }
        }
        updateCountInStacks();

        if (getInternalInventory().getStackInSlot(0) != null) {
            getInternalInventory().getStackInSlot(0).stackSize--;
            if (getInternalInventory().getStackInSlot(0).stackSize <= 0)
                getInternalInventory().setInventorySlotContents(0, null);
        }

        ItemStack out = currentRecipe.getOutput().copy();
        out.stackSize += out.stackSize * bonus;
        insertOutput(out);
    }

    protected void updateRecipeIfNeeded() {
        usedSlots.clear();

        if (getInternalInventory().getStackInSlot(0) == null) {
            currentRecipe = null;
            return;
        }

        List<InvEntry> inputs = new ArrayList<>();

        for (int i = 1; i < getInvSize(); i++) {
            ItemStack stack = getInternalInventory().getStackInSlot(i);
            if (stack != null) {
                inputs.add(new InvEntry(i, stack));
            }
        }

        currentRecipe = getRecipe(inputs);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        tank.readFromNBT(data);
        infinityWater = data.getBoolean("infinityWater");
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        tank.writeToNBT(data);
        data.setBoolean("infinityWater", infinityWater);
    }

    @Override
    public boolean readFromStream(ByteBuf data) {
        boolean changed = super.readFromStream(data);
        boolean oldInfinityWater = infinityWater;
        infinityWater = data.readBoolean();
        int amount = data.readInt();
        int fluidId = data.readInt();

        if (fluidId == -1 || amount <= 0) {
            tank.setFluid(null);
        } else {
            Fluid fluid = FluidRegistry.getFluid(fluidId);
            tank.setFluid(new FluidStack(fluid, amount));
        }

        return changed || oldInfinityWater != infinityWater;
    }

    @Override
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeBoolean(infinityWater);
        data.writeInt(tank.getFluidAmount());

        int fluidId = tank.getFluid() != null ? FluidRegistry.getFluidID(tank.getFluid().getFluid()) : -1;
        data.writeInt(fluidId);
    }

    @Override
    protected List<CustomRecipePetals> getRecipes() {
        return recipes;
    }

    @Override
    protected int getInvSize() {
        return 25;
    }

    @Override
    protected int getInvOutSize() {
        return 24;
    }

    @Override
    protected int getSpeed() {
        return 20 * WTConfig.speedPetals;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null) return 0;
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) return null;
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == FluidRegistry.WATER;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    public boolean isInfinityWater() {
        return infinityWater;
    }
}
