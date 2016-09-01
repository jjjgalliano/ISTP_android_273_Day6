package com.example.user.myandroidapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by user on 2016/8/24.
 */

@ParseClassName("OwnedPokemonInfo")
public class OwnedPokemonInfo extends ParseObject implements Parcelable {
 //   public class OwnedPokemonInfo implements Parcelable {

    public final static String pokemonIdKey = "pokeId";
    public static final String nameKey="name"; // for intent extra  passs name
    public final static String levelKey ="level";
    public final static String currentHPKey = "currentHP";
    public final static String maxHPKey = "maxHPKey";
    public final static String type1Key = "type1";
    public final static String type2Key = "type2";
    public final static String skillsKey = "skills";
    public final static String isSelectedKey = "isSelected";




    public static final String pokemonInfoObjectKey="Object";  // for intent extrat pass  parcelable (serialized )object
    public static final int maxNumSkills = 4;
    public static String[] typeNames;


    private int pokemonId;
    private String name;
    private int level;
    private int currentHP;
    private int maxHP;

    private int type_1;
    private int type_2;
    private String[] skills = new String[maxNumSkills];

    public boolean isSelected = false;


    public OwnedPokemonInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getPokemonId());
        dest.writeString(this.getName());
        dest.writeInt(this.getLevel());
        dest.writeInt(this.getCurrentHP());
        dest.writeInt(this.getMaxHP());
        dest.writeInt(this.getType_1());
        dest.writeInt(this.getType_2());
        dest.writeStringArray(this.getSkills());
        //dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected OwnedPokemonInfo(Parcel in) {
        this.setPokemonId(in.readInt());
        this.setName(in.readString());
        this.setLevel(in.readInt());
        this.setCurrentHP(in.readInt());
        this.setMaxHP(in.readInt());
        this.setType_1(in.readInt());
        this.setType_2(in.readInt());
        this.setSkills(in.createStringArray());
       // this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<OwnedPokemonInfo> CREATOR = new Parcelable.Creator<OwnedPokemonInfo>() {
        @Override
        public OwnedPokemonInfo createFromParcel(Parcel source) {
            return new OwnedPokemonInfo(source);
        }

        @Override
        public OwnedPokemonInfo[] newArray(int size) {
            return new OwnedPokemonInfo[size];
        }
    };


    //getter and setter
    public int getPokemonId() {
        return getInt(pokemonIdKey);  // from Parse lib  // pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        put(pokemonIdKey,pokemonId);
        //this.pokemonId = pokemonId;
    }

    public String getName() {
        return getString(nameKey); //name;
    }

    public void setName(String name) {
        put(nameKey,name);
       // this.name = name;
    }

    public int getLevel() {
        return getInt(levelKey);
    }

    public void setLevel(int level) {
        put(levelKey,level);
       // this.level = level;
    }

    public int getCurrentHP() {

        return getInt(currentHPKey);
    }

    public void setCurrentHP(int currentHP) {
        put(currentHPKey,currentHP);
        //this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return getInt(maxHPKey);//maxHP;
    }

    public void setMaxHP(int maxHP) {
        put(maxHPKey,maxHP);
       // this.maxHP = maxHP;
    }

    public int getType_1() {
      return  getInt(type1Key);

    }

    public void setType_1(int type_1) {
        put(type1Key,type_1);
       // this.type_1 = type_1;
    }

    public int getType_2() {

        return  getInt(type2Key);//type_2;
    }

    public void setType_2(int type_2) {
        put(type2Key,type_2);//this.type_2 = type_2;
    }

    boolean skillHaveBeenInited = false;
    boolean skillHaveBeenModified = false;

    public String[] getSkills() {
        if(!skillHaveBeenInited) {
            skillHaveBeenInited = true;
            this.skills = readSkillFromParseObjStorage();
        }
        else if(skillHaveBeenModified) {
            skillHaveBeenModified = false;
            this.skills = readSkillFromParseObjStorage();
        }

        return this.skills;
    }

    private String[] readSkillFromParseObjStorage() {
        ArrayList<String> skillList = (ArrayList)get(skillsKey);
        String[] skillArray = new String[maxNumSkills];
        if(skillList != null) {
            for(int i = 0;i < skillList.size();i++) {
                skillArray[i] = skillList.get(i);
            }
        }

        return skillArray;
    }

    public void setSkills(String[] skills) {
        ArrayList<String> skillList = new ArrayList<>(skills.length);
        for(String skillName : skills) {
            if(skillName != null) {
                skillList.add(skillName);
            }
        }
        put(skillsKey, skillList);

        skillHaveBeenModified = true;
    }

    public boolean isSelected() {
        return getBoolean(isSelectedKey);//isSelected;
    }

    public void setSelected(boolean selected) {
      put(isSelectedKey,selected); // isSelected = selected;
    }
}
