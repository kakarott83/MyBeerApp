package e.lm280.myapplication.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListViewAdaperWeeks extends BaseAdapter {

    private ArrayList<ListItemWeek> listData;
    private LayoutInflater layoutInflater;

    public CustomListViewAdaperWeeks(Context aContext, ArrayList<ListItemWeek> listData){
        this.listData = listData;
        layoutInflater = LayoutInflater.from( aContext );

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder;

        if (v == null){
            v = layoutInflater.inflate( R.layout.list_row_weeks,null );
            holder = new ViewHolder();
            holder.uWeek = (TextView) v.findViewById( R.id.week );
            holder.uItem = (TextView) v.findViewById( R.id.item );
            holder.uUser = (TextView) v.findViewById( R.id.user );
            v.setTag( holder );
        }else {
            holder = (ViewHolder) v.getTag();
        }

        holder.uWeek.setText( listData.get( position ).getWeek() );
        holder.uItem.setText( listData.get( position ).getItem() );
        holder.uUser.setText( listData.get( position ).getUsers() );
        return v;
    }

    private static class ViewHolder {
        TextView uWeek;
        TextView uItem;
        TextView uUser;
    }
}
