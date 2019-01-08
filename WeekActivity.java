package e.lm280.myapplication.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class WeekActivity extends AppCompatActivity {

    private DatenbankManager mHelper;
    private SQLiteDatabase mDatabase;
    private ImageButton btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_week );

        //Elemente
        final ListView listViewWeek = (ListView) findViewById( R.id.listViewWeek );

        //Datenbank instanziieren
        mHelper = new DatenbankManager( this );

        getWeek( listViewWeek );

        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

    }

    private void getWeek(ListView listViewWeek){

        //Lokale Variable
        int year;

        //Werte aus DB ermitteln
        ArrayList<HashMap<String,String>> dbItemList = mHelper.GetItems();


        Calendar calendar = Calendar.getInstance(Locale.GERMAN);
        DateFormat formatter = new SimpleDateFormat( "dd.MM.yyyy" );
        ArrayList<Items> itemList = new ArrayList<Items>(  );
        ArrayList<String> weeks = new ArrayList<>(  );
        ArrayList<String> user = new ArrayList<>(  );
        ArrayList<HashMap<String,String>> values = new ArrayList<HashMap<String,String>>(  );

        //Items erzeugen
        for (int i = 0; i < dbItemList.size(); i++)
        {

            String[] datValues = dbItemList.get( i ).get( "date" ).split( "\\." );
            calendar.set( Integer.parseInt( datValues[2] ),Integer.parseInt( datValues[1] ) - 1,Integer.parseInt( datValues[0] ));
            year = calendar.get( calendar.YEAR );

            //Ende des Jahres neue Woche beginnt
            if (calendar.get( Calendar.MONTH ) == Calendar.DECEMBER && calendar.get( calendar.WEEK_OF_YEAR ) == 1 ){
                year = year + 1;
            }

            Items item = new Items(
                    dbItemList.get( i ).get( "username" ),
                    dbItemList.get( i ).get( "date" ),
                    year + "_" + calendar.get( calendar.WEEK_OF_YEAR )
            );

            itemList.add( item );

            //Weeks unique
            if (!weeks.contains( item.getWeek() )){
                weeks.add( item.getWeek() );
            }

            Log.d("Datum", formatter.format( calendar.getTime() ) + "_" + calendar.get( calendar.WEEK_OF_YEAR ) + "_" + year);

        }

        for (String week : weeks)
        {
            int countItems = 0;
            user.clear();
            Log.d( "Week",week );

            for(int j = 0; j < itemList.size(); j++){

                Items item = itemList.get( j );

                if (item.getWeek().equals( week ))
                {
                    countItems ++;

                    if (!user.contains( item.getUsername() ))
                    {
                        user.add( itemList.get( j ).getUsername() );
                        Log.d( "User",itemList.get( j ).getUsername() );
                    }
                }

            }
            HashMap<String,String> map = new HashMap<String, String>(  );
            map.put( "week",week );
            map.put( "countItems",String.valueOf( countItems ) );
            map.put( "countUser",String.valueOf( user.size() ) );
            values.add( map );

        }

        //View erzeugen

        ArrayList<ListItemWeek> li = new ArrayList<>(  );

        for (int i = 0; i < values.size(); i++)
        {

            ListItemWeek week = new ListItemWeek();

            week.setWeek( values.get( i ).get( "week" ) );
            week.setItem( "Getrunkene Bier: " + values.get( i ).get( "countItems" ) );
            week.setUser( "Anzahl Mitarbeiter: " + values.get( i ).get( "countUser" ) );

            li.add( week );

        }

        listViewWeek.setAdapter( new CustomListViewAdaperWeeks( this,li ) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_new:
                goBack();
                break;
        }
        return super.onOptionsItemSelected(item); //To change body of generated methods, choose Tools | Templates.
    }

    private void goBack(){
        Intent goBack = new Intent( WeekActivity.this,MainActivity.class );
        startActivity( goBack );
    }

}
