package foxiwhitee.FoxWhiteTechnologies.tile;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.tile.FoxBaseTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.item.ModItems;

import java.util.*;

public class TileMalachitePlate extends FoxBaseTile implements ISparkAttachable {
    public static final int MAX_MANA = 10000000;
    private int mana;
    private RecipeMalachitePlate currentRecipe;

    public TileMalachitePlate() {}

    @TileEvent(TileEventType.TICK)
    public void tick() {
        List<EntityItem> items = this.getItems();
        currentRecipe = findMatchingRecipe(items);
        if (currentRecipe != null) {

            ISparkEntity spark = this.getAttachedSpark();
            if (spark != null) {
                for (ISparkEntity otherSpark : SparkHelper.getSparksAround(this.worldObj, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D)) {
                    if (spark != otherSpark && otherSpark.getAttachedTile() != null && otherSpark.getAttachedTile() instanceof IManaPool) {
                        otherSpark.registerTransfer(spark);
                    }
                }
            }

            if (this.mana > 0) this.doParticles();

            if (this.mana >= currentRecipe.getManaCost() && !this.worldObj.isRemote) {
                consumeInputs(items, currentRecipe);

                EntityItem out = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, currentRecipe.getOut().copy());
                this.worldObj.spawnEntityInWorld(out);
                this.worldObj.playSoundAtEntity(out, "botania:terrasteelCraft", 1.0F, 1.0F);
                this.mana = Math.max(0, this.mana - currentRecipe.getManaCost());
                this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord));
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord);

                currentRecipe = null;
            }
        }

    }

    public static RecipeMalachitePlate findMatchingRecipe(List<EntityItem> list) {
        List<ItemStack> inventory = new ArrayList<>();
        for (EntityItem entityItem : list) {
            if (entityItem != null && !entityItem.isDead) {
                inventory.add(entityItem.getEntityItem());
            }
        }
        for (RecipeMalachitePlate recipe : ModRecipes.recipesMalachitePlate) {
            if (recipe.matches(inventory)) {
                return recipe;
            }
        }
        return null;
    }

    public static void consumeInputs(List<EntityItem> items, RecipeMalachitePlate recipe) {
        List<ItemStack> inventory = new ArrayList<>();
        for (EntityItem entityItem : items) {
            if (entityItem != null && !entityItem.isDead) {
                inventory.add(entityItem.getEntityItem());
            }
        }
        List<Object> required = new ArrayList<>(recipe.getInputs());

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack inv = inventory.get(i);
            if (inv == null) continue;

            int foundIndex = -1;

            for (int j = 0; j < required.size(); j++) {
                Object in = required.get(j);

                if (in instanceof ItemStack st) {
                    if (IFoxRecipe.simpleAreStacksEqual(st, inv) && inv.stackSize >= st.stackSize) {
                        foundIndex = j;
                        inv.stackSize -= st.stackSize;
                        break;
                    }
                }
                else if (in instanceof StackOreDict ore) {
                    if (ore.check(inv, true)) {
                        foundIndex = j;
                        inv.stackSize -= ore.getCount();
                        break;
                    }
                }
            }

            if (foundIndex != -1) {
                required.remove(foundIndex);
                if (inv.stackSize <= 0)
                    inventory.set(i, null);
            }
        }
    }

    private void doParticles() {
        if (this.worldObj.isRemote) {

            int ticks = (int)(100.0F * ((float)this.getCurrentMana() / (float)currentRecipe.getManaCost()));
            int totalSpiritCount = 3;
            double tickIncrement = 360.0D / (double)totalSpiritCount;
            int speed = 5;
            double wticks = (double)(ticks * speed) - tickIncrement;
            double r = Math.sin((double)(ticks - 100) / 10.0D) * 2.0D;
            double g = Math.sin(wticks * Math.PI / 180.0D * 0.55D);

            // ПРОГРЕС 0..1
            float progress = ticks / 100F;

            // ГРАДІЄНТ: зелений → червоний
            float red   = progress;       // 0 → 1
            float green = 1F - progress;  // 1 → 0
            float blue  = 0F;

            float[] colorsfx = new float[] { red, green, blue };

            for (int i = 0; i < totalSpiritCount; ++i) {

                double x = (double)this.xCoord + Math.sin(wticks * Math.PI / 180.0D) * r + 0.5D;
                double y = (double)this.yCoord + 0.25D + Math.abs(r) * 0.7D;
                double z = (double)this.zCoord + Math.cos(wticks * Math.PI / 180.0D) * r + 0.5D;

                wticks += tickIncrement;

                Botania.proxy.wispFX(this.worldObj, x, y, z,
                    colorsfx[0], colorsfx[1], colorsfx[2],
                    0.85F, (float)g * 0.05F, 0.25F);

                Botania.proxy.wispFX(this.worldObj, x, y, z,
                    colorsfx[0], colorsfx[1], colorsfx[2],
                    (float)Math.random() * 0.1F + 0.1F,
                    (float)(Math.random() - 0.5D) * 0.05F,
                    (float)(Math.random() - 0.5D) * 0.05F,
                    (float)(Math.random() - 0.5D) * 0.05F, 0.9F);

                if (ticks == 100) {
                    for (int j = 0; j < 15; ++j) {
                        Botania.proxy.wispFX(this.worldObj,
                            this.xCoord + 0.5D,
                            this.yCoord + 0.5D,
                            this.zCoord + 0.5D,
                            colorsfx[0], colorsfx[1], colorsfx[2],
                            (float)Math.random() * 0.15F + 0.15F,
                            (float)(Math.random() - 0.5D) * 0.125F,
                            (float)(Math.random() - 0.5D) * 0.125F,
                            (float)(Math.random() - 0.5D) * 0.125F);
                    }
                }
            }
        }
    }


    private List<EntityItem> getItems() {
        return this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)(this.zCoord), (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)));
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeCustomNBT(NBTTagCompound cmp) {
        cmp.setInteger("mana", this.mana);
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readCustomNBT(NBTTagCompound cmp) {
        this.mana = cmp.getInteger("mana");
    }

    public int getCurrentMana() {
        return this.mana;
    }

    public boolean isFull() {
        return this.mana >= MAX_MANA;
    }

    public void recieveMana(int mana) {
        this.mana = Math.max(0, Math.min(MAX_MANA, this.mana + mana));
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord));
    }

    public boolean canRecieveManaFromBursts() {
        return findMatchingRecipe(this.getItems()) != null;
    }

    public boolean canAttachSpark(ItemStack stack) {
        return true;
    }

    public void attachSpark(ISparkEntity entity) { }

    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 1), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity)sparks.get(0);
            return (ISparkEntity)e;
        } else {
            return null;
        }
    }

    public boolean areIncomingTranfersDone() {
        return findMatchingRecipe(this.getItems()) == null;
    }

    public int getAvailableSpaceForMana() {
        return Math.max(0, MAX_MANA - this.getCurrentMana());
    }
}
