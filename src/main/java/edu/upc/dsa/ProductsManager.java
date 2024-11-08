package edu.upc.dsa;

import edu.upc.dsa.models.Products;
import java.util.List;

public interface ProductsManager {

    public Products addProduct(int id, String nameProduct, int price);
    public Products addProduct(String nameProduct, int price);
    public Products addProduct(Products o);
    public int size();
    public List<Products> getProducts();

    //futures implementacions
    //public Object getObject(String id);
    //public void deleteObject(String id);
    //public Object updateObject(Object o);
    //public void clear();

}
