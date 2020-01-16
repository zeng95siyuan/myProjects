package com.jr_4.client.Pojos;

public class Friend {

    private String friendname;
    private int friendId;
    private int user_id;
    private boolean banned;

    public Friend() {

    }

    public Friend(String friendname, int friendId, int userId, boolean banned) {
        this.friendname = friendname;
        this.friendId = friendId;
        this.user_id = userId;
        this.banned = banned;

    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setFriendname(String friendname){
        this.friendname=friendname;
    }

    public void setFriendId(int frindId){
        this.friendId=frindId;
    }

    public void setUser_id(int userId){
        this.user_id=userId;
    }

    public String getFriendname(){
        return  friendname;
    }

    public int getFriendId(){
        return  friendId;
    }

    public int getUserId(){
        return user_id;
    }
}
