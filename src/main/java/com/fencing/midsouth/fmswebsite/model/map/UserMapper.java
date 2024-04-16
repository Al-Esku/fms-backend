package com.fencing.midsouth.fmswebsite.model.map;

import com.fencing.midsouth.fmswebsite.model.dto.ClubResponse;
import com.fencing.midsouth.fmswebsite.model.dto.UserResponse;
import com.fencing.midsouth.fmswebsite.model.entity.Club;
import com.fencing.midsouth.fmswebsite.model.entity.User;

public class UserMapper {
    public static UserResponse responseMap(User user, ClubResponse club) {
        return new UserResponse(user.getUsername(), user.getRoles(), club);
    }
}
