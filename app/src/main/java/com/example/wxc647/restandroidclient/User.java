package com.example.wxc647.restandroidclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by wxc647 on 8/2/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "user")
public class User
{

     private String firstName;


    private String lastName;


    private boolean isActive;

    public User()
    {
    }

    public User(String firstNameParam, String lastNameParam)
    {
        this.firstName = firstNameParam;
        this.lastName = lastNameParam;
        this.isActive = true;
    }

    public User(String firstNameParam, String lastNameParam, boolean isActiveParam)
    {
        this(firstNameParam,lastNameParam);
        this.isActive = isActiveParam;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return String.format("FirstName: %s, LastName: %s, IsActive: %s",
                this.firstName, this.lastName, this.isActive);
    }
}
