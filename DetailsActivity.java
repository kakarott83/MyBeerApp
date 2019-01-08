package e.lm280.myapplication.app;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private ImageButton btnCounter;
    private ImageButton btnDelete;
    private TextView tvName;
    private TextView tvSection;
    private TextView tvCounter;
    private TextView tvCountBeerThisWeek;
    private TextView tvCountBeerThisMonth;
    private TextView tvCountBeerThisYear;
    private Integer count = 0;
    private Integer countweek = 0;
    private Integer countmonth = 0;
    private Integer countyear = 0;
    private Calendar calender = Calendar.getInstance();
    private SimpleDateFormat datFormat = new SimpleDateFormat( "dd.MM.yyyy" );
    private GregorianCalendar gCalendar;
    private DatenbankManager mHelper;
    private SQLiteDatabase mDatabase;
    private String name;
    private String section;
    private String id;
    private String CountThisWeek;
    private String CountThisMonth;
    private String CountThisYear;
    private Boolean delete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details );

        //Elemente
        btnCounter = (ImageButton) findViewById( R.id.btnCounter );
        btnDelete = (ImageButton) findViewById( R.id.btnDelete );
        tvName = (TextView) findViewById( R.id.editTextName );
        tvSection = (TextView) findViewById( R.id.editTextSection );
        tvCounter = (TextView) findViewById( R.id.textViewCounter );
        tvCountBeerThisWeek = (TextView) findViewById( R.id.tvCountBeerThisWeek );
        tvCountBeerThisMonth = (TextView) findViewById( R.id.tvCountBeerThisMonth );
        tvCountBeerThisYear = (TextView) findViewById( R.id.tvCountBeerThisYear );



        //Datenbank instanziieren
        mHelper = new DatenbankManager( getApplicationContext() );


        //Content auslesen
        Intent values = getIntent();

        name = values.getStringExtra( "name" );
        section = values.getStringExtra( "section" );
        id = values.getStringExtra( "id" );

        tvName.setText( name );
        tvSection.setText( section );

        //Methoden
        setCounter();

        btnCounter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playSound();

                mHelper.insertItem( name,id,datFormat.format( calender.getTime() ) );

                //Testing
                //mHelper.insertItem( name,id,"30.12.2018" );
                //mHelper.insertItem( name,id,"31.12.2018" );
                //mHelper.insertItem( name,id,"11.11.2018" );

                Toast.makeText( getApplicationContext(),"Neues Bier hinzugefügt",Toast.LENGTH_SHORT ).show();
                count = counterItems( name,datFormat.format( calender.getTime() ),0);
                setCounter();
            }
        } );

        btnDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog( v );

            }
        } );

        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

    }

    private int counterItems(String uname, String udate, int interval){

        ArrayList<HashMap<String,String>> itemList = mHelper.GetItems();

        DateFormat formatter = new SimpleDateFormat( "dd.MM.yyyy" );
        Date firstDayOfWeek = new Date();
        Date currData = new Date();
        Date itemDate = new Date();

        ArrayList<ListItemItem> itemUser = new ArrayList<>(  );

        for (int i = 0; i < itemList.size(); i++){

            try{
                currData = (Date)formatter.parse( udate ); //aktuelles Datum
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try{
                firstDayOfWeek = (Date)formatter.parse( getDateFirstWeek(interval) ); //Anfang der Woche
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try{
                itemDate = (Date)formatter.parse( itemList.get( i ).get( "date" ) ); //Items Datum
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (itemList.get( i ).get( "username" ).equals( uname.toString() ) && (itemDate.after( firstDayOfWeek ) || itemDate.equals( firstDayOfWeek ) ))
            {

                ListItemItem item = new ListItemItem(
                        itemList.get( i ).get( "id" ),
                        itemList.get( i ).get( "userid" ),
                        itemList.get( i ).get( "date" ),
                        itemList.get( i ).get( "username" )
                );
                itemUser.add( item );
            }

        }

        return itemUser.size();
    }

    private String getDateFirstWeek(int interval){

        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance(Locale.GERMAN);

        //Aktuelles Datum formatieren
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy",Locale.GERMAN);

        //Datum für Anfang der Woche setzen
        switch (interval)
        {
            case 1:
                cal.set(cal.YEAR,Integer.valueOf( formatYear.format( cal.getTime() ) ) );
                cal.set( cal.WEEK_OF_YEAR,cal.WEEK_OF_YEAR  + 1 );
                cal.set( cal.DAY_OF_WEEK,cal.MONDAY );
                Log.d( "Diese Woche",datFormat.format( cal.getTime() ) );
                break;

            case 2:
                cal.set(cal.YEAR,Integer.valueOf( formatYear.format( cal.getTime() ) ) );
                cal.set( cal.MONTH,cal.get( cal.MONTH ) );
                cal.set( cal.DAY_OF_MONTH,1 );
                Log.d( "Diesen Monat",datFormat.format( cal.getTime() ) );
                break;

            case 3:
                cal.set(cal.YEAR,Integer.valueOf( formatYear.format( cal.getTime() ) ) );
                cal.set(cal.MONTH,1);
                cal.set( cal.DAY_OF_YEAR,1 );
                Log.d( "Diese Jahr",datFormat.format( cal.getTime() ) );
                break;

                default:
                    break;

        }

        return datFormat.format( cal.getTime() );
    }

    private void setCounter(){

        //Heute
        count = counterItems( name,datFormat.format( calender.getTime() ),0);
        tvCounter.setText( count.toString() );

        //Diese Woche
        countweek = counterItems( name,datFormat.format( calender.getTime() ),1);
        tvCountBeerThisWeek.setText( " " + countweek.toString() );

        //Diesen Monat
        countmonth = counterItems( name,datFormat.format( calender.getTime() ),2);
        tvCountBeerThisMonth.setText(  " " +  countmonth.toString() );

        //Dieses Jahr
        countyear = counterItems( name,datFormat.format( calender.getTime() ),3);
        tvCountBeerThisYear.setText( " " +  countyear.toString() );

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
        Intent goBack = new Intent( DetailsActivity.this,MainActivity.class );
        startActivity( goBack );
    }

    private void showDialog(View view){
        AlertDialog.Builder deleteUserDialogBuilder = new AlertDialog.Builder( this );
        deleteUserDialogBuilder.setMessage( "Willst du den User wirklich löschen?" );
        deleteUserDialogBuilder.setPositiveButton( "Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHelper.deleteUser( Integer.valueOf( id ) );
                Toast.makeText( getBaseContext(),"User " + name + " wurde gelöscht",Toast.LENGTH_SHORT ).show();
                Intent back = new Intent( DetailsActivity.this,MainActivity.class );
                startActivity( back );
            }
        } );
        deleteUserDialogBuilder.setNegativeButton( "Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete = false;
            }
        } );

        AlertDialog deleteUser = deleteUserDialogBuilder.create();
        deleteUser.show();
    }

    private void playSound(){

        MediaPlayer mp = MediaPlayer.create( this,R.raw.bieropen );
        mp.start();
    }
}
