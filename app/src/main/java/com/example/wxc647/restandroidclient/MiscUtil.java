package com.example.wxc647.restandroidclient;

import java.util.List;

/**
 * Created by wxc647 on 8/4/2015.
 */
public class MiscUtil
{
    public String getUserUri(String userId, List<User> usersCollection)
    {
        for (User cUser : usersCollection)
        {
            String userUri = cUser.get_Links().getSelf().getHref();
            String checkedUserId = userUri.substring(userUri.lastIndexOf('/') + 1);
            if(Integer.parseInt(userId.trim()) == Integer.parseInt(checkedUserId.trim()))
            {
                return userUri;
            }
        }

        return null;
    }
}
