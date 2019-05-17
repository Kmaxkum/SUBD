package model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table (name = "ads_board")
public class AdsBoard {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ads_board_id_seq")
	private int id;
	
	@Column(name = "location")
	private String location;
	
	@OneToMany(mappedBy = "adsBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListOfAds> listOfAds;
	
	public AdsBoard() {};
	
	public AdsBoard(String location) {
		this.location = location;
	}

	public int getId() {
        return id;
    }
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public List<ListOfAds> getListOfAds() {
		return listOfAds;
	}
	
	public void setListOfAds(List <ListOfAds> listOfAds) {
		this.listOfAds = listOfAds;
	}
	
	@Override
    public String toString() {
        return location;
    }
}
