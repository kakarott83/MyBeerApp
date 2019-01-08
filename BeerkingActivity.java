package e.lm280.myapplication.app;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class BeerkingActivity extends AppCompatActivity {

    private StarAnimationView mAnimationView;
    private DatenbankManager mHelper;
    private TextView tvleadername;
    private TextView tvcount;
    private SQLiteDatabase mDatabase;
    private int leadercount = 0;
    private String leaderName = "";
    private String leaderCount = "";
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_beerking);
        mAnimationView = (StarAnimationView) findViewById(R.id.animated_view);
        //findViewById(R.id.btn_pause).setOnClickListener(this);
        //findViewById(R.id.btn_resume).setOnClickListener(this);

        tvleadername = (TextView) findViewById( R.id.tvleadername );
        tvcount = (TextView) findViewById( R.id.tvcount );

        //Datenbank instanziieren
        mHelper = new DatenbankManager( this );

        //MediaPlayer instanziieren
        mp = MediaPlayer.create( this,R.raw.applaus );

        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        //Methoden
        getBeerKing();
        playApplaus( true,mp );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimationView.resume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mAnimationView.pause();
        playApplaus( false,mp);
    }


    private void getBeerKing(){


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

        //Alle User ermitteln
        for (int i = 0; i < itemList.size(); i++){

            if (!user.contains( itemList.get( i ).getUsername() )){
                user.add( itemList.get( i ).getUsername() );
            }

        }

        ArrayList<HashMap<String,String>> leader = new ArrayList<HashMap<String, String>>(  );

        for (int i = 0; i < user.size(); i++){

            int counter = 0;

            //Anzahl an Bier
            for (int j = 0; j < itemList.size(); j++){

                if (user.get( i ).equals( itemList.get( j ).getUsername() )){
                    counter++;
                }

            }

            if (counter >= leadercount){

                 if (counter > leadercount){
                     leader.clear();
                     HashMap<String,String> map = new HashMap<String, String>(  );
                     map.put( "name",user.get( i ) );
                     map.put( "counter",String.valueOf( counter ) );
                     leadercount = counter;
                     leader.add( map );
                 } else{
                     HashMap<String,String> map = new HashMap<String, String>(  );
                     map.put( "name",user.get( i ) );
                     map.put( "counter",String.valueOf( counter ) );
                     leader.add( map );
                 }
            }
         }

         //Namen setzen
        for (int i = 0; i < leader.size(); i++){

            if (leaderName.length() > 0){
                leaderName = leaderName +", " +leader.get( i ).get( "name" );
            }
            else{
                leaderName = leader.get( i ).get( "name" );
                leaderCount = leader.get( i ).get( "counter" );
            }
        }

        if (leaderName.length() > 0){
            tvleadername.setText( leaderName );
            tvcount.setText( leaderCount );
        }else{
            tvleadername.setText( "Karli Kaufmann" );
            tvcount.setText( "0" );
        }
    }

    private void playApplaus(boolean start, MediaPlayer mp){

        if (start){
            mp.start();
        }else{
            mp.stop();
        }
    }
}
