package org.codeAcademy.model;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "Tournament")
public class Tournament {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "prize_fund")
    private BigDecimal prizeFund;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_matches")
    private List<Match> matches = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "tournament_teams",
        joinColumns = {
            @JoinColumn(name = "tournament_id")},
            inverseJoinColumns = {
            @JoinColumn(name = "team_id")}
    )
    @Embedded
    private List<Team> teams = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "team_points_mapping",
    joinColumns = {@JoinColumn(name ="team_id", referencedColumnName = "points")})
    @MapKeyColumn(name = "team")
    @Column(name = "teams_points")
    private HashMap<Team,Integer> teams_points = new HashMap<>();

    public Tournament() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrizeFund() {
        return prizeFund;
    }

    public void setPrizeFund(BigDecimal prizeFund) {
        this.prizeFund = prizeFund;
    }

    public List<Match> getMatches() {
        return matches;
    }
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public HashMap<Team, Integer> getTeams_points() {
        return teams_points;
    }

    public void setTeams_points(HashMap<Team, Integer> teams_points) {
        this.teams_points = teams_points;
    }
}
