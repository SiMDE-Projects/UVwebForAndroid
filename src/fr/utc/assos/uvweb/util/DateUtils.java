package fr.utc.assos.uvweb.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * A helper class to manage Dates
 */
public class DateUtils {
    private static final PeriodFormatter sYearFormatter = new PeriodFormatterBuilder()
            .appendYears()
            .appendSuffix(" an", " ans")
            .toFormatter();
    private static final PeriodFormatter sMonthFormatter = new PeriodFormatterBuilder()
            .appendMonths()
            .appendSuffix(" mois")
            .toFormatter();
    private static final PeriodFormatter sDayFormatter = new PeriodFormatterBuilder()
            .appendDays()
            .appendSuffix(" jour", " jours")
            .toFormatter();
    private static final PeriodFormatter sHourFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix(" heure", " heures")
            .toFormatter();
    private static final PeriodFormatter sHourMinuteFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix(" heure", " heures")
            .appendSeparator(" et ")
            .appendMinutes()
            .appendSuffix(" minute", " minutes")
            .toFormatter();
    private static final PeriodFormatter sMinuteSecondFormatter = new PeriodFormatterBuilder()
            .appendMinutes()
            .appendSuffix(" minute", " minutes")
            .appendSeparator(" et ")
            .appendSeconds()
            .appendSuffix(" seconde", " secondes")
            .toFormatter();
    private static final int HOUR_THRESHOLD = 5;

    public static String getFormattedDateDifference(DateTime d1, DateTime d2) {
        final Period period = new Period(d1, d2).normalizedStandard();

        if (period.getYears() > 0) {
            return sYearFormatter.print(period);
        }
        if (period.getMonths() > 0) {
            return sMonthFormatter.print(period);
        }
        if (period.getDays() > 0) {
            return sDayFormatter.print(period);
        }
        final int hours = period.getHours();
        if (hours > 0) {
            if (hours > HOUR_THRESHOLD) {
                return sHourFormatter.print(period);
            }
            return sHourMinuteFormatter.print(period);
        }
        return sMinuteSecondFormatter.print(period);
    }
}
