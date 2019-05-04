/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author NetLenovo
 */
public class Region {
    private int id;
    private String reg_desc;

    public Region() {
    }

    public Region(int id, String reg_desc) {
        this.id = id;
        this.reg_desc = reg_desc;
    }

    public Region(String reg_desc) {
        this.reg_desc = reg_desc;
    }

    @Override
    public String toString() {
        return "Region{" + "id=" + id + ", reg_desc=" + reg_desc + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReg_desc() {
        return reg_desc;
    }

    public void setReg_desc(String reg_desc) {
        this.reg_desc = reg_desc;
    }
    
    
}
