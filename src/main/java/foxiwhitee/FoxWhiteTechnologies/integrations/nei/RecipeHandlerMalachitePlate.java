package foxiwhitee.FoxWhiteTechnologies.integrations.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.ModBlocks;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class RecipeHandlerMalachitePlate extends TemplateRecipeHandler {

    public String getRecipeName() {
        return StatCollector.translateToLocal("tooltip.malachitePlate");
    }

    public String getRecipeID() {
        return "malachitePlate";
    }

    public String getGuiTexture() {
        return "botania:textures/gui/neiBlank.png";
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(72, 54, 18, 18), this.getRecipeID(), new Object[0]));
    }

    public int recipiesPerPage() {
        return 2;
    }

    public void drawBackground(int recipe) {
        super.drawBackground(recipe);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        GuiDraw.changeTexture("botania:textures/gui/petalOverlay.png");
        GuiDraw.drawTexturedModalRect(45, 10, 38, 7, 92, 92);
        int mana = ((CachedMalachitePlateRecipe)this.arecipes.get(recipe)).manaUsage;
        renderPoolManaBar(32, 112, 2334172, 1.0F, mana);
    }

    private void renderPoolManaBar(int x, int y, int color, float alpha, int mana) {
        Minecraft mc = Minecraft.getMinecraft();
        int maxMana = 1000000;
        double poolCount = (double)mana / maxMana;

        String strPoolShort = String.format("%.1f", poolCount) + "x";

        poolCount = Math.floor((double)mana / maxMana);
        if (poolCount < 0) poolCount = 0;
        int onePoolMana = mana - (int)poolCount * maxMana;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,###", symbols);

        String result = df.format(mana);

        String strPoolFull = result + " mana";

        int xc = x - mc.fontRenderer.getStringWidth(strPoolShort) / 2;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xc + 10.0F, (float)y + 5.0F, 0.0F);

        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.pool), 0, 0);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        GL11.glTranslatef(18.0F, 5.0F, 300.0F);
        mc.fontRenderer.drawString(strPoolShort, 0, 0, color);
        mc.fontRenderer.drawString(strPoolFull, 0, mc.fontRenderer.FONT_HEIGHT, color);

        GL11.glPopMatrix();

        if (poolCount * maxMana == mana) {
            onePoolMana = (int)poolCount * maxMana;
        }

        HUDHandler.renderManaBar(x, y, color, alpha, onePoolMana, maxMana);
    }


    public CachedMalachitePlateRecipe getCachedRecipe(RecipeMalachitePlate recipe) {
        return new CachedMalachitePlateRecipe(recipe);
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeID())) {
            for(RecipeMalachitePlate recipe : ModRecipes.recipesMalachitePlate) {
                this.arecipes.add(this.getCachedRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public void loadCraftingRecipes(ItemStack result) {
        for(RecipeMalachitePlate recipe : ModRecipes.recipesMalachitePlate) {
            if (recipe != null && (recipe.getOut().stackTagCompound != null &&
                NEIServerUtils.areStacksSameType(recipe.getOut(), result) ||
                recipe.getOut().stackTagCompound == null && NEIServerUtils.areStacksSameTypeCrafting(recipe.getOut(), result))) {
                this.arecipes.add(this.getCachedRecipe(recipe));
            }
        }

    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for(RecipeMalachitePlate recipe : ModRecipes.recipesMalachitePlate) {
            if (recipe != null) {
                CachedMalachitePlateRecipe crecipe = this.getCachedRecipe(recipe);
                if (crecipe.contains(crecipe.getIngredients(), ingredient) || crecipe.contains(crecipe.getOtherStacks(), ingredient)) {
                    this.arecipes.add(crecipe);
                }
            }
        }

    }

    public class CachedMalachitePlateRecipe extends TemplateRecipeHandler.CachedRecipe {
        public java.util.List<PositionedStack> inputs = new ArrayList<>();
        public PositionedStack output;
        public int manaUsage;

        public CachedMalachitePlateRecipe(RecipeMalachitePlate recipe) {
            this.setIngredients(recipe.getInputs());
            this.output = new PositionedStack(recipe.getOut(), 111, 21);
            this.inputs.add(new PositionedStack(new ItemStack(foxiwhitee.FoxWhiteTechnologies.ModBlocks.MALACHITE_PLATE), 73, 55));
            this.manaUsage = recipe.getManaCost();
        }

        public void setIngredients(java.util.List<Object> inputs) {
            float degreePerInput = 360.0F / (float)inputs.size();
            float currentDegree = -90.0F;

            for(Object o : inputs) {
                if (o instanceof ItemStack stack) {
                    int posX = (int)Math.round((double)73.0F + Math.cos((double)currentDegree * Math.PI / (double)180.0F) * (double)32.0F);
                    int posY = (int)Math.round((double)55.0F + Math.sin((double)currentDegree * Math.PI / (double)180.0F) * (double)32.0F);
                    this.inputs.add(new PositionedStack(stack, posX, posY));
                    currentDegree += degreePerInput;
                }
            }
        }

        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(RecipeHandlerMalachitePlate.this.cycleticks / 20, this.inputs);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
