package SocialNetworkAnalysisAndModels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.anselm.plm.utilobj.LogIt;

public class hw1 {
	
	public static void main(String[] args) {
		String filename = "com-amazon.ungraph.txt";
		//filename = "mini.txt";
		
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer,Integer> degreeToNodeNum = new HashMap<Integer, Integer>();
		Peter hw1 = new Peter();
		LogIt log = new LogIt();
		
		try {
			graph = hw1.readGraph(filename);
			if(args.length == 2)
				switch(args[0])
				{
					case "-deg":
						log.setLogFile("Degree.csv");
				
						degreeToNodeNum = hw1.eachDegreeOfNodeNum(graph);
						log.log("Degree,NodeNum");
						
						for(Integer i : degreeToNodeNum.keySet())
						{
							log.log(i+","+degreeToNodeNum.get(i));
						}
						break;
					case "-dim":
						log.setLogFile("Diameter.csv");
						log.log("Node ID,Depth");
						for(int nodeID:graph.keySet())
						{
							if(graph.get(nodeID).size() == 1)
								log.log(nodeID+","+hw1.BFS(graph,nodeID));
						}
						break;
					case "-acc":
						log.log("average clustering coef: "+hw1.ClusteringCoef(graph));
						break;
					default:
						System.exit(1);
				}
			else
			{
				int res = hw1.BFS(graph, 150736);
				System.out.print(res);
				//System.out.println(hw1.BFS(graph,1));
				//System.out.println(hw1.BFS(graph,548091));
				//hw1.setLogFileOfGraph(graph);
				//System.out.println(graph.get(548091).contains(88160));
				//log.log(graph.get(524280));//524280 CC: 0.3333333333333333  link Of Neighbor: 2
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
