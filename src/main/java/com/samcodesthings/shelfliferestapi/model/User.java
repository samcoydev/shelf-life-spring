package com.samcodesthings.shelfliferestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "sl_user")
@Data
public class User {

    @Id
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(optional = true)
    @JoinColumn(name = "household_id")
    @JsonProperty("household_id")
    private Household household;

    @Column(name = "has_been_welcomed")
    private boolean hasBeenWelcomed;

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnoreProperties("friendsList")
    private List<User> friendsList;

    public void addFriend(User friend) {
        this.friendsList.add(friend);
    }

    public void removeFriend(User friend) {
        this.friendsList.remove(friend);
    }
}
