package e.lm280.myapplication.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomListViewAdapter extends BaseAdapter {

    private ArrayList<ListItemUser> arrayList;
    private LayoutInflater layoutInflater;
    private List<ListItemUser> userList = null;
    private Context mContext;

    public CustomListViewAdapter(Context aContext, List<ListItemUser> listData){
        mContext = aContext;
        this.userList = listData;
        layoutInflater = LayoutInflater.from( mContext );
        this.arrayList = new ArrayList<ListItemUser>(  );
        this.arrayList.addAll( userList );
    }

    @Override
    public int getCount() {

        return userList.size();
    }

    @Override
    public Object getItem(int position) {

        return userList.get( position );
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null){
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById( R.id.name );
            holder.uSection = (TextView) v.findViewById( R.id.section );
            //holder.uId = (TextView) v.findViewById( R.id._id );
            v.setTag( holder );
        } else{
            holder = (ViewHolder) v.getTag();
        }

        holder.uName.setText( userList.get( position ).getName() );
        holder.uSection.setText( userList.get( position ).getSection() );
        //holder.uId.setText( userList.get( position ).getId() );

        v.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent( mContext,DetailsActivity.class );
                intent.putExtra( "id",(userList.get( position ).getId()));
                intent.putExtra( "name",(userList.get( position ).getName()));
                intent.putExtra( "section",(userList.get( position ).getSection()));
                mContext.startActivity( intent );
            }
        } );
        return v;

    }


    static class ViewHolder {
        TextView uName;
        TextView uSection;
        TextView uId;
    }

    //Filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.GERMAN );
        userList.clear();

        if (charText.length() == 0){
            userList.addAll( arrayList );
        }else{

            for (ListItemUser user : arrayList){

                if (user.getName().toLowerCase( Locale.GERMAN ).contains( charText )){
                    userList.add( user );
                }
            }

        }
        notifyDataSetChanged();
    }

}
