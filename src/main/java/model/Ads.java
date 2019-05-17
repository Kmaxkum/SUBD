package model;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table (name = "ads")
public class Ads {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ads_id_seq")
	private int id;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne()
    @JoinColumn(name="applicant_id", nullable=false)
    private Applicant applicant;
	
	@ManyToOne()
    @JoinColumn(name="status_id", nullable=false)
    private Status status;
	
	@OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListOfAdsAds> listOfAdsAds;
	
	@OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespondedAds> respondedAds;
	
	public Ads() {}
	
	public Ads(Date createDate, String content, Applicant applicant, Status status) {
		this.createDate = createDate;
		this.content = content;
		this.applicant = applicant;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Applicant getApplicant() {
		return applicant;
	}
	
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<ListOfAdsAds> getListOfAdsAds() {
		return listOfAdsAds;
	}
	
	public void setListOfAdsAds(List<ListOfAdsAds> listOfAdsAds) {
		this.listOfAdsAds = listOfAdsAds;
	}
	
	public List<RespondedAds> getRespondedAds() {
		return respondedAds;
	}
	
	public void setRespondedAds(List<RespondedAds> respondedAds) {
		this.respondedAds = respondedAds;
	}
	
	@Override
    public String toString() {
        return applicant.toString() + Integer.toString(id);
    }
}
