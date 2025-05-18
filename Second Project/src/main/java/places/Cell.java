package places;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Cell {

    private Product product;
    private int cellSpace;
    private int occupiedSpace = 0;
    private String nameProduct;

    @JsonCreator
    public Cell(@JsonProperty("cell space") int cellSpace) {
        this.cellSpace = cellSpace;
    }

    public int getCellSpace(){
        return cellSpace;
    }

    public Product getProduct() {
        return product;
    }

    public int getOccupiedSpace(){
        return occupiedSpace;
    }
    public void setOccupiedSpace(int occupiedSpace){
        this.occupiedSpace = occupiedSpace;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product == null){
            this.nameProduct = null;
        }
        else {
            this.nameProduct = product.getName();
        }
    }

    public void setCellSpace(int cellSpace) {
        this.cellSpace = cellSpace;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return cellSpace == cell.cellSpace && occupiedSpace == cell.occupiedSpace && Objects.equals(product, cell.product) && Objects.equals(nameProduct, cell.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, cellSpace, occupiedSpace, nameProduct);
    }
}
