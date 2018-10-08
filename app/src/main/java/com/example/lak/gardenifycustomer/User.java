package com.example.lak.gardenifycustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lak on 07-10-2018.
 */

public class User {
    String name;
    String password;
    String phoneno;
    String address;
    String email;
    ArrayList<String> cart;
    ArrayList<String> pendingorders;
    ArrayList<String> confirmorders;
    ArrayList<String> declineorders;

    public User() {
        cart=new ArrayList<>();
        pendingorders=new ArrayList<>();
        confirmorders=new ArrayList<>();
        declineorders=new ArrayList<>();

    }
    /*public User(String name,String password){
        cart=new ArrayList<>();
        this.name=name;
        this.password=password;

    }*/

    void set(String name,String password,String phoneno,String address,String email)
    {
        this.name=name;
        this.password=password;
        this.phoneno=phoneno;
        this.address=address;
        this.email=email;
    }

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public ArrayList<String> getCart()
    {
        return cart;
    }
    public String getPhoneno(){
        return phoneno;
    }
    public String getAddress(){
        return address;
    }
    public String getEmail(){
        return address;
    }
    public void addToCart(String order){
        cart.add(order);

    }
    public ArrayList<String> getPendingorders(){
        return pendingorders;
    }
    public ArrayList<String> getConfirmorders(){
        return confirmorders;
    }
    public void addpendingorder(String order){
        pendingorders.add(order);
    }
    public void addconfirmorder(String order){
        pendingorders.add(order);
    }
    public void removerpendingorder(String order){
        pendingorders.remove(order);
    }
    public void removerfromcart(String order){
        cart.remove(order);
    }
    public void addtodeclineorder(String order){
        declineorders.add(order);
    }
    public ArrayList<String> getDeclineorders(){
        return declineorders;
    }
}
