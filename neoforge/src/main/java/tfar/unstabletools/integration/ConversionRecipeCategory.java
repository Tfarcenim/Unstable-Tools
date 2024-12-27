package tfar.unstabletools.integration;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import tfar.unstabletools.init.ModItems;

import java.util.List;

public class ConversionRecipeCategory implements DisplayCategory<ConversionRecipeDisplay> {
    @Override
    public CategoryIdentifier<? extends ConversionRecipeDisplay> getCategoryIdentifier() {
        return REIPlugin.TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.rei.unstabletools.category.block_conversions");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.UNSTABLE_HOE);
    }

    @Override
    public List<Widget> setupDisplay(ConversionRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 52, bounds.getCenterY()-8);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 63, startPoint.y)).entries(display.getOutputEntries().get(0)).markOutput());
        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 24;
    }
}
