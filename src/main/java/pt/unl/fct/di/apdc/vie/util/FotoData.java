package pt.unl.fct.di.apdc.vie.util;

public class FotoData {

	private byte[] bytes;
	private String tokenID;
	
	public FotoData() {
	}
	
	public FotoData(byte[] bytes, String tokenID) {
		this.bytes = bytes;
		this.tokenID = tokenID;
	}
	
	public String getTokenID() {
		return tokenID;
	}
	public byte[] getBytes() {
		return bytes;
	}
}
