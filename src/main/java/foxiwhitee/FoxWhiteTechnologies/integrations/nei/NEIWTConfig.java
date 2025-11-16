package foxiwhitee.FoxWhiteTechnologies.integrations.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.GuiRecipeTab;
import codechicken.nei.recipe.HandlerInfo;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import net.minecraft.item.ItemStack;

public class NEIWTConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        RecipeHandlerMalachitePlate recipeHandlerMalachitePlate = new RecipeHandlerMalachitePlate();
        API.registerRecipeHandler(recipeHandlerMalachitePlate);
        API.registerUsageHandler(recipeHandlerMalachitePlate);
        API.addRecipeCatalyst(new ItemStack(ModBlocks.MALACHITE_PLATE), "foxiwhitee.FoxWhiteTechnologies.integrations.nei.RecipeHandlerMalachitePlate");

        HandlerInfo handler = new HandlerInfo(
            "foxiwhitee.FoxWhiteTechnologies.integrations.nei.RecipeHandlerMalachitePlate",
            FoxWTCore.MODNAME,
            FoxWTCore.MODID,
            true,
            ""
        );
        handler.setItem("foxwhitetechnologies:malachitePlate", "");
        handler.setHandlerDimensions(108 + 16 + 5, 162, 2);
        GuiRecipeTab.handlerMap.put(handler.getHandlerName(), handler);
    }

    @Override
    public String getName() {
        return FoxWTCore.MODNAME;
    }

    @Override
    public String getVersion() {
        return FoxWTCore.VERSION;
    }
}
