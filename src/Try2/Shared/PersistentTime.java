package Try2.Shared;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class PersistentTime implements Serializable {
    private final Date time;
    public PersistentTime() {
        time = Calendar.getInstance().getTime();
    }
    public Date getTime() {
        return time;
    }
}
