package com.example.lak.gardenifycustomer;

import java.util.ArrayList;

/**
 * Created by Lak on 08-10-2018.
 */

public class Provider {
    String name;
    String password;
    String address1;
    String city;
    String mobileno;
    ArrayList<String> skills;
    ArrayList<String> pendingorder;
    ArrayList<String> confirmorder;

    public Provider(){
        skills=new ArrayList<>();
        pendingorder=new ArrayList<>();
        confirmorder=new ArrayList<>();

    }

    void set(String name,String password,String address1,String city,String mobileno)
    {
        this.name=name;
        this.password=password;
        this.address1=address1;
        this.city=city;
        this.mobileno=mobileno;
        skills = new ArrayList<>();
        skills.add("\t Current Skills:-");
        pendingorder = new ArrayList<>();
        pendingorder.add("\t Current Skills:-");
        confirmorder = new ArrayList<>();
        confirmorder.add("\t Current Skills:-");
        //skills.add("test1");
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getAddress1(){
        return address1;
    }
    public String getMobileno(){
        return mobileno;
    }
    public String getCity(){
        return city;
    }

    void addskills(String skill){
        skills.add(skill);
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public ArrayList<String> getConfirmorder() {
        return confirmorder;
    }

    public ArrayList<String> getPendingorder() {
        return pendingorder;
    }
    void addpendingorder(String pendingorder){
        this.pendingorder.add(pendingorder);
    }
    void addconfirmorder(String confirmorder){
        this.confirmorder.add(confirmorder);
    }
}
