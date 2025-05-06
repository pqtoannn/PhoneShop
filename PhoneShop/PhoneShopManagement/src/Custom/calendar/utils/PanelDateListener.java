package Custom.calendar.utils;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import Custom.calendar.model.ModelDate;

/**
 *
 * @author Raven
 */
public interface PanelDateListener {

    public boolean cellPaint(Graphics2D g2, Rectangle2D rectangle, ModelDate e);
}
