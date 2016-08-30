package com.example.user.myandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myandroidapp.adapter.PokemonListViewAdapter;
import com.example.user.myandroidapp.model.OwnedPokemonDataManager;
import com.example.user.myandroidapp.model.OwnedPokemonInfo;

import java.util.ArrayList;

public class PokemonListActivity extends CustomizedActivity implements OnPokemonSelectedChangeListener, AdapterView.OnItemClickListener,DialogInterface.OnClickListener{

    public final static int  detailActivityRequestCode =1; // intent  key , can't be the same
    public final static String ownedPokemonInfoKey ="ownedPokemonInfoKey"; //intent key , pass object


    PokemonListViewAdapter arrayAdapter;
    ArrayList<OwnedPokemonInfo> ownedPokemonInfos;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        activityName = this.getClass().getSimpleName();

        OwnedPokemonDataManager dataManager = new OwnedPokemonDataManager(this);
        dataManager.loadListViewData();
        dataManager.loadPokemonTypes();

        ownedPokemonInfos = dataManager.getOwnedPokemonInfos();

        OwnedPokemonInfo[] initPokemonInfos = dataManager.getInitPokemonInfos();
        Intent srcIntent = getIntent();                    // what if intent is empty ???

        int selectedIndex = srcIntent.getIntExtra(MainActivity.selectedPokemonIndexKey, 0);
        ownedPokemonInfos.add(0, initPokemonInfos[selectedIndex]);

        ListView listView = (ListView)findViewById(R.id.listView);
        arrayAdapter = new PokemonListViewAdapter(this,
                R.layout.row_view_of_pokemon_list,
                ownedPokemonInfos);
        arrayAdapter.pokemonSelectedChangeListener = this;

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        alertDialog = new AlertDialog.Builder(this)
                        .setTitle("ALERT !")
                        .setMessage("sure you want to delete it ? ")
                        .setNegativeButton("no",this)
                        .setPositiveButton("sure",this)
        .create()
        ;

        //setNegativeButton can put listener

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(arrayAdapter.selectedPokemons.isEmpty()) {
            return false; //not showing anything on action bar
        }
        else {
            getMenuInflater().inflate(R.menu.selected_pokemon_action_bar, menu);
            return true; //show the menu items on action bar
        }
    }


    void deleteOwnPokemon()
    {
        for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
            ownedPokemonInfos.remove(ownedPokemonInfo);
        }
        arrayAdapter.selectedPokemons.clear();
        arrayAdapter.notifyDataSetChanged();

        // second way to remove from ownedPokemonInfos
//            for(OwnedPokemonInfo ownedPokemonInfo : arrayAdapter.selectedPokemons) {
//                arrayAdapter.remove(ownedPokemonInfo);
//            }
//            arrayAdapter.selectedPokemons.clear();

        invalidateOptionsMenu();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String test = null ;
        int itemId = item.getItemId();
        if(itemId == R.id.action_delete) {
           // deleteOwnPokemon();
            alertDialog.show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSelectedChange(OwnedPokemonInfo ownedPokemonInfo) {
        invalidateOptionsMenu(); //make system call onCreateOptionsMenu
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
      OwnedPokemonInfo ownedPokemonInfo=  arrayAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(PokemonListActivity.this ,  DetailActivity.class);
        intent.putExtra(ownedPokemonInfoKey,ownedPokemonInfo); // simgle object
        startActivityForResult(intent,detailActivityRequestCode);   //!!!

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == detailActivityRequestCode) //result come from detail activity
        {

                if(resultCode == DetailActivity.savePokemonIntoComputer) {
                  String  pokemonName = data.getStringExtra(OwnedPokemonInfo.nameKey);

                    if (arrayAdapter != null) {
                        OwnedPokemonInfo ownedPokemonInfo = arrayAdapter.getItemWithName(pokemonName);
                        arrayAdapter.remove(ownedPokemonInfo);

                        //alternatives
//                    ownedPokemonInfos.remove(ownedPokemonInfo);
//                    arrayAdapter.notifyDataSetChanged();

                        // another
                        Toast.makeText(this, pokemonName + "data saved to computer", Toast.LENGTH_SHORT).show();
                    }
                }
               else  if(resultCode == DetailActivity.addPokemonLevel)
                {
                    OwnedPokemonInfo ownedPokemonInfo = data.getParcelableExtra(OwnedPokemonInfo.pokemonInfoObjectKey);
                    if (arrayAdapter != null) {
                        arrayAdapter.update(ownedPokemonInfo);

                       // Toast.makeText(this, pokemonName + "data saved to computer", Toast.LENGTH_SHORT).show();
                    }
                }


        }else
        {}
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {

        if(which == AlertDialog.BUTTON_NEGATIVE )
        {
            Toast.makeText(this, "cancel delete",Toast.LENGTH_SHORT);
        }
        else if(which == AlertDialog.BUTTON_POSITIVE)
        {

            deleteOwnPokemon();
        }


    }
}
