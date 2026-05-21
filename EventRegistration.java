import java.util.ArrayList;
import java.util.List;

public class EventRegistration {
    private Student student;
    private String eventCategory;
    private List<String> addOns;
    private String paymentMethod;
    private double totalFee;

    public EventRegistration(Student student, String eventCategory, List<String> addOns,
                             String paymentMethod, double totalFee) {
        this.student = student;
        this.eventCategory = eventCategory;
        this.addOns = new ArrayList<>(addOns);
        this.paymentMethod = paymentMethod;
        this.totalFee = totalFee;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public List<String> getAddOns() {
        return new ArrayList<>(addOns);
    }

    public void setAddOns(List<String> addOns) {
        this.addOns = new ArrayList<>(addOns);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }
}
