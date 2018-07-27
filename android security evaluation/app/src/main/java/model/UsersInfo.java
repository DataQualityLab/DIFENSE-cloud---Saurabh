package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wanis on 6/21/2018.
 */

public class UsersInfo {

    @SerializedName("name")
    private String name;

    public String getName(){
        return name;
    }
}
