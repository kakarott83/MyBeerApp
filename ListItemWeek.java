package e.lm280.myapplication.app;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SpinnerAdapter;

public class ListItemWeek {

    private String week;
    private String item;
    private String user;

    public String getWeek(){
        return this.week;
    };

    public String getItem(){
        return this.item;
    };

    public String getUsers(){
        return this.user;
    };

    public void setWeek(String week){
        this.week = week;
    };

    public void setItem(String item){
        this.item = item;
    };

    public void setUser(String user){
        this.user = user;
    };

}
