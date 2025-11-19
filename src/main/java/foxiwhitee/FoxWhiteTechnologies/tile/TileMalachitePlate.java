package foxiwhitee.FoxWhiteTechnologies.tile;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.tile.FoxBaseTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.multiblock.Multiblock;
import vazkii.botania.api.lexicon.multiblock.MultiblockSet;
import vazkii.botania.api.lexicon.multiblock.component.MultiblockComponent;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileTerraPlate;
import vazkii.botania.common.item.ModItems;

import java.util.*;

public class TileMalachitePlate extends FoxBaseTile implements ISparkAttachable {
    private static class MBPart {
        private final ItemStack stack;
        private final int x, y, z;

        private MBPart(ItemStack stack, int x, int y, int z) {
            this.stack = stack;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static final int MAX_MANA = 10000000;
    private int mana, ticks, tier;
    private RecipeMalachitePlate currentRecipe;

    private static final List<MBPart> TIER_1 = new ArrayList<>();
    private static final List<MBPart> TIER_2 = new ArrayList<>();
    private static final List<MBPart> TIER_3 = new ArrayList<>();
    private static final List<MBPart> TIER_4 = new ArrayList<>();

    public TileMalachitePlate() {
        if (TIER_1.isEmpty() && TIER_2.isEmpty() && TIER_3.isEmpty() && TIER_4.isEmpty()) {
            initMultiBlockStructure();
        }
    }

    public static MultiblockSet makeMultiblockSet(int tier) {
        Multiblock mb = new Multiblock();

        if (TIER_1.isEmpty() && TIER_2.isEmpty() && TIER_3.isEmpty() && TIER_4.isEmpty()) {
            initMultiBlockStructure();
        }

        List<MBPart> parts = new ArrayList<>(switch (tier) {
            case 0 -> TIER_1;
            case 1 -> TIER_2;
            case 2 -> TIER_3;
            case 3 -> TIER_4;
            default -> throw new IllegalStateException("Unexpected value: " + tier);
        });

        for (MBPart part : parts) {
            mb.addComponent(part.x, part.y, part.z, Block.getBlockFromItem(part.stack.getItem()), part.stack.getItemDamage());
        }

        mb.addComponent(0, 0, 0, foxiwhitee.FoxWhiteTechnologies.ModBlocks.MALACHITE_PLATE, 0);
        mb.setRenderOffset(0, 1, 0);
        return mb.makeSetRegisterStructure(TileMalachitePlate.class, foxiwhitee.FoxWhiteTechnologies.ModBlocks.MALACHITE_PLATE, new MultiblockComponent[0]);
    }

    private static void initMultiBlockStructure() {
        final ItemStack livingRock = new ItemStack(ModBlocks.livingrock);
        final ItemStack livingWood = new ItemStack(ModBlocks.livingwood);
        final ItemStack pylon = new ItemStack(ModBlocks.pylon);
        final ItemStack naturaPylon = new ItemStack(ModBlocks.pylon, 1, 1);
        final ItemStack gaiaPylon = new ItemStack(ModBlocks.pylon, 1, 2);
        final ItemStack terraSteelBlock = new ItemStack(ModBlocks.storage, 1, 1);
        final ItemStack midgardBlock = new ItemStack(foxiwhitee.FoxWhiteTechnologies.ModBlocks.MIDGARD_BLOCK);
        final ItemStack valhallaBlock = new ItemStack(foxiwhitee.FoxWhiteTechnologies.ModBlocks.VALHALLA_BLOCK);
        final ItemStack helhelmBlock = new ItemStack(foxiwhitee.FoxWhiteTechnologies.ModBlocks.HELHEIM_BLOCK);
        final ItemStack obsidian = new ItemStack(Blocks.obsidian);
        TIER_1.addAll(Arrays.asList(
            new MBPart(livingRock, 0, -1, 0),

            new MBPart(livingRock, 0, -1, -1), // верх z-1
            new MBPart(livingRock, -1, -1, 0), // вліво x-1
            new MBPart(livingRock, 1, -1, 0),
            new MBPart(livingRock, 0, -1, 1),

            new MBPart(livingRock, -2, -1, -2),
            new MBPart(livingRock, 2, -1, -2),
            new MBPart(livingRock, 2, -1, 2),
            new MBPart(livingRock, -2, -1, 2),

            new MBPart(terraSteelBlock, 0, -1, -2),
            new MBPart(terraSteelBlock, -2, -1, 0),
            new MBPart(terraSteelBlock, 2, -1, 0),
            new MBPart(terraSteelBlock, 0, -1, 2),

            new MBPart(terraSteelBlock, -1, -1, -1),
            new MBPart(terraSteelBlock, 1, -1, -1),
            new MBPart(terraSteelBlock, 1, -1, 1),
            new MBPart(terraSteelBlock, -1, -1, 1),

            new MBPart(pylon, -2, 1, -2),
            new MBPart(pylon, 2, 1, -2),
            new MBPart(pylon, 2, 1, 2),
            new MBPart(pylon, -2, 1, 2)
        ));
        TIER_2.addAll(Arrays.asList(
            new MBPart(livingRock, 0, -1, 0),

            new MBPart(livingRock, 1, -1, -2),
            new MBPart(livingRock, -1, -1, -2),
            new MBPart(livingRock, 1, -1, 2),
            new MBPart(livingRock, -1, -1, 2),
            new MBPart(livingRock, -2, -1, -1),
            new MBPart(livingRock, -2, -1, 1),
            new MBPart(livingRock, 2, -1, -1),
            new MBPart(livingRock, 2, -1, 1),

            new MBPart(midgardBlock, 0, -1, -1),
            new MBPart(midgardBlock, -1, -1, 0),
            new MBPart(midgardBlock, 1, -1, 0),
            new MBPart(midgardBlock, 0, -1, 1),

            new MBPart(obsidian, -1, -1, -1),
            new MBPart(obsidian, 1, -1, -1),
            new MBPart(obsidian, 1, -1, 1),
            new MBPart(obsidian, -1, -1, 1),
            new MBPart(obsidian, -3, -1, -3),
            new MBPart(obsidian, 3, -1, -3),
            new MBPart(obsidian, 3, -1, 3),
            new MBPart(obsidian, -3, -1, 3),

            new MBPart(pylon, -3, 1, -3),
            new MBPart(pylon, 3, 1, -3),
            new MBPart(pylon, 3, 1, 3),
            new MBPart(pylon, -3, 1, 3)
        ));
        TIER_3.addAll(Arrays.asList(
            new MBPart(livingRock, 0, -1, 0),

            new MBPart(livingRock, -2, -1, -2),
            new MBPart(livingRock, -2, -1, 2),
            new MBPart(livingRock, 2, -1, 2),
            new MBPart(livingRock, 2, -1, -2),

            new MBPart(livingRock, -4, -1, -3),
            new MBPart(livingRock, -3, -1, -4),
            new MBPart(livingRock, -5, -1, -4),
            new MBPart(livingRock, -4, -1, -5),

            new MBPart(livingRock, 4, -1, -3),
            new MBPart(livingRock, 3, -1, -4),
            new MBPart(livingRock, 5, -1, -4),
            new MBPart(livingRock, 4, -1, -5),

            new MBPart(livingRock, 4, -1, 3),
            new MBPart(livingRock, 3, -1, 4),
            new MBPart(livingRock, 5, -1, 4),
            new MBPart(livingRock, 4, -1, 5),

            new MBPart(livingRock, -4, -1, 3),
            new MBPart(livingRock, -3, -1, 4),
            new MBPart(livingRock, -5, -1, 4),
            new MBPart(livingRock, -4, -1, 5),

            new MBPart(obsidian, -1, -1, -3),
            new MBPart(obsidian, -1, -1, -2),
            new MBPart(obsidian, -1, -1, -1),
            new MBPart(obsidian, -2, -1, -1),
            new MBPart(obsidian, -3, -1, -1),

            new MBPart(obsidian, 1, -1, -3),
            new MBPart(obsidian, 1, -1, -2),
            new MBPart(obsidian, 1, -1, -1),
            new MBPart(obsidian, 2, -1, -1),
            new MBPart(obsidian, 3, -1, -1),

            new MBPart(obsidian, 1, -1, 3),
            new MBPart(obsidian, 1, -1, 2),
            new MBPart(obsidian, 1, -1, 1),
            new MBPart(obsidian, 2, -1, 1),
            new MBPart(obsidian, 3, -1, 1),

            new MBPart(obsidian, -1, -1, 3),
            new MBPart(obsidian, -1, -1, 2),
            new MBPart(obsidian, -1, -1, 1),
            new MBPart(obsidian, -2, -1, 1),
            new MBPart(obsidian, -3, -1, 1),

            new MBPart(obsidian, -4, -1, -4),
            new MBPart(obsidian, 4, -1, -4),
            new MBPart(obsidian, 4, -1, 4),
            new MBPart(obsidian, -4, -1, 4),

            new MBPart(obsidian, 0, -1, -5),
            new MBPart(obsidian, 0, -1, 5),
            new MBPart(obsidian, 5, -1, 0),
            new MBPart(obsidian, -5, -1, 0),

            new MBPart(valhallaBlock, 0, -1, -1),
            new MBPart(valhallaBlock, 0, -1, 1),
            new MBPart(valhallaBlock, 1, -1, 0),
            new MBPart(valhallaBlock, -1, -1, 0),

            new MBPart(midgardBlock, 0, -1, -2),
            new MBPart(midgardBlock, 0, -1, 2),
            new MBPart(midgardBlock, 2, -1, 0),
            new MBPart(midgardBlock, -2, -1, 0),

            new MBPart(livingWood, 0, -1, -3),
            new MBPart(livingWood, 0, -1, 3),
            new MBPart(livingWood, 3, -1, 0),
            new MBPart(livingWood, -3, -1, 0),

            new MBPart(livingWood, 0, -1, -4),
            new MBPart(livingWood, 0, -1, 4),
            new MBPart(livingWood, 4, -1, 0),
            new MBPart(livingWood, -4, -1, 0),

            new MBPart(pylon, 0, 1, -5),
            new MBPart(pylon, 0, 1, 5),
            new MBPart(pylon, 5, 1, 0),
            new MBPart(pylon, -5, 1, 0),

            new MBPart(naturaPylon, -4, 1, -4),
            new MBPart(naturaPylon, -4, 1, 4),
            new MBPart(naturaPylon, 4, 1, 4),
            new MBPart(naturaPylon, 4, 1, -4)
        ));
        TIER_4.addAll(Arrays.asList(
            new MBPart(livingRock, 0, -1, 0),
            new MBPart(livingRock, 0, -1, -1),
            new MBPart(livingRock, 0, -1, 1),
            new MBPart(livingRock, -1, -1, 0),
            new MBPart(livingRock, 1, -1, 0),

            new MBPart(livingRock, -3, -1, -2),
            new MBPart(livingRock, -2, -1, -3),
            new MBPart(livingRock, 3, -1, -2),
            new MBPart(livingRock, 2, -1, -3),
            new MBPart(livingRock, 3, -1, 2),
            new MBPart(livingRock, 2, -1, 3),
            new MBPart(livingRock, -3, -1, 2),
            new MBPart(livingRock, -2, -1, 3),

            new MBPart(valhallaBlock, -4, -1, -2),
            new MBPart(valhallaBlock, -2, -1, -4),
            new MBPart(valhallaBlock, 4, -1, -2),
            new MBPart(valhallaBlock, 2, -1, -4),
            new MBPart(valhallaBlock, 4, -1, 2),
            new MBPart(valhallaBlock, 2, -1, 4),
            new MBPart(valhallaBlock, -4, -1, 2),
            new MBPart(valhallaBlock, -2, -1, 4),

            new MBPart(helhelmBlock, 0, -1, -3),
            new MBPart(helhelmBlock, 0, -1, 3),
            new MBPart(helhelmBlock, -3, -1, 0),
            new MBPart(helhelmBlock, 3, -1, 0),

            new MBPart(helhelmBlock, -2, -1, -2),
            new MBPart(helhelmBlock, 2, -1, -2),
            new MBPart(helhelmBlock, 2, -1, 2),
            new MBPart(helhelmBlock, -2, -1, 2),

            new MBPart(midgardBlock, -4, -1, -4),
            new MBPart(midgardBlock, 4, -1, -4),
            new MBPart(midgardBlock, 4, -1, 4),
            new MBPart(midgardBlock, -4, -1, 4),

            new MBPart(midgardBlock, -5, -1, -4),
            new MBPart(midgardBlock, -4, -1, -5),
            new MBPart(midgardBlock, 5, -1, -4),
            new MBPart(midgardBlock, 4, -1, -5),
            new MBPart(midgardBlock, 5, -1, 4),
            new MBPart(midgardBlock, 4, -1, 5),
            new MBPart(midgardBlock, -5, -1, 4),
            new MBPart(midgardBlock, -4, -1, 5),

            new MBPart(livingWood, 0, -1, -2),
            new MBPart(livingWood, 0, -1, 2),
            new MBPart(livingWood, 2, -1, 0),
            new MBPart(livingWood, -2, -1, 0),

            new MBPart(livingWood, -1, -1, -3),
            new MBPart(livingWood, -1, -1, -4),
            new MBPart(livingWood, 1, -1, -3),
            new MBPart(livingWood, 1, -1, -4),

            new MBPart(livingWood, -3, -1, -1),
            new MBPart(livingWood, -4, -1, -1),
            new MBPart(livingWood, -3, -1, 1),
            new MBPart(livingWood, -4, -1, 1),

            new MBPart(livingWood, 3, -1, -1),
            new MBPart(livingWood, 4, -1, -1),
            new MBPart(livingWood, 3, -1, 1),
            new MBPart(livingWood, 4, -1, 1),

            new MBPart(livingWood, -1, -1, 3),
            new MBPart(livingWood, -1, -1, 4),
            new MBPart(livingWood, 1, -1, 3),
            new MBPart(livingWood, 1, -1, 4),

            new MBPart(obsidian, -1, -1, -1),
            new MBPart(obsidian, -2, -1, -1),
            new MBPart(obsidian, -1, -1, -2),
            new MBPart(obsidian, -5, -1, -1),
            new MBPart(obsidian, -6, -1, -1),
            new MBPart(obsidian, -5, -1, -2),
            new MBPart(obsidian, -1, -1, -5),
            new MBPart(obsidian, -2, -1, -5),
            new MBPart(obsidian, -1, -1, -6),

            new MBPart(obsidian, 1, -1, -1),
            new MBPart(obsidian, 2, -1, -1),
            new MBPart(obsidian, 1, -1, -2),
            new MBPart(obsidian, 5, -1, -1),
            new MBPart(obsidian, 6, -1, -1),
            new MBPart(obsidian, 5, -1, -2),
            new MBPart(obsidian, 1, -1, -5),
            new MBPart(obsidian, 2, -1, -5),
            new MBPart(obsidian, 1, -1, -6),

            new MBPart(obsidian, 1, -1, 1),
            new MBPart(obsidian, 2, -1, 1),
            new MBPart(obsidian, 1, -1, 2),
            new MBPart(obsidian, 5, -1, 1),
            new MBPart(obsidian, 6, -1, 1),
            new MBPart(obsidian, 5, -1, 2),
            new MBPart(obsidian, 1, -1, 5),
            new MBPart(obsidian, 2, -1, 5),
            new MBPart(obsidian, 1, -1, 6),

            new MBPart(obsidian, -1, -1, 1),
            new MBPart(obsidian, -2, -1, 1),
            new MBPart(obsidian, -1, -1, 2),
            new MBPart(obsidian, -5, -1, 1),
            new MBPart(obsidian, -6, -1, 1),
            new MBPart(obsidian, -5, -1, 2),
            new MBPart(obsidian, -1, -1, 5),
            new MBPart(obsidian, -2, -1, 5),
            new MBPart(obsidian, -1, -1, 6),

            new MBPart(obsidian, 0, -1, -8),
            new MBPart(obsidian, 0, -1, 8),
            new MBPart(obsidian, 8, -1, 0),
            new MBPart(obsidian, -8, -1, 0),

            new MBPart(obsidian, -5, -1, -5),
            new MBPart(obsidian, -6, -1, -4),
            new MBPart(obsidian, -4, -1, -6),

            new MBPart(obsidian, 5, -1, -5),
            new MBPart(obsidian, 6, -1, -4),
            new MBPart(obsidian, 4, -1, -6),

            new MBPart(obsidian, 5, -1, 5),
            new MBPart(obsidian, 6, -1, 4),
            new MBPart(obsidian, 4, -1, 6),

            new MBPart(obsidian, -5, -1, 5),
            new MBPart(obsidian, -6, -1, 4),
            new MBPart(obsidian, -4, -1, 6),

            new MBPart(naturaPylon, 0, 1, -8),
            new MBPart(naturaPylon, 0, 1, 8),
            new MBPart(naturaPylon, 8, 1, 0),
            new MBPart(naturaPylon, -8, 1, 0),

            new MBPart(pylon, -6, 1, -4),
            new MBPart(pylon, -4, 1, -6),

            new MBPart(pylon, 6, 1, -4),
            new MBPart(pylon, 4, 1, -6),

            new MBPart(pylon, 6, 1, 4),
            new MBPart(pylon, 4, 1, 6),

            new MBPart(pylon, -6, 1, 4),
            new MBPart(pylon, -4, 1, 6),

            new MBPart(gaiaPylon, -4, 2, -4),
            new MBPart(gaiaPylon, 4, 2, -4),
            new MBPart(gaiaPylon, 4, 2, 4),
            new MBPart(gaiaPylon, -4, 2, 4)
        ));
    }

    @TileEvent(TileEventType.TICK)
    public void tick() {
        List<EntityItem> items = this.getItems();
        currentRecipe = findMatchingRecipe(items);
        if (currentRecipe != null) {
            ticks++;
            if (ticks >= 20) {
                ticks = 0;
                updateTier();
            }
            if (currentRecipe.getTier() <= tier) {
                ISparkEntity spark = this.getAttachedSpark();
                if (spark != null) {
                    for (ISparkEntity otherSpark : SparkHelper.getSparksAround(this.worldObj, (double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D)) {
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
            } else {
                currentRecipe = null;
            }
        } else {
            ticks = 0;
        }

    }

    private void updateTier() {
        int newTier = detectTier();
        if (newTier != tier) {
            tier = newTier;
            markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    private int detectTier() {
        if (checkTier(TIER_4)) return 3;
        if (checkTier(TIER_3)) return 2;
        if (checkTier(TIER_2)) return 1;
        if (checkTier(TIER_1)) return 0;
        return -1;
    }

    private boolean checkTier(List<MBPart> list) {
        for (MBPart p : list) {
            int bx = xCoord + p.x;
            int by = yCoord + p.y;
            int bz = zCoord + p.z;

            if (!ItemStack.areItemStacksEqual(
                new ItemStack(worldObj.getBlock(bx, by, bz), 1, worldObj.getBlockMetadata(bx, by, bz)),
                p.stack
            )) {
                return false;
            }
        }
        return true;
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
        cmp.setInteger("tier", this.tier);
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readCustomNBT(NBTTagCompound cmp) {
        this.mana = cmp.getInteger("mana");
        this.tier = cmp.getInteger("tier");
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
