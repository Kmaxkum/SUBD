package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "responded")
public class Responded {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "responded_id_seq")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone_number")
	private int phoneNumber;
	
	@Column(name = "e_mail")
	private String eMail;
	
	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="status_id", nullable=false)
    private Status status;
	
	@OneToMany(mappedBy = "responded", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespondedAds> respondedAds;
	
	public Responded() {};
	
	public Responded(String name, int phoneNumber, String eMail, Status status) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEMail() {
		return eMail;
	}
	
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	public List<RespondedAds> getAds() {
		return respondedAds;
	}
	
	public void setAds(List<RespondedAds> respondedAds) {
		this.respondedAds = respondedAds;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return name;
    }
}
