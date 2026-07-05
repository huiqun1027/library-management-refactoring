import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    public static final long MILLISECONDS_IN_DAY = 1000L * 60 * 60 * 24;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static long calculateOverdueDays(Date dueDate) {
        if (dueDate == null) {
            return 0;
        }
        Date today = new Date();
        if (dueDate.before(today)) {
            return (today.getTime() - dueDate.getTime()) / MILLISECONDS_IN_DAY;
        }
        return 0;
    }

    public static Date calculateDueDate(int borrowDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, borrowDays);
        return cal.getTime();
    }

    public static String formatDate(Date date) {
        if (date == null) return "N/A";
        return dateFormat.format(date);
    }
}
