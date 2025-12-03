package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipePureDaisy;
import foxiwhitee.FoxWhiteTechnologies.util.RecipeInitializer;

import java.util.ArrayList;
import java.util.List;

public class TileMechanicPureDaisy extends TileMechanicBlock<CustomRecipePureDaisy> {
    private static final List<CustomRecipePureDaisy> recipes = new ArrayList<>();

    public TileMechanicPureDaisy() {
        if (recipes.isEmpty()) {
            RecipeInitializer.initRecipesMechanicPureDaisy(recipes);
        }
    }

    @Override
    protected List<CustomRecipePureDaisy> getRecipes() {
        return recipes;
    }

    @Override
    protected int getInvSize() {
        return 24;
    }

    @Override
    protected int getInvOutSize() {
        return 24;
    }

    @Override
    protected int getSpeed() {
        return 20 * WTConfig.speedPureDaisy;
    }

    @Override
    protected int getMaxSpeedBonus() {
        return WTConfig.pureDaisyMaxSpeedBonus;
    }

    @Override
    protected boolean hasProductivity() {
        return WTConfig.pureDaisyHasProductivity;
    }
}
