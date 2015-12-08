package sql2bean.fx;


public class DummyBean {
	private Integer id;
	private String fullName;
	private Integer money;

	public Integer getId(){return id;}
	public void setId(Integer id){ this.id = id;}

	public String getFullName(){ return fullName;}
	public void setFullName(String fullName){ this.fullName = fullName;}

	public Integer getMoney(){return money;}
	public void setMoney(Integer money){ this.money = money; }

	public DummyBean(Integer id, String name, Integer moeny){
		setId(id);
		setFullName(name);
		setMoney(moeny);
	}
}
