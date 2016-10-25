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
	private static int[] visited;

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

		edges = new int[noOfNodes][];

		// get unidirectional paths
		int j;

		for (j = 0; j < noOfNodes ; j++) {
			String line = lines.get(j+1);
			ArrayList<Integer> nodeEdges = new ArrayList<>(line.split(" ").length - 1);
			for (String s : line.split(" ")) {
				nodeEdges.add(new Integer(s));
			}
			inputEdges.put(j, nodeEdges);
		}

		// getting bidirectional and setting edgeCounter
		int i;

		for(i = 0; i < noOfNodes; i++){
			int k = 0;
			for(j = 0; j < noOfNodes ; j++){
				if(i == j) continue;
				if(hasPath(i,j)){
					k++;
				}
			}
			int[] refs = new int[k];
			k = 0;
			for(j = 0; j < noOfNodes ; j++){
				if(i == j) continue;
				if(hasPath(i,j)){
					refs[k++] = j;
				}
			}
			edges[i] = refs;
		}

		visited = new int[noOfNodes];

		// count all cases

		visited[startNode] = 1;
		bruteForce(startNode, 1);

		// generate output

//		System.out.println(counter);
	}


	private static void bruteForce(int currentNode, int i) {

		outer:
		for (int node : edges[currentNode]) {
			if (visited[node] == 1) {
				continue;
			}
			if (node == endNode) {
				continue;
			}
			if(i == noOfNodes - 2){
				if(hasPath(node, endNode)){
					counter++;
					continue;
				}
				continue;
			}

			visited[node] = 1;

			inner:
			for (int n : edges[currentNode]) {
				if (visited[n] == 1 || n == node) continue;
				int openConnections = 0;
				for (int neighbor : edges[n]) {
					if (neighbor == node)
						continue inner;
					openConnections += 1-visited[neighbor];
				}
				if (openConnections <= (n == endNode ? 0 : 1)) {
					visited[node] = 0;
					continue outer;
				}
			}

			bruteForce(node, i + 1);
			visited[node] = 0;
		}
	}

	private static final boolean hasPath(int i, int j) {
		return inputEdges.get(i).contains(j) || inputEdges.get(j).contains(i);
	}

	static long tester(String[] args){
		main(args);
		long r = counter;
		counter = 0;
		return r;
	}
}
