package de.raik.tnttimer.restrictions;

import net.labymod.core_implementation.mc116.gui.ModPlayerTabOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Field;

public class GommeHDBedWarsRestriction implements Restriction {

    private static final Field HEADER_FIELD;

    private final Minecraft minecraft = Minecraft.getInstance();

    static {
        Field headerField = null;
        try {
            headerField = ModPlayerTabOverlay.class.getDeclaredField("header");
            headerField.setAccessible(true);
        } catch (NoSuchFieldException ignored) {}

        HEADER_FIELD = headerField;
    }

    @Override
    public boolean isRestricted() {
        ITextComponent textComponent;
        try {
            ModPlayerTabOverlay overlayGui = (ModPlayerTabOverlay) this.minecraft.ingameGUI.getTabList();
            textComponent = (ITextComponent) HEADER_FIELD.get(overlayGui);
        } catch (IllegalAccessException | ClassCastException exception) {
            return false;
        }
        if (textComponent == null) {
            return false;
        }

        String text = textComponent.getString().toLowerCase();

        return text.contains("gommehd.net") && text.contains("bedwars");
    }


}
