package foxiwhitee.FoxWhiteTechnologies.recipes;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.value.IAny;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static foxiwhitee.FoxLib.recipes.RecipeUtils.getItemStack;

public class JSONRecipeMalachitePlate implements IJsonRecipe<Object, ItemStack> {
    private ItemStack output;
    private List<Object> inputs;
    private int tier, manaCost;

    public JSONRecipeMalachitePlate() {}

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[] {output};
    }

    @Override
    public Object[] getInputs() {
        return inputs.toArray();
    }

    @Override
    public boolean matches(List<Object> list) {
        List<Object> inputsMissing = new ArrayList<>(inputs);

        for(Object o : list) {
            if (o instanceof ItemStack stack) {
                if (stack == null) {
                    break;
                }

                int stackIndex = -1;

                for(int j = 0; j < inputsMissing.size(); ++j) {
                    Object input = inputsMissing.get(j);
                    if (input instanceof ItemStack st && IFoxRecipe.simpleAreStacksEqual(st, stack) && stack.stackSize >= st.stackSize) {
                        stackIndex = j;
                        break;
                    } else if (input instanceof StackOreDict ore && ore.check(stack, true)) {
                        stackIndex = j;
                        break;
                    }
                }

                if (stackIndex != -1) {
                    inputsMissing.remove(stackIndex);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return inputsMissing.isEmpty();
    }

    @Override
    public boolean hasOreDict() {
        return true;
    }

    @Override
    public boolean hasMineTweakerIntegration() {
        return true;
    }

    @Override
    public String getType() {
        return "malachitePlate";
    }

    @Override
    public IJsonRecipe create(JsonObject data) {
        try {
            this.output = RecipeUtils.getOutput(data);
            Object[] objects = getInputs(data, hasOreDict());
            this.inputs = Arrays.asList(objects);
            this.tier = data.get("tier").getAsInt();
            this.manaCost = data.get("mana").getAsInt();
        } catch (RuntimeException e) {
            if (e.getMessage().startsWith("Item not found:")) {
                this.inputs = null;
                this.output = null;
            } else {
                throw e;
            }
        }
        return this;
    }

    @Override
    public void register() {
        if (this.output == null && this.inputs == null) {
            return;
        }
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("Inputs cannot be empty for autoCrystallizer recipe");
        RecipeMalachitePlate recipe = new RecipeMalachitePlate(output, tier, manaCost, inputs);
        ModRecipes.recipesMalachitePlate.add(recipe);
    }

    @Override
    public void addCraftByMineTweaker(IItemStack stack, IAny... inputs) {
        int[] ints = new int[2];
        Object[] objects = new Object[inputs.length - 2];
        for (int i = 0; i < inputs.length; i++) {
            if (i <= 1) {
                ints[i] = inputs[i].asInt();
            } else {
                IIngredient ingredient = inputs[i].as(IIngredient.class);
                objects[i - 2] = ingredient.getInternal();
            }
        }
        ItemStack real = (ItemStack) stack.getInternal();
        RecipeMalachitePlate recipe = new RecipeMalachitePlate(real, ints[0], ints[1], Arrays.asList(objects));
        ModRecipes.recipesMalachitePlate.add(recipe);

    }

    @Override
    public void removeCraftByMineTweaker(IItemStack stack) {
        ItemStack real = (ItemStack) stack.getInternal();
        ModRecipes.recipesMalachitePlate.removeIf(recipe -> IFoxRecipe.simpleAreStacksEqual(real, recipe.getOut()));
    }

    private static Object[] getInputs(JsonObject data, boolean oreDict) throws RuntimeException {
        if (!data.has("inputs")) {
            throw new RuntimeException("Unable to find craft inputs");
        } else {
            JsonArray inp = data.get("inputs").getAsJsonArray();
            Object[] inputs = new Object[inp.size()];

            for(int i = 0; i < inp.size(); ++i) {
                if (inp.get(i).isJsonObject()) {
                    inputs[i] = getItemStack(inp.get(i));
                } else {
                    String name = inp.get(i).getAsString();
                    if (oreDict && name.startsWith("<ore:")) {
                        Parsed parsed = parseOredict(name);
                        inputs[i] = new StackOreDict(parsed.name, parsed.count);
                    } else {
                        inputs[i] = getItemStack(name);
                    }
                }
            }

            return inputs;
        }
    }

    private static Parsed parseOredict(String input) {
        Pattern pattern = Pattern.compile("^<([\\w-]+):([\\w.-]*?)(?::(\\d+))?>$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String first = matcher.group(1);
            String second = matcher.group(2);
            int colonNumber = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 1;
            return new Parsed(first, second, colonNumber);
        } else {
            throw new RuntimeException("ItemStack should have the form <modId:name.meta:count> where meta and count are optional");
        }
    }

    @Desugar
    private static record Parsed(String modId, String name, int count) {
        public String toString() {
            return "Parsed[" + "modId=" + this.modId + "," + "name=" + this.name + "," + "count=" + this.count + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Parsed parsed = (Parsed) o;
            return count == parsed.count && Objects.equals(name, parsed.name) && Objects.equals(modId, parsed.modId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(modId, name, count);
        }
    }
}
