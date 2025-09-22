import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RateGenerator {

    static class Rate {
        private String occupancyDate;
        private String rateCode;
        private String currencyCode;
        private String roomTypeCode;
        private double singleRate;
        private double doubleRate;
        private double additionalChildRate;
        private double additionalAdultRate;
        private boolean taxExcluded;

        public Rate(String occupancyDate, String rateCode, String currencyCode, String roomTypeCode,
                    double singleRate, double doubleRate,
                    double additionalChildRate, double additionalAdultRate, boolean taxExcluded) {
            this.occupancyDate = occupancyDate;
            this.rateCode = rateCode;
            this.currencyCode = currencyCode;
            this.roomTypeCode = roomTypeCode;
            this.singleRate = singleRate;
            this.doubleRate = doubleRate;
            this.additionalChildRate = additionalChildRate;
            this.additionalAdultRate = additionalAdultRate;
            this.taxExcluded = taxExcluded;
        }

        @Override
        public String toString() {
            return String.format(
                    "{\n  \"occupancyDate\": \"%s\",\n  \"rateCode\": \"%s\",\n  \"currencyCode\": \"%s\",\n  \"roomTypeCode\": \"%s\",\n  \"singleRate\": %.2f,\n  \"doubleRate\": %.2f,\n  \"additionalChildRate\": %.2f,\n  \"additionalAdultRate\": %.2f,\n  \"taxExcluded\": %s\n}",
                    occupancyDate, rateCode, currencyCode, roomTypeCode,
                    singleRate, doubleRate, additionalChildRate, additionalAdultRate, taxExcluded
            );
        }
    }

    public static void main(String[] args) {
        List<Rate> rateList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2025, 7, 20);

        for (int i = 0; i < 100; i++) {
            LocalDate date = startDate.plusDays(i);
            double singleRate = 105.00 + i;  // increases by 1 each day
            double doubleRate = 300.00 + i * 2; // increases by 2 each day

            Rate rate = new Rate(
                    date.toString(),
                    "BAR",
                    "EUR",
                    "STD",
                    singleRate,
                    doubleRate,
                    0.00,
                    0.00,
                    false
            );

            rateList.add(rate);
        }

        // Print all rates
        rateList.forEach(System.out::println);
    }
}
