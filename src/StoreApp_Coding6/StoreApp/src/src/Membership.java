import java.util.Date;

public class Membership {
	private double membershipId;
	private User user;
	private MembershipType membershipType;
	private Date startDate; 
	private Date endDate;
	public double getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(double membershipId) {
		this.membershipId = membershipId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public MembershipType getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
