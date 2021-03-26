package fi.dy.masa.malilib.event;

import java.util.ArrayList;
import java.util.List;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import fi.dy.masa.malilib.interfaces.IRenderDispatcher;
import fi.dy.masa.malilib.interfaces.IRenderer;
import fi.dy.masa.malilib.util.InfoUtils;

public class RenderEventHandler implements IRenderDispatcher
{
    private static final RenderEventHandler INSTANCE = new RenderEventHandler();

    private final List<IRenderer> overlayRenderers = new ArrayList<>();
    private final List<IRenderer> tooltipLastRenderers = new ArrayList<>();
    private final List<IRenderer> worldLastRenderers = new ArrayList<>();

    public static IRenderDispatcher getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void registerGameOverlayRenderer(IRenderer renderer)
    {
        if (this.overlayRenderers.contains(renderer) == false)
        {
            this.overlayRenderers.add(renderer);
        }
    }

    @Override
    public void registerTooltipLastRenderer(IRenderer renderer)
    {
        if (this.tooltipLastRenderers.contains(renderer) == false)
        {
            this.tooltipLastRenderers.add(renderer);
        }
    }

    @Override
    public void registerWorldLastRenderer(IRenderer renderer)
    {
        if (this.worldLastRenderers.contains(renderer) == false)
        {
            this.worldLastRenderers.add(renderer);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderGameOverlayPost(float partialTicks, MatrixStack matrixStack)
    {
        if (this.overlayRenderers.isEmpty() == false)
        {
            for (IRenderer renderer : this.overlayRenderers)
            {
                renderer.onRenderGameOverlayPost(partialTicks);
            }
        }

        InfoUtils.renderInGameMessages(matrixStack);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderTooltipLast(ItemStack stack, int x, int y)
    {
        if (this.tooltipLastRenderers.isEmpty() == false)
        {
            for (IRenderer renderer : this.tooltipLastRenderers)
            {
                renderer.onRenderTooltipLast(stack, x, y);
            }
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderWorldLast(MatrixStack matrixStack, float partialTicks)
    {
        if (this.worldLastRenderers.isEmpty() == false)
        {
            for (IRenderer renderer : this.worldLastRenderers)
            {
                renderer.onRenderWorldLast(partialTicks, matrixStack);
            }
        }
    }
}
