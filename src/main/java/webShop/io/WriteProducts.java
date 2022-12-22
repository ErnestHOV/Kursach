package webShop.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import webShop.model.Product;

public class WriteProducts {

    private BufferedWriter bw;
    private String file;

    public WriteProducts(String file){
        this.file = file;
    }

    public void writeProducts(ArrayList<Product> products) throws IOException {
        open();
        for(Product p : products) {
            bw.write(p.toString());
            System.out.println("Writing product: "+p.getName());
        }
        close();
    }

    public void close() throws IOException {
        bw.close();
    }

    public void open() throws IOException {
        bw = new BufferedWriter(
                new OutputStreamWriter(
                        Files.newOutputStream(Paths.get(file))));
    }

}