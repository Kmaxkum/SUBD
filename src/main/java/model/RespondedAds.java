package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "responded_ads")
public class RespondedAds {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "responded_id_seq")
	private int id;
	
	@ManyToOne()
    @JoinColumn(name="responded_id", nullable=false)
    private Responded responded;
	
	@ManyToOne()
    @JoinColumn(name="ads_id", nullable=false)
    private Ads ads;
	
	public RespondedAds() {};
	
	public RespondedAds(Responded responded, Ads ads) {
		this.responded = responded;
		this.ads = ads;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Responded getResponded() {return responded;}
	
	public void setResponded(Responded responded) {
		this.responded = responded;
	}
	
	public Ads getAds() {return ads;}
	
	public void setAds(Ads ads) {
		this.ads = ads;
	}
	
	@Override
    public String toString() {
        return responded.toString() + ads.toString();
    }
}
