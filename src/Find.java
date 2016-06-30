import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Find{

	private static final int MAX_REFS = 7;

	private static TreeMap<Integer, ArrayList<Integer>> inputEdges = new TreeMap<>();
	private static int[][] edges;
	private static int endNode;
	private static long counter = 0;
	private static int noOfNodes;
	private static boolean[] visited;
	private static int[] references;

	public static void main(String[] args) {
		// it's just brute-forcing a bit

		// read file

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert lines != null;
		int startNode = new Integer(lines.get(0).split(" ")[0]);
		endNode = new Integer(lines.get(0).split(" ")[1]);

		noOfNodes = lines.size() - 1;

		edges = new int[noOfNodes][MAX_REFS];
		references = new int[noOfNodes];

		// get unidirectional paths
		for (int i = 0; i < noOfNodes; i++) {
			String line = lines.get(i+1);
			ArrayList<Integer> nodeEdges = new ArrayList<>(line.split(" ").length - 1);
			for (String s : line.split(" ")) {
				nodeEdges.add(new Integer(s));
			}
			inputEdges.put(i, nodeEdges);
		}

		// getting bidirectional and setting edgeCounter

		for(int i = 0; i < noOfNodes; i++){
			int k = 0;
			for(int j = 0; j < noOfNodes; j++){
				if(j < MAX_REFS) edges[i][j] = -1;
				if(i == j) continue;
				if(hasPath(i,j)){
					references[i]++;
					edges[i][k] = j;
					k++;
				}
			}
		}

		visited = new boolean[noOfNodes];

		// count all cases

		visited[startNode] = true;
		references[startNode] = 0;
		for(int n : edges[startNode]){
			if(n == -1) break;
			if(n != startNode) references[n]--;
		}
		bruteForce(startNode, 1);

		// generate output

		System.out.println(counter);
	}

	private static void bruteForce(int currentNode, int i) {

		for (int node : edges[currentNode]) {
			if (node == -1) return;
			if (visited[node]) {
				continue;
			}
			if (node == endNode) {
				continue;
			}
			if(i == noOfNodes - 2){
				if(hasPath(node, endNode)){
					counter++;
				}
				continue;
			}

			visited[node] = true;
			for(int neighbour : edges[node]){
				if(neighbour == -1 ){
					visited[node] = false;
					return;
				}
				if(!visited[neighbour] || references[neighbour] >= 1){
					break;
				}
			}
//			int cache = references[node];
//			references[node] = 0;
//			for (int l : edges[node]) {
//				if(l == -1) break;
//				if(l == node) continue;
// 				if(!visited[l]){
//					references[l]--;
//					if(references[l] == 0){
//						return;
//					}
//				}
//			}
			bruteForce(node, i + 1);
//			for (int l : edges[node]){
//				if(l == -1) break;
//				references[l]++;
//			}
//			references[node] = cache;
			visited[node] = false;
		}
	}

	private static boolean hasPath(int i, int j) {
		return inputEdges.get(i).contains(j) || inputEdges.get(j).contains(i);
	}

	static long tester(String[] args){
		main(args);
		long r = counter;
		counter = 0;
		return r;
	}
}
