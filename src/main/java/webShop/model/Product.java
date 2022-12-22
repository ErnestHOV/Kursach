package webShop.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Product {

    private String name;
    private Integer cost;
    private String dateAdded;
    private Integer numberOfProduct;
    private String description;
    private String category;

    public Product(String name, Integer cost, String dateAdded, Integer numberOfProduct, String description, String category) {
        this.name = name;
        this.cost = cost;
        this.dateAdded = dateAdded;
        this.numberOfProduct = numberOfProduct;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(Integer numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return category;
    }

    public void setCategories(String categories) {
        this.category = categories;
    }

    public static Comparator<Product> costComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {
            Integer cost1 = p1.getCost();
            Integer cost2 = p2.getCost();
            return cost1.compareTo(cost2);
        }
    };

    public static Comparator<Product> alphComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {
            String name1 = p1.getName();
            String name2 = p2.getName();
            return name1.compareTo(name2);
        }
    };

    public static Comparator<Product> dateComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null, date2 = null;
            try {
                date1 = dateFormat.parse(p1.getDateAdded());
                date2 = dateFormat.parse(p2.getDateAdded());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1.compareTo(date2);
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("&");
        sb.append(cost).append("&");
        sb.append(dateAdded).append("&");
        sb.append(numberOfProduct).append("&");
        sb.append(description).append("&");
        sb.append(category).append("\r\n");
        return sb.toString();
    }

}