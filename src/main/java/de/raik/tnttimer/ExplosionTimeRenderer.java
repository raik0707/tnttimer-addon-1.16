package de.raik.tnttimer;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.renderer.RenderEntityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.text.DecimalFormat;

public class ExplosionTimeRenderer {

    private final TNTTimerAddon addon;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public ExplosionTimeRenderer(TNTTimerAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onRender(RenderEntityEvent event) {
        if (!(event.getEntity() instanceof TNTEntity)) {
            return;
        }
        if (!this.addon.isEnabled() || this.addon.isRestricted()) {
            return;
        }

        TNTEntity entity = (TNTEntity) event.getEntity();

        if (Minecraft.getInstance().getRenderManager().squareDistanceTo(entity) > (64D * 64D)) {
            return;
        }
        this.render(event, entity);
    }

    private Color getColor(TNTEntity tntEntity) {
        if (!this.addon.isColored()) {
            return Color.WHITE;
        }

        float green = Math.max(Math.min(tntEntity.getFuse() / 80.0F, 1F), 0F);
        return new Color(1F - green, green, 0F);
    }

    private String getTag(TNTEntity tntEntity) {
        float number = (tntEntity.getFuse()) / 20F;

        if (number < 0) {
            return null;
        }

        return this.decimalFormat.format(number);
    }

    private void render(RenderEntityEvent eventPayload, TNTEntity tntEntity) {
        String tag = this.getTag(tntEntity);
        if (tag == null) {
            return;
        }
        eventPayload.getMatrixStack().push();
        Minecraft.getInstance().fontRenderer.getClass();
        eventPayload.getMatrixStack().translate(0.0D, tntEntity.getHeight() + 0.5F, 0.0D);
        float scale = 0.01666668F * 1.6F;
        eventPayload.getMatrixStack().rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        eventPayload.getMatrixStack().scale(-scale, -scale, scale);
        float x = -Minecraft.getInstance().fontRenderer.getStringPropertyWidth(new StringTextComponent(tag)) / 2.0F;
        float textOpacity = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
        int opacity = (int)(textOpacity * 255.0F) << 24;
        Minecraft.getInstance().fontRenderer.renderString(tag, x, 0.0F, this.getColor(tntEntity).getRGB(), false, eventPayload.getMatrixStack().getLast().getMatrix(), eventPayload.getBuffer(), false, opacity, eventPayload.getPackedLight());
        Minecraft.getInstance().fontRenderer.getClass();
        eventPayload.getMatrixStack().pop();
    }
}
