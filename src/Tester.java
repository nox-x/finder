import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nox_x on 6/12/16.
 */
public class Tester {

    @Test
    public void testing (){
        long time = System.currentTimeMillis();

        proof("graph04a.txt", 1, time);
        proof("graph04b.txt", 2, time);
        proof("graph05a.txt", 2, time);
        proof("graph06a.txt", 1, time);
        proof("graph06b.txt", 1, time);
        proof("graph06c.txt", 0, time);
        proof("graph08a.txt", 1, time);
        proof("graph09a.txt", 2, time);
        proof("graph09b.txt", 7, time);
        proof("graph09c.txt", 13, time);
        proof("graph10a.txt", 0, time);
        proof("graph11a.txt", 2, time);
        proof("graph44a.txt", 110, time);
        proof("graph44b.txt", 300, time);
//        for(int i = 5; i > 0; i--)
        proof("graph44c.txt", 794, time);


        proof("graph52a.txt", 909, time);
//        proof("graph52b.txt", 788, time);
//        proof("graph52c.txt", 1819, time);
//        proof("graph52d.txt", 2570, time);
//        proof("graph52e.txt", 3047, time);
//        proof("graph52f.txt", 2729, time);
//        proof("graph59a.txt", 115, time);
    }

    private void proof(String s, int result, long t) {
        String[] a = new String[]{s};
        t = System.currentTimeMillis();
        assertEquals(result, Find.tester(a));
        System.out.println(s + " needed " + (System.currentTimeMillis()-t) + " ms");
    }


}
