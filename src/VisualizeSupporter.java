import de.wegenerd.hamilton.visualizer.Node;

import java.math.BigInteger;
import java.util.ArrayList;

public class VisualizeSupporter extends Algorithm{

	private ArrayList<Node> visitedNodes;

	public void run() {
		visitedNodes = new ArrayList<>();
		startNode.setHighlight(true);
		visitedNodes.add(startNode);
		BigInteger result = solve(startNode.getNeighbours());
		publishResult(result);
	}

	private BigInteger solve(ArrayList<Node> neighbours) {
		BigInteger result = BigInteger.ZERO;

		if(cancel){
			return result;
		}
		for (Node node : neighbours) {

			if (visitedNodes.contains(node)) {
				continue;
			}
			if (node.equals(endNode)) {
				continue;
			}
			if(visitedNodes.size() == Node.getAll().size() - 2){
				if(hasPath(node, endNode)){
					return BigInteger.ONE;
				}
				continue;
			}
			visitedNodes.add(node);
			node.setHighlight(true);
			result = result.add(solve(node.getNeighbours()));
			node.setHighlight(false);
			visitedNodes.remove(node);
		}
		return result;
	}


	private boolean hasPath(Node first, Node second) {
		return first.getNeighbours().contains(second);
	}


	@Override
	public String getAlgorithmName() {
		return "complex brute force";
	}
}
