package pt.mrdb.model;

import java.time.LocalDate;

public class Client {
	
	private Integer id;
	private String nif;
    private String password;
    private String name;
    private LocalDate dateOfBirth; 
    private String phone; 
    private String mobile;
    private String email;
    private String occupation;
    
    
    public Client() {
    	
    }
    
    
	public Client(String nif, String password, String name, LocalDate dateOfBirth, String phone, String mobile,
			String email, String occupation) {
		this.nif = nif;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.occupation = occupation;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	@Override
	public String toString() {
		return "Client id=" + id + ", nif=" + nif + ", name=" + name + ", dateOfBirth="
				+ dateOfBirth+"\n";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
    
    
}
