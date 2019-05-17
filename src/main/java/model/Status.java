package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "status")
public class Status {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "status_id_seq")
	private int id;
	
	@Column(name = "state")
	private String state;
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ads> ads;
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Applicant> applicant;
	
	@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Responded> responded;
	
	public Status() {};
	
	public Status(String state) {
		this.state = state;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public List<Ads> getAds() {
		return ads;
	}
	
	public void setAds(List<Ads> ads) {
		this.ads = ads;
	}
	
	public List<Applicant> getApplicant() {
		return applicant;
	}
	
	public void setApplicant(List<Applicant> applicant) {
		this.applicant = applicant;
	}
	
	public List<Responded> getResponded() {
		return responded;
	}
	
	public void setResponded(List<Responded> responded) {
		this.responded = responded;
	}
	
	@Override
    public String toString() {
        return state;
    }
}
