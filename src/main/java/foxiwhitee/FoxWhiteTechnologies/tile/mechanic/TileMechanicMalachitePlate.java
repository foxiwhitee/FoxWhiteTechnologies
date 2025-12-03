package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;

import java.util.List;

public class TileMechanicMalachitePlate extends TileMechanicManaBlock<RecipeMalachitePlate> {
    public TileMechanicMalachitePlate() {

    }

    @Override
    protected List<RecipeMalachitePlate> getRecipes() {
        return ModRecipes.recipesMalachitePlate;
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
        return 20 * WTConfig.speedMalachitePlate;
    }

    @Override
    protected int getMaxSpeedBonus() {
        return WTConfig.malachitePlateMaxSpeedBonus;
    }

    @Override
    protected boolean hasProductivity() {
        return WTConfig.malachitePlateHasProductivity;
    }
}
