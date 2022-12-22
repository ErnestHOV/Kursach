package webShop.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import webShop.exception.WebShopException;
import webShop.model.Product;

public class ReadProducts {

    private BufferedReader br;
    private String file;

    public ReadProducts(String file){
        this.file = file;
    }

    public ArrayList<Product> readFile() throws IOException {
        open();
        ArrayList<Product> res = new ArrayList<Product>();
        String line = null;
        while((line = br.readLine()) != null) {
            String[] data = new String[6]; int i = 0;
            Scanner scan = new Scanner(line);
            scan.useDelimiter("&");
            while (scan.hasNext()) {
                data[i] = scan.next();
                i++;
            }
            if(i != 6) {
                System.err.println("incorrect data base format| not all arguments of product " + data.length + "!");
                throw new WebShopException("incorrect data base format| not all arguments of product " + i + " " + line);
            }
            String name = data[0];
            Integer cost = 0;
            try {
                Scanner scanner = new Scanner(data[1]);
                int s = scanner.nextInt();
                cost = s;
            }catch(NumberFormatException ex) {
                System.err.println("incorrect data base format| second argument has to be a cost");
                throw new WebShopException("incorrect data base format| second argument has to be a cost");
            }
            String dateAdded = data[2];
            Integer numberOfProduct = 0;
            try {
                Scanner scanner = new Scanner(data[3]);
                int s = scanner.nextInt();
                numberOfProduct = s;
            }catch(NumberFormatException ex) {
                System.err.println("incorrect data base format| third argument has to be a number of products");
                throw new WebShopException("incorrect data base format| third argument has to be a number of products");
            }
            String description = data[4];
            String category = data[3];
            res.add(new Product(name, cost, dateAdded, numberOfProduct, description, category));

        }

        close();
        return res;
    }

    public void close() throws IOException {
        br.close();
    }

    public void open() throws FileNotFoundException {
        br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)));
    }

}