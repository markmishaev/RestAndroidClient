package com.example.wxc647.restandroidclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by wxc647 on 8/2/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "user")
public class User
{
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName(value = "self")
    public class Self
    {
        private String href;

        public Self()
        {

        }

        public Self(String href)
        {
            this.href = href;
        }

        public String getHref()
        {
            return href;
        }

        public void setHref(String href)
        {
            this.href = href;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName(value = "_links")
    public class Link
    {
        private Self self;

        public Link()
        {

        }

        public Link(Self self)
        {
            this.self = self;
        }

        public Self getSelf()
        {
            return self;
        }

        public void setSelf(Self self)
        {
            this.self = self;
        }
    }

    private Link _links;

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

    public User(String firstNameParam, String lastNameParam, boolean isActiveParam, Link _links)
    {
        this(firstNameParam, lastNameParam, isActiveParam);
        this._links = _links;
    }

    public Link get_Links()
    {
        return _links;
    }

    public void set_Links(Link links)
    {
        this._links = links;
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
