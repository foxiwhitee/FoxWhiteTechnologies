package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.util.RecipeInitializer;

import java.util.ArrayList;
import java.util.List;

public class TileMechanicElvenTrade extends TileMechanicManaBlock<CustomRecipeElvenTrade> {
    private static final List<CustomRecipeElvenTrade> recipes = new ArrayList<>();

    public TileMechanicElvenTrade() {
        if (recipes.isEmpty()) {
            RecipeInitializer.initRecipesMechanicElvenTrade(recipes);
        }
    }

    @Override
    protected List<CustomRecipeElvenTrade> getRecipes() {
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
        return 20 * WTConfig.speedElvenTrade;
    }
}
