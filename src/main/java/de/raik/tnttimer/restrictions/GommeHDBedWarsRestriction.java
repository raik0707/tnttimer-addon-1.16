package de.raik.tnttimer.restrictions;

import de.raik.tnttimer.TNTTimerAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.gui.screen.overlay.PlayerTabListOverlayEvent;
import net.labymod.api.event.events.network.server.DisconnectServerEvent;

public class GommeHDBedWarsRestriction implements Restriction {

    private boolean isBedWars = false;

    public GommeHDBedWarsRestriction(TNTTimerAddon addon) {
        addon.api.getEventService().registerListener(this);
    }

    @Override
    public boolean isRestricted() {
        return this.isBedWars;
    }

    @Subscribe
    public void onTabUpdate(PlayerTabListOverlayEvent event) {
        if (!event.getType().equals(PlayerTabListOverlayEvent.Type.HEADER)) {
            return;
        }

        String unformattedText = event.getHeader().getUnformattedComponentText();
        this.isBedWars = unformattedText.contains("gommehd.net") && unformattedText.contains("bedwars");
    }

    @Subscribe
    public void onQuit(DisconnectServerEvent event) {
        this.isBedWars = false;
    }

}
