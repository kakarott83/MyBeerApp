package e.lm280.myapplication.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.BoringLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class AdminActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "lm28081983@gmail.com:abc123"
    };

    // UI references.
    private EditText mPasswordView;
    private EditText mEmailView;
    private View mLoginFormView;
    private String mPassword;
    private String mEmail;
    private Boolean LogIn = false;
    private Boolean emailIsValid;
    private Boolean pwIsValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );
        // Set up the login form.
        mPasswordView = (EditText) findViewById( R.id.password );
        mEmailView = (EditText) findViewById( R.id.email );




        Button mEmailSignInButton = (Button) findViewById( R.id.email_sign_in_button );
        mEmailSignInButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {

                mPassword = mPasswordView.getText().toString();
                mEmail = mEmailView.getText().toString();

                emailIsValid = isEmailValid( mEmail );
                pwIsValid = isPasswordValid( mPassword );

                if (emailIsValid && pwIsValid){

                    for (String credentials : DUMMY_CREDENTIALS){
                        String[] values = credentials.split( ":" );
                        String email = values[0];
                        String pw = values[1];

                        if (email.equals( mEmail ) && pw.equals( mPassword )){
                            LogIn = true;
                        }
                    }

                    if (LogIn){
                        Intent setting = new Intent( AdminActivity.this,SettingActivity.class );
                        startActivity( setting );
                    } else {
                        Toast.makeText( getBaseContext(),"Email/Passwort ungültig",Toast.LENGTH_SHORT ).show();
                    }
                }else{

                    if (!emailIsValid){
                        Toast.makeText( getBaseContext(),"E-Mail ist nicht korrekt",Toast.LENGTH_SHORT ).show();
                    }

                    if (!pwIsValid){
                        Toast.makeText( getBaseContext(),"Passwort ist nicht korrrekt",Toast.LENGTH_SHORT ).show();
                    }
                }

            }
        } );
    }

    private boolean isEmailValid(String email) {

        return email.contains( "@" );
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }
}

