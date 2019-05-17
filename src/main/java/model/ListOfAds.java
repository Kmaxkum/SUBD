package model;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table (name = "list_of_ads")
public class ListOfAds {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "list_of_ads_id_seq")
	private int id;
	
	@Column(name = "date_from")
	private Date dateFrom;
	
	@Column(name = "date_to")
	private Date dateTo;
	
	@Column(name = "relevance")
	private Boolean relevance;
	
	@OneToMany(mappedBy = "listOfAds",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListOfAdsAds> listOfAdsAds;
	
	@ManyToOne()
    @JoinColumn(name="ads_board_id", nullable=false)
    private AdsBoard adsBoard;
	
	public ListOfAds() {}
	
	public ListOfAds(Date dateFrom, Date dateTo, Boolean relevance, AdsBoard adsBoard) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.relevance = relevance;
		this.adsBoard = adsBoard;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}
	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public Date getDateTo() {
		return dateTo;
	}
	
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public Boolean getRelevance() {
		return relevance;
	}
	
	public void setRelevance(Boolean relevance) {
		this.relevance = relevance;
	}
	
	public List<ListOfAdsAds> getListOfAdsAds() {
		return listOfAdsAds;
	}
	
	public void setListOfAdsAds(List<ListOfAdsAds> listOfAdsAds) {
		this.listOfAdsAds = listOfAdsAds;
	}
	
	public AdsBoard getAdsBoard() {
		return adsBoard;
	}
	
	public void setAdsBoard(AdsBoard adsBoard) {
		this.adsBoard = adsBoard;
	}
	
	@Override
    public String toString() {
        return adsBoard.toString() + Integer.toString(id);
    }
}
