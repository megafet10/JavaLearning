//More Example at https://www.programcreek.com/java-api-examples/index.php?source_dir=Curecoin-master/Blockchain.java

import java.util.ArrayList;

public class BlockchainExam {

	ArrayList<Block> blockChain = new ArrayList<>();
	
	
	
	public static void main (String args[]) {
		
		String transactionNo1 = "Minh send to Huong 100 Bitcoin";
		Block blockNo1 = new Block (0 ,transactionNo1.getBytes());
		
		String transactionNo2 = "Huong send to Ms.Ngan 5 Bitcoin";
		Block blockNo2 = new Block(blockNo1.getBlockHash(), transactionNo2.getBytes());
		
		String transactionNo3 = "Minh send to Ms.Thuy 5 Bitcoin";
		Block blockNo3 = new Block(blockNo2.getBlockHash(), transactionNo3.getBytes());
		
		System.out.println("Block01 Hash: " + blockNo1.getBlockHash());
		System.out.println("Block02 Hash: " + blockNo2.getBlockHash());
		System.out.println("Block03 Hash: " + blockNo3.getBlockHash());
	}
}
