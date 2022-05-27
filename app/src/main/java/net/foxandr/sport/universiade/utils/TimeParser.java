package net.foxandr.sport.universiade.utils;

import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.zone.ZoneRules;

public class TimeParser {

    public static OffsetDateTime getOffsetDateTimeFromString(String stringInstant) {
        ZoneId zone = ZoneId.systemDefault();
        ZoneRules rules = zone.getRules();
        Instant instant = Instant.now();
        ZoneOffset offset = rules.getOffset(instant);
        return Instant.parse(stringInstant).atOffset(offset);
    }

    public static String getFormattedOffsetDataTimeFromString(String stringInstant,
                                                              DateTimeFormatter formatter) {

        OffsetDateTime offsetDataTime = getOffsetDateTimeFromString(stringInstant);
        return offsetDataTime.format(formatter);
    }


}
