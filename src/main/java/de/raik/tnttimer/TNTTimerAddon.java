package de.raik.tnttimer;

import com.google.gson.JsonObject;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class TNTTimerAddon extends LabyModAddon {

    private boolean enabled = true;
    private boolean colored = true;

    @Override
    public void onEnable() {
        this.api.getEventService().registerListener(new ExplosionTimeRenderer(this));
    }

    @Override
    public void loadConfig() {
        JsonObject config = this.getConfig();

        this.enabled = config.has("enabled") ? config.get("enabled").getAsBoolean() : this.enabled;
        this.colored = config.has("colored") ? config.get("colored").getAsBoolean() : this.colored;
    }

    @Override
    protected void fillSettings(List<SettingsElement> settings) {
        settings.add(new BooleanElement("Enabled", this, new ControlElement.IconData(Material.LEVER)
                , "enabled", this.enabled));
        settings.add(new BooleanElement("Colored Time", this
                , new ControlElement.IconData("labymod/textures/settings/settings/tabping_colored.png")
                , "colored", this.colored).bindDescription("The time tag will change the color dynamically depending on remaining time until the explosion"));
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isColored() {
        return this.colored;
    }
}
