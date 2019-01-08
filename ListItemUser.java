package e.lm280.myapplication.app;

import java.util.List;

public class ListItemUser {

    private String name;
    private String section;
    private String id;
    private String countbeer;

    public ListItemUser(){};

    public ListItemUser(String name,String section,String id){
        this.name = name;
        this.section = section;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSection() {
        return this.section;
    }

    public String getCountbeer(){
        return this.countbeer;
    }

    public void setName(String uName){

        this.name = uName;
    }

    public void setSection(String uSection){
        this.section = uSection;
    }

    public void setId(String uId){

        this.id = uId;
    }

    public void setCountbeer(String uCount){
        this.countbeer = uCount;
    }
}
