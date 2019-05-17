package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "list_of_ads_ads")
public class ListOfAdsAds {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "list_of_ads_ads_id_seq")
	private int id;
	
	@ManyToOne()
    @JoinColumn(name="list_of_ads_id", nullable=false)
    private ListOfAds listOfAds;
	
	@ManyToOne()
    @JoinColumn(name="ads_id", nullable=false)
    private Ads ads;
	
	public ListOfAdsAds() {};
	
	public ListOfAdsAds(ListOfAds listOfAds, Ads ads) {
		this.listOfAds = listOfAds;
		this.ads = ads;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public ListOfAds getListOfAds() {return listOfAds;}
	
	public void setListOfAds(ListOfAds listOfAds) {
		this.listOfAds = listOfAds;
	}
	
	public Ads getAds() {return ads;}
	
	public void setAds(Ads ads) {
		this.ads = ads;
	}
	
	@Override
    public String toString() {
        return listOfAds.toString() + ads.toString();
    }	
}
