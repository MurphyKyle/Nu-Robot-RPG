package enums;

public enum Type {

	BALLISTIC, 
	BEAM, 
	FIRE, 
	ELECTRICITY, 
	EXPLOSIVE;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.toString().charAt(0));
		sb.append(this.toString().substring(1, this.toString().length()).toLowerCase());
		return sb.toString();
	}
}
