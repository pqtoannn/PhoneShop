package Custom.calendar.utils;

import java.awt.event.MouseEvent;
import Custom.calendar.model.ModelDate;


public interface CalendarSelectedListener {

    public void selected(MouseEvent evt, ModelDate date);
}
