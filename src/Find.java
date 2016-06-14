import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Find{

    private static TreeMap<Integer, ArrayList<Integer>> inputEdges = new TreeMap<>();
    private static TreeMap<Integer, LinkedList<Node>> edges = new TreeMap<>();
    private static long counter = 0;
    private static int noOfNodes;
    private static ArrayList<Node> visited;

    public static void main(String[] args) {
        // it's just brute-forcing a bit

        // read file

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int startNode = new Integer(lines.get(0).split(" ")[0]);
        int endNode = new Integer(lines.get(0).split(" ")[1]);

        noOfNodes = lines.size() - 1;

        ArrayList<Node> nodes = new ArrayList<>(noOfNodes);
        for(int i = 0; i< noOfNodes; i++) nodes.add(new Node(i));

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

        LinkedList<Node> es;
        for(int i = 0; i < noOfNodes ; i++){
            es = new LinkedList<>();
            for(int j = 0; j < noOfNodes ; j++){
                if(hasPath(i, j) && i!=j){
                    es.add(nodes.get(j));
                }
            }
            edges.put(i, es);
        }

        visited = new ArrayList<>();
        // count all cases

        Node start = nodes.get(startNode);
        Node finish = nodes.get(endNode);

        visited.add(start);
        bruteForce(edges.get(startNode), finish, 1);
        // generate output

        System.out.println(counter);
    }

    private static void bruteForce(LinkedList<Node> list, Node end, int i) {
        for (Node node : list) {
            if(visited.contains(node)){
                continue;
            }
            if(node.hashCode() == end.hashCode()) {
                if (i == noOfNodes - 1){
                    counter++;
                    return;
                }
                continue;
            }
            visited.add(node);
            bruteForce(edges.get(node.name), end, i+1);
            visited.remove(node);
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

    private static class Node{
        public int name;

        Node(int i){
            name = i;
        }
    }
}
