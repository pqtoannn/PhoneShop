
package Custom;

import com.formdev.flatlaf.icons.FlatAbstractIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;


public class ColorIcon extends FlatAbstractIcon {

    private int[] rgb = new int[3];

    public ColorIcon(int width, int height, int[] rgb) {
        super(width, height, null);
        this.rgb = rgb;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    @Override
    protected void paintIcon(Component c, Graphics2D gd) {
        Color color = new Color(getRgb()[0], getRgb()[1], getRgb()[2]);
        gd.setColor(color);
        gd.fillRoundRect(1, 1, width - 2, height - 2, 5, 5);
    }
}
