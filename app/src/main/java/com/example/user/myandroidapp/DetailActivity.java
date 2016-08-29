package com.example.user.myandroidapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by user on 2016/8/26.
 */
//public class DetailActivity extends AppCompatActivity {
public class DetailActivity extends CustomizedActivity {


    public final static int savePokemonIntoComputer =1; //result code
    public final static int addPokemonLevel =2;
    OwnedPokemonInfo ownedPokemonInfo ;

    public Intent srcIntent ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        srcIntent = getIntent(); // what if getIntent = null
        ownedPokemonInfo = srcIntent.getParcelableExtra(PokemonListActivity.ownedPokemonInfoKey); // extra intent name

        setUI(ownedPokemonInfo);
    }

   void setUI( OwnedPokemonInfo ownedPokemonInfo){

       setContentView(R.layout.activity_detail);
       ((TextView)findViewById(R.id.nameText)).setText(ownedPokemonInfo.name);
       ((TextView)findViewById(R.id.levelText)).setText(String.valueOf(ownedPokemonInfo.level));
      // ((TextView)findViewById(R.id.maxHP)).setText(ownedPokemonInfo.maxHP);
       ((TextView)findViewById(R.id.currentHP)).setText(String.valueOf(ownedPokemonInfo.currentHP));
       ((TextView)findViewById(R.id.maxHP)).setText(String.valueOf(ownedPokemonInfo.maxHP));
       int progress = (int)(((float)ownedPokemonInfo.currentHP/ownedPokemonInfo.maxHP)*100);
       ((ProgressBar)findViewById(R.id.hpBar)).setProgress(progress);

       //TextView typeInfo= ((TextView)findViewById(R.id.type1Text));//.setText(String.valueOf(ownedPokemonInfo.currentHP));
       TextView type1Text = (TextView)findViewById(R.id.type1Text);
       if(ownedPokemonInfo.type_1 != -1) {
           type1Text.setText(OwnedPokemonInfo.typeNames[ownedPokemonInfo.type_1]);
       }
       else {
           type1Text.setText("");
       }

       TextView type2Text = (TextView)findViewById(R.id.type2Text);
       if(ownedPokemonInfo.type_2 != -1) {
           type2Text.setText(OwnedPokemonInfo.typeNames[ownedPokemonInfo.type_2]);
       }
       else {
           type2Text.setText("");
       }

       Resources resources = this.getResources();

       for(int i =1; i< OwnedPokemonInfo.maxNumSkills; i++)
       {
           int skillResId = resources.getIdentifier(String.format("skill%dText",i),"id",getPackageName()); // R.id.____
           // R.id.skill1Text;
          TextView skillTextView = (TextView)findViewById(skillResId);
           if(ownedPokemonInfo.skills[i-1] != null)
           {
               skillTextView.setText(ownedPokemonInfo.skills[i-1]);
           }else
           {
               skillTextView.setText("");
           }

       }
       String url = String.format("http://www.csie.ntu.edu.tw/~r03944003/detailImg/%d.png", ownedPokemonInfo.pokemonId);
       ImageLoader.getInstance().displayImage(url, (ImageView) findViewById(R.id.appearanceImg));

   }

   // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_action_bar,menu); // ?? Menu 誰給他？？ 誰call
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_save)
        {
            Intent intent = new Intent();
            intent.putExtra(OwnedPokemonInfo.nameKey, ownedPokemonInfo.name);
            //Log.d("test_name",ownedPokemonInfo.name+":"+OwnedPokemonInfo.nameKey);

            setResult(savePokemonIntoComputer,intent);
            finish();
            return true;

        }else if(itemId == R.id.action_level_up)
        {

            ownedPokemonInfo.level++;
            ((TextView)findViewById(R.id.levelText)).setText(String.valueOf(ownedPokemonInfo.level)); //activity_detail

           Intent intent = new Intent();

            intent.putExtra(OwnedPokemonInfo.pokemonInfoObjectKey, ownedPokemonInfo);

            setResult(addPokemonLevel,intent);

            finish();
                return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
