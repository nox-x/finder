import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Christoph Ruhl on 6/11/16.
 */

public class Find{

    private static TreeMap<Integer, ArrayList<Integer>> inputEdges= new TreeMap<>();
    private static TreeMap<Integer, ArrayList<Integer>> edges= new TreeMap<>();
    private static long counter = 0;
    private static int startNode;
    private static int endNode;
    private static int noOfNodes;

    public static void main(String[] args) {
        // it's just brute-forcing a bit

        // read file

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        startNode = new Integer(lines.get(0).split(" ")[0]);
        endNode = new Integer(lines.get(0).split(" ")[1]);

        noOfNodes = lines.size() - 1;

        // get unidirectional paths
        for (int i = 0; i < noOfNodes; i++) {
            String line = lines.get(i+1);
            ArrayList<Integer> nodeEdges = new ArrayList<>(line.split(" ").length - 1);
            for (String s : line.split(" ")) {
                nodeEdges.add(new Integer(s));
            }
            inputEdges.put(i, nodeEdges);
        }

        // getting bidirectional
        for(int i = 0; i < noOfNodes ; i++){
            ArrayList<Integer> es = new ArrayList<>();
            for(int j = 0; j < noOfNodes ; j++){
                if(hasPath(i, j)){
                    es.add(j);
                }
            }
            edges.put(i, es);
        }

        // count all cases
        ArrayList<Integer> vis = new ArrayList<>(noOfNodes);
        bruteForce(startNode, vis, 1);

        // generate output

        System.out.println(counter);
    }

    private static void bruteForce(int node, ArrayList<Integer> visits, int d) {
        ArrayList<Integer> v = (ArrayList<Integer>) visits.clone();
        v.add(node);
        for (Integer n : edges.get(node)) {
            if(!v.contains(n)){
                if(n == endNode && d == noOfNodes - 1){
                    counter++;
                    return;
                } else if(n != endNode){
                    bruteForce(n, v, d + 1);
                }
            }
        }
    }

    private static boolean hasPath(Integer i, Integer j){
        if(inputEdges.get(i).contains(j)) return true;
        else return inputEdges.get(j).contains(i);
    }

    public static long tester(String[] args){
        main(args);
        long r = counter;
        counter = 0;
        return r;
    }
}
