import java.util.List;
import java.util.ArrayList;

public class CharacterCounter implements Runnable {
	private List<String> lines;
	private int rp;
	private int start;
	private int end;
	
	public CharacterCounter(List<String> lines, int start, int end) {
		this.rp = 0;
		this.lines = lines;
		this.start = start;
		this.end = end;
	}
	
	public int getRp(){
		return this.rp;
	}

	public void count_characters() throws InterruptedException{
		int count = 0;
		String line = "";

		for (int i = this.start; i < this.end; i++){	// lineas
			line = this.lines.get(i);
			for (int j = 0; j < line.length(); j++){		// cada linea
				count++;
			}
		}
		Thread.sleep(1000);
		this.rp = count;
	}

	@Override
	public void run(){
		count_characters();
		
	}
}
