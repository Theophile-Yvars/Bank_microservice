package al.newbank.d.Marketing.components;

import java.time.LocalDate;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class SeasonDetecter {


    public String isInSeasonalPeriod(LocalDate currenDate) {
        if (currenDate == null) {
            return "null";
        }
        if(this.isChristmas(currenDate)){
            return "Christmas";
        }
        if(this.isSummer(currenDate)){
            return "Summer";
        }
        if(this.isBackToSchool(currenDate)){
            return "BackToSchool";
        }
        if(this.isSpring(currenDate)){
            return "Spring";
        }
        return "null";
    }

    public boolean isChristmas(LocalDate currentDate) {
        LocalDate christmasStart = LocalDate.of(currentDate.getYear(), 11, 1);
        LocalDate christmasEnd = LocalDate.of(currentDate.getYear(), 12, 31);
        return currentDate.isAfter(christmasStart) && currentDate.isBefore(christmasEnd);
    }

    public boolean isSummer(LocalDate currentDate) {
        LocalDate summerStart = LocalDate.of(currentDate.getYear(), 6, 1);
        LocalDate summerEnd = LocalDate.of(currentDate.getYear(), 8, 31);
        return currentDate.isAfter(summerStart) && currentDate.isBefore(summerEnd);
    }

    public boolean isBackToSchool(LocalDate currentDate) {
        LocalDate backToSchoolStart = LocalDate.of(currentDate.getYear(), 9, 1);
        LocalDate backToSchoolEnd = LocalDate.of(currentDate.getYear(), 9, 30);
        return currentDate.isAfter(backToSchoolStart) && currentDate.isBefore(backToSchoolEnd);
    }


    public boolean isSpring(LocalDate currentDate) {
        LocalDate springStart = LocalDate.of(currentDate.getYear(), 3, 1);
        LocalDate springEnd = LocalDate.of(currentDate.getYear(), 5, 31);
        return currentDate.isAfter(springStart) && currentDate.isBefore(springEnd);
    }
    
}
