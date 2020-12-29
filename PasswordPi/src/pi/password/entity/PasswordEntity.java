package pi.password.entity;

public class PasswordEntity {
	
	private String NAME;
	private String PASSWORD;

	public PasswordEntity(String name, String password) {
		this.NAME = name;
		this.PASSWORD = password;
	}
	
	public String getName() {
		return NAME;
	}
	
	public String getPassword() {
		return PASSWORD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
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
		PasswordEntity other = (PasswordEntity) obj;
		if (NAME == null) {
			if (other.NAME != null)
				return false;
		} else if (!NAME.equals(other.NAME))
			return false;
		return true;
	}
	
	

}
