package org.codeAcademy.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_teams")
    private List<Team> teams = new ArrayList<>();

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
}
