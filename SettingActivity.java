package e.lm280.myapplication.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button btnResetDb;
    Button btnSaveValues;

    private DatenbankManager mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );

        btnResetDb = (Button) findViewById( R.id.btnRestDb );
        btnSaveValues = (Button) findViewById( R.id.btnSavesValues );

        mHelper = new DatenbankManager( this );

        btnResetDb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.deleteDatabase();
                Toast.makeText( getBaseContext(),"Tabelle User und Items gelöscht!",Toast.LENGTH_SHORT ).show();
            }
        } );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

}
