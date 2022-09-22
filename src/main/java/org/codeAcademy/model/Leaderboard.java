package org.codeAcademy.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "Leaderboard_id")
    private long leaderboardId;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "leaderboard",fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "tournaments")
    private List<Tournament> tournaments = new ArrayList<>();

    public Leaderboard() {
    }

    public long getLeaderboardId() {
        return leaderboardId;
    }

    public void setLeaderboardId(long leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
