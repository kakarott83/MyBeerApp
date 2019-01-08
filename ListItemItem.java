package e.lm280.myapplication.app;

public class ListItemItem {

    private String date;
    private String username;
    private String userid;
    private String id;

    public ListItemItem(){};

    public ListItemItem (String id, String userid, String username, String date){
        this.date = date;
        this.userid = userid;
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid() {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

}
