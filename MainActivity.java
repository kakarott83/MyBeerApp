package e.lm280.myapplication.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatenbankManager mHelper;
    private SQLiteDatabase mDatabase;
    private EditText editsearch;
    private CustomListViewAdapter adapter;
    private ImageButton btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //Elemente
        final ListView listViewUser = (ListView) findViewById( R.id.listViewUser );
        editsearch = (EditText) findViewById( R.id.search );
        btnSearch= (ImageButton) findViewById(R.id.btnSearch );

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Datenbank instanziieren
        mHelper = new DatenbankManager( this );

        //UserListe erzeugen
        ArrayList<HashMap<String,String>> userList = mHelper.GetUser();

        final ArrayList<ListItemUser> li = new ArrayList<ListItemUser>(  );

        if (userList.size() == 0){

            ListItemUser user = new ListItemUser(
                    "Es konnten keine User ermittelt werden!",
                    "Neue User können über das Kontextmenü erfasst werden",
                    null);

            li.add( user );


        }

        for (int i = 0 ; i < userList.size() ; i++){

            ListItemUser user = new ListItemUser();
            ArrayList<HashMap<String,String>> countItems = new ArrayList<>(  );

            //countItems = getItemsByUser( userList.get( i ).get( "name" ) );

            user.setName( userList.get( i ).get( "name" ) );
            user.setId( userList.get( i ).get( "_id" ) );
            //user.setCountbeer( String.valueOf( countItems.size() ));
            user.setSection( userList.get( i ).get( "section" ) );

            li.add( user );
        }

        adapter = new CustomListViewAdapter( this,li );
        listViewUser.setAdapter( adapter );

        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );


        editsearch.addTextChangedListener( new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editsearch.getText().toString().toLowerCase(Locale.GERMAN );
                adapter.filter( text );
            }
        } );
    }


    @Override
    protected void onResume() {
        super.onResume();
        mDatabase = mHelper.getReadableDatabase();
        //Toast.makeText( this,"Datenbank geöffnet",Toast.LENGTH_SHORT ).show();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabase.close();
        //Toast.makeText( this,"Datenbank geschlossen",Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_adduser:
                addUser();
                break;
            case R.id.action_statistic:
                getStatistic();
                break;
            case R.id.action_beerking:
                getBeerKing();
                break;
            case R.id.action_setting:
                getSetting();
                break;
        }
        return super.onOptionsItemSelected(item); //To change body of generated methods, choose Tools | Templates.
    }

    private void getStatistic() {
        Intent weeks = new Intent( MainActivity.this,WeekActivity.class );
        startActivity( weeks );
    }

    private void addUser(){
        Intent userAdd = new Intent( MainActivity.this,UserActivity.class );
        startActivity( userAdd );
    }
    private void getBeerKing(){
        Intent beerking = new Intent( MainActivity.this,BeerkingActivity.class );
        startActivity( beerking );
    }
    private void getSetting(){
        Intent reset = new Intent( MainActivity.this,AdminActivity.class );
        startActivity( reset );
        //Toast.makeText( getBaseContext(),"Funktion noch nicht freigeschaltet",Toast.LENGTH_SHORT ).show();
    }

    private ArrayList<HashMap<String,String>> getItemsByUser(String name){
        ArrayList<HashMap<String,String>> itemsByUser = new ArrayList<>(  );
        itemsByUser = mHelper.GetItemsByUser( name );
        return itemsByUser;
    }


}
