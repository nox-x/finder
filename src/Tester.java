import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class Tester {
	

	// private long[] times = new long[22];
	HashMap<String, Long> times = new HashMap<>();
	int state;
	int counter = 0;
	
	@Test
	public void testing (){
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() - time < 1000);
		for(state = 0; state < 100; state++) {
			time = System.currentTimeMillis();
			proof("graph04a.txt", 1);
			proof("graph04b.txt", 2);
			proof("graph05a.txt", 2);
			proof("graph06a.txt", 1);
			proof("graph06b.txt", 1);
			proof("graph06c.txt", 0);
			proof("graph08a.txt", 1);
			proof("graph09a.txt", 2);
			proof("graph09b.txt", 7);
			proof("graph09c.txt", 13);
			proof("graph10a.txt", 0);
			proof("graph11a.txt", 2);
			proof("graph44a.txt", 110);
			proof("graph44b.txt", 300);
			proof("graph44c.txt", 794);
			proof("graph52a.txt", 909);
			proof("graph52b.txt", 788);
			proof("graph52c.txt", 1819);
			proof("graph52d.txt", 2570);
			proof("graph52e.txt", 3047);
			proof("graph52f.txt", 2729);
			proof("graph59a.txt", 115);
			System.out.println(state + " needed over all: " + (System.currentTimeMillis() - time));
		}
		generateOutput();
	}

	private void generateOutput() {
		System.out.println(times);
	}

	private void proof(String s, int result) {
		String[] a = new String[]{s};
		long t = System.currentTimeMillis();
		assertEquals(result, Find.tester(a));
		t = System.currentTimeMillis() - t;
		//System.out.println(s + " needed " + t + " ms");
		if(times.containsKey(s)) times.put(s, ((state * times.get(s)) + t) / (state + 1));
		else times.put(s, t);

		if(counter < 22) counter++;
		else counter = 0;
	}


}
