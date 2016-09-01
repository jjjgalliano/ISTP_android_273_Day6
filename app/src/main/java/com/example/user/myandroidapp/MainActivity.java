package com.example.user.myandroidapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

//import com.nostra13.universalimageloader.cache.disc.impl.ext.DiskLruCache;
//import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

public class MainActivity extends CustomizedActivity implements View.OnClickListener, EditText.OnEditorActionListener {

    public static final String selectedPokemonIndexKey = "selectedPokemonIndexKey";
    public static final String trainerNameKey = "trainerName";
    public static final String selectedOptionsIdexKey = "optionIndex";
    public static final String nameTextKey ="nameText";
    public static final String emailKey ="email";
    public static final String profileImgUrlKey = "profileImgUrl";

    static final String[] pokemonNames = {"小火龍", "傑尼龜", "妙蛙種子"};
    TextView infoText;
    EditText nameEditText;
    RadioGroup optionsGroup;
    Button confirmBtn;
    Handler handler;
    ProgressBar progressBar;
    SharedPreferences preferences;
    LoginButton loginBtn;

    CallbackManager callbackManager;
    AccessToken currentToken;
    AccessToken accessToken;

    int selectedOptionIndex = 0;
    String nameOftheTrainer = null;
    boolean isFirstTimeUsingThisPage = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        handler = new Handler(this.getMainLooper());

        activityName = this.getClass().getSimpleName();


        //added for fb
        accessToken = null; currentToken=null;;

        //find UIs by their ids
        infoText = (TextView) findViewById(R.id.infoText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText.setOnEditorActionListener(this);
        nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        optionsGroup = (RadioGroup) findViewById(R.id.optionsGroup);
        confirmBtn = (Button) findViewById(R.id.confirmButton);
        confirmBtn.setOnClickListener(MainActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.loadingBar);
        progressBar.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                .sweepSpeed(1f)
                .strokeWidth(8f)
                .build()
        );
        loginBtn =(LoginButton)findViewById(R.id.login_button);

        preferences = getSharedPreferences(Application.class.getSimpleName(), MODE_PRIVATE); //this app can access this setting
        nameOftheTrainer = preferences.getString(trainerNameKey, null); // if no , null
        selectedOptionIndex = preferences.getInt(selectedOptionsIdexKey, 0);

        if (nameOftheTrainer == null) {
            nameEditText.setVisibility(View.VISIBLE);
            optionsGroup.setVisibility(View.VISIBLE);
            confirmBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
            setupFBLogin();

            isFirstTimeUsingThisPage = true;

        } else {
            nameEditText.setVisibility(View.INVISIBLE);
            optionsGroup.setVisibility(View.INVISIBLE);
            confirmBtn.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);

            isFirstTimeUsingThisPage = false;

            confirmBtn.performClick();
            // can get onclick function
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    Runnable jumpToNewActivityTask = new Runnable() {
        @Override
        public void run() {
            jumpToNewActivity();
        }
    };

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.confirmButton) {
            //confirmButton was clicked
            Log.d("buttonTest", "confirm-button was clicked");

            if (isFirstTimeUsingThisPage) {
                nameOftheTrainer = nameEditText.getText().toString();

                int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
                View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
//            int selectedIndex = optionsGroup.indexOfChild(selectedRadioButtonView);

//                RadioButton selectedRadioButton = (RadioButton) selectedRadioButtonView;
//                String radioBtnText = selectedRadioButton.getText().toString();
                selectedOptionIndex = optionsGroup.indexOfChild(selectedRadioButtonView);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(trainerNameKey, nameOftheTrainer);
                editor.putInt(selectedOptionsIdexKey, selectedOptionIndex);
                editor.commit();

            }

            String welcomeMessage = String.format(
                    "你好, 訓練家%s 歡迎來到神奇寶貝的世界 你的第一個夥伴是%s",
                    nameOftheTrainer,
                    pokemonNames[selectedOptionIndex]
            );
            infoText.setText(welcomeMessage);

            handler.postDelayed(jumpToNewActivityTask, 5 * 1000); //delay 3 seconds
        }


    }

    private int getSelectedPokemonIndex() {
        int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();
        View selectedRadioButtonView = optionsGroup.findViewById(selectedRadioButtonId);
        return optionsGroup.indexOfChild(selectedRadioButtonView);
    }

    private void jumpToNewActivity() {

        Intent intent = new Intent();

        intent.setClass(MainActivity.this, PokemonListActivity.class);
        intent.putExtra(selectedPokemonIndexKey, selectedOptionIndex);//getSelectedPokemonIndex());

        startActivity(intent); //
        finish(); //main activity not root ; root activity is the new activity


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //dismiss virtual keyboard
            InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            //simulate button clicked
            confirmBtn.performClick();
            return true;
        }
        return false;
    }


    public void setupFBLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginBtn.setReadPermissions("public_profile", "email");
        loginBtn.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessToken = loginResult.getAccessToken();
                        sendGraphReq();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.

                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.myandroidapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.myandroidapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void sendGraphReq() {
        if(accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() { // RESPONSE     
                        @Override

                        public void onCompleted(JSONObject object, GraphResponse response) {
//     ID FB       if (response != null) {
                            SharedPreferences preferences = getSharedPreferences(Application.class.getName(), MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            nameOftheTrainer = object.optString("name");
                            editor.putString(nameTextKey, nameOftheTrainer);
                            editor.putString(emailKey, object.optString("email"));

                            if (object.has("picture")) {
                                try {
                                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    editor.putString(profileImgUrlKey, profilePicUrl);
                                } catch (Exception e) {
                                    Log.d("FB", e.getLocalizedMessage());
                                }
                            }
                            editor.commit();
                        }
                     });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
        }


    }

}
