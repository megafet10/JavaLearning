import java.util.Arrays;

//Declare for block class
public class Block {
	private byte[] transaction;
	private int previousHash;
	
	private int blockHash;
	
	public Block (int previousHash, byte[] transaction) {
		this.previousHash = previousHash;
		this.transaction = transaction;
		
		Object[] contents = {Arrays.hashCode(transaction), previousHash};
		
		this.blockHash = Arrays.hashCode(contents);
		
	}
	
	public int getPreviousHash () {
		return previousHash;
	}
	
	public byte[] getTransaction () {
		return transaction;
	}

	public int getBlockHash() {
		return blockHash;
	}
	
}
