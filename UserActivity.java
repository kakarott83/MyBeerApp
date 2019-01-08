package e.lm280.myapplication.app;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity {

    EditText username;
    Spinner usersection;
    ImageButton btnSave;
    private DatenbankManager mHelper;
    private SQLiteDatabase mDatabase;
    private boolean isExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );

        //Elemente
        username = (EditText) findViewById( R.id.editTextUserName );
        usersection = (Spinner) findViewById( R.id.spinnerUserSection );
        btnSave = (ImageButton) findViewById( R.id.btnSave );

        //Spinner befüllen
        final String[] section = {"Accounting","Frontoffice","Middleoffice","Backoffice","Projektleitung","Geschäftsführung","Innovation","Support","Testing","Projektmanagementoffice","Andere"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,section );
        adapterSpinner.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        usersection.setAdapter( adapterSpinner );


        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isExists = false;

                //Datenbank instanziieren
                mHelper = new DatenbankManager( getApplicationContext() );

                //User ermitteln
                ArrayList<HashMap<String,String>> user = new ArrayList<>(  );
                user = mHelper.GetUser();


                String name = username.getText().toString();
                String userSection = section[(int) usersection.getSelectedItemId()];

                //Feld muss gefüllt sein

                if (name.length() > 0 && userSection.length() > 0){

                    for (int i = 0; i < user.size(); i++){

                        if (user.get( i ).get( "name" ).toUpperCase().contains( name.toUpperCase() )){
                            isExists = true;
                        }
                    }

                    if (!isExists){
                        mHelper.insertUser( name,userSection );
                        Toast.makeText( getBaseContext(),"User: " + name + " in der Abteilung  " + userSection + " wurde hinzugefügt.", Toast.LENGTH_SHORT ).show();

                        //Zurück
                        Intent back = new Intent( UserActivity.this, MainActivity.class );
                        startActivity( back );

                    }else {
                        Toast.makeText( getBaseContext(),"User: " + name + " in der Abteilung " + userSection + " existiert bereits!", Toast.LENGTH_SHORT ).show();
                    }

                }else {

                    if (name.length() == 0){
                        Toast.makeText( getBaseContext(),"Der Name fehlt!",Toast.LENGTH_SHORT ).show();
                    }

                    if (userSection.length() == 0){
                        Toast.makeText( getBaseContext(),"Der Name fehlt!",Toast.LENGTH_SHORT ).show();
                    }

                }
            }
        } );

        //Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
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
        Intent goBack = new Intent( UserActivity.this,MainActivity.class );
        startActivity( goBack );
    }
}
