import java.util.List;

public class FeeCalculator {
    public static final String WORKSHOP = "Workshop - RM20";
    public static final String SEMINAR = "Seminar - RM15";
    public static final String COMPETITION = "Competition - RM30";

    public static final String CERTIFICATE = "Certificate - RM5";
    public static final String FOOD_PACKAGE = "Food Package - RM10";
    public static final String EVENT_TSHIRT = "Event T-Shirt - RM25";

    public double calculateTotalFee(String eventCategory, List<String> addOns) {
        double total = getEventFee(eventCategory);

        for (String addOn : addOns) {
            total += getAddOnFee(addOn);
        }

        return total;
    }

    public double getEventFee(String eventCategory) {
        if (WORKSHOP.equals(eventCategory)) {
            return 20.00;
        }
        if (SEMINAR.equals(eventCategory)) {
            return 15.00;
        }
        if (COMPETITION.equals(eventCategory)) {
            return 30.00;
        }
        return 0.00;
    }

    public double getAddOnFee(String addOn) {
        if (CERTIFICATE.equals(addOn)) {
            return 5.00;
        }
        if (FOOD_PACKAGE.equals(addOn)) {
            return 10.00;
        }
        if (EVENT_TSHIRT.equals(addOn)) {
            return 25.00;
        }
        return 0.00;
    }
}
