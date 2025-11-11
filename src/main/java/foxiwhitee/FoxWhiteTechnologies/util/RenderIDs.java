package foxiwhitee.FoxWhiteTechnologies.util;

import cpw.mods.fml.client.registry.RenderingRegistry;

public enum RenderIDs {
    ASGARD_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    HELHELM_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    VALHALLA_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    MIDGARD_MANA_POOL(RenderingRegistry.getNextAvailableRenderId()),
    ASGARD_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    HELHELM_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    VALHALLA_SPREADER(RenderingRegistry.getNextAvailableRenderId()),
    MIDGARD_SPREADER(RenderingRegistry.getNextAvailableRenderId());

    private int id;
    RenderIDs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
