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
		switch(this){
			case BALLISTIC: sb.append("Ballistic");
			break;
			case BEAM: sb.append("Beam");
			break;
			case FIRE: sb.append("Fire");
			break;
			case ELECTRICITY: sb.append("Electricity");
			break;
			case EXPLOSIVE: sb.append("Explosive");
			break;
			default: sb.append("null");
		}
		return sb.toString();
	}
}
