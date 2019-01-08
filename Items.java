package e.lm280.myapplication.app;

public class Items {

    private String username;
    private String date;
    private String week;

    public Items(String name, String date, int i){

    }

    public Items(String name,String date, String week){
        this.username = name;
        this.date = date;
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public String getUsername(){
        return username;
    }

    public String getWeek(){
        return week;
    }
}
