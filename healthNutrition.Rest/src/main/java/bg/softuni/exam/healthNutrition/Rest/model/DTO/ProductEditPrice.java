package bg.softuni.exam.healthNutrition.Rest.model.DTO;

import jakarta.validation.constraints.Positive;

public class ProductEditPrice {
    @Positive
    private Double price;

    public ProductEditPrice() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
