package bg.softuni.exam.healthNutrition.Rest.model.DTO;


public class ProductInCartDTO {

    private String name;

    private Double price;

    private int quantity;

    public ProductInCartDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity++;

    }
    public void decreaseQuantity() {
        if (this.quantity >1) {
            this.quantity--;
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
