import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Find{

    private static TreeMap<Integer, ArrayList<Integer>> inputEdges = new TreeMap<>();
	private static int[][] edges;
	private static int endNode;
	private static long counter = 0;
    private static int noOfNodes;
    private static boolean[] visited;

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
        endNode = new Integer(lines.get(0).split(" ")[1]);

        noOfNodes = lines.size() - 1;

		edges = new int[noOfNodes][noOfNodes];

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

		// clear array


		for(int i = 0; i < noOfNodes; i++){
			int k = 0;
			for(int j = 0; j < noOfNodes; j++){
				edges[i][j] = -1;
				if(hasPath(i,j)){
					edges[i][k] = j;
					k++;
				}
			}
		}

        visited = new boolean[noOfNodes];
        // count all cases

        visited[startNode] = true;
        bruteForce(startNode, 1);
        // generate output

        System.out.println(counter);
    }

    private static void bruteForce(int currentNode, int i) {
		for (int node : edges[currentNode]) {
			if(node == -1) return;
			if (node == endNode) {
				if(i == noOfNodes - 1){
					counter++;
				}
				continue;
			}
			if (visited[node]) {
				continue;
			}

			visited[node] = true;
			bruteForce(node, i + 1);
			visited[node] = false;
		}
	}

    private static boolean hasPath(Integer i, Integer j){
        if(inputEdges.get(i).contains(j)){
			return true;
		}
        else if(inputEdges.get(j).contains(i)){
			return true;
		}
		return false;
    }

    public static long tester(String[] args){
        main(args);
        long r = counter;
        counter = 0;
        return r;
    }
}
