package foxiwhitee.FoxWhiteTechnologies.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeManaInfusion;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipeManaInfusion extends RecipeManaInfusion implements IBotanyManaRecipe {
    public CustomRecipeManaInfusion(ItemStack output, Object input, int mana) {
        super(output, input, mana);
    }

    public CustomRecipeManaInfusion(RecipeManaInfusion recipe) {
        super(recipe.getOutput(), recipe.getInput(), recipe.getManaToConsume());
        setAlchemy(recipe.isAlchemy());
        setConjuration(recipe.isConjuration());
    }

    @Override
    public List<Object> getInputs() {
        List<Object> inputs = new ArrayList<>();
        inputs.add(getInput());
        return inputs;
    }

    @Override
    public int getManaUsage() {
        return getManaToConsume();
    }

    @Override
    public boolean upgradedMatches(IInventory inv, boolean b) {
        return matches(inv.getStackInSlot(0));
    }
}
