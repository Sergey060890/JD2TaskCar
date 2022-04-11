package entity;

import java.util.Objects;

/**
 * Entity class car.
 */
@MyTable(name = "car")
public class Car {
    /**
     * Identifier field.
     */
    @MyColumn(name = "identifier")
    private Integer identifier;
    /**
     * Brand field.
     */
    @MyColumn(name = "Brand")
    private String brand;
    /**
     * Color field.
     */
    @MyColumn(name = "Color")
    private String color;
    /**
     * Price field.
     */
    @MyColumn(name = "Price")
    private Integer price;

    /**
     * Constructor for car class.
     *
     * @param incomingIdentifier Incoming identifier
     * @param incomingBrand      Incoming brand
     * @param incomingColor      Incoming color
     * @param incomingPrice      Incoming price
     */

    public Car(final Integer incomingIdentifier,
               final String incomingBrand,
               final String incomingColor,
               final Integer incomingPrice) {
        this.identifier = incomingIdentifier;
        this.brand = incomingBrand;
        this.color = incomingColor;
        this.price = incomingPrice;
    }

    /**
     * Default constructor.
     */
    public Car() {
    }

    /**
     * Getter for field identifier.
     *
     * @return identifier
     */
    public Integer getId() {
        return identifier;
    }

    /**
     * Getter for field brand.
     *
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Getter for field color.
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter for field price.
     *
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param o incoming object to compare
     * @return true if equals, else false
     */

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(identifier, car.identifier)
                && Objects.equals(brand, car.brand)
                && Objects.equals(color, car.color)
                && Objects.equals(price, car.price);
    }

    /**
     * @return Hash of fields of this class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(identifier, brand, color, price);
    }

    /**
     * @return String with fields names and their values.
     */
    @Override
    public String toString() {
        return "CAR{"
                + "identifier=" + identifier
                + ", brand='" + brand + '\''
                + ", color='" + color + '\''
                + ", price='" + price + '\''
                + '}';
    }
}

