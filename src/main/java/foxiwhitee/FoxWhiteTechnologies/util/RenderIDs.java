package foxiwhitee.FoxWhiteTechnologies.util;

import cpw.mods.fml.client.registry.RenderingRegistry;

public enum RenderIDs {
    ASGARD_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    HELHEIM_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    VALHALLA_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    MIDGARD_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),

    ASGARD_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    HELHEIM_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    VALHALLA_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    MIDGARD_SPREADER(RenderingRegistry.getNextAvailableRenderId()),

    MECHANIC_ELVEN_TRADE(RenderingRegistry.getNextAvailableRenderId()),
    MECHANIC_MALACHITE_PLATE(RenderingRegistry.getNextAvailableRenderId()),
    MECHANIC_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    MECHANIC_PETALS(RenderingRegistry.getNextAvailableRenderId()),
    MECHANIC_PURE_DAISY(RenderingRegistry.getNextAvailableRenderId()),
    MECHANIC_RUNE_ALTAR(RenderingRegistry.getNextAvailableRenderId());

    private int id;
    RenderIDs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
