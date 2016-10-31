package SocialNetworkAnalysisAndModels;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.anselm.plm.utilobj.LogIt;

public class Peter 
{
	
	//hw1 variable
	private static final int Nodes_Number = 600000;//plus 1 for array
	private int visited[] = new int[Nodes_Number];
	
	//hw2 variable
	
	public Peter(){}
	
	//hw1 method
	private void iniVisited()
	{
		for(int i = 0; i < Nodes_Number; i++)
			visited[i] = 0;
	}
	
	public void setLogFileOfGraph(HashMap<Integer, ArrayList<Integer>> graph)
	{
		LogIt log = new LogIt();
		try {
			log.setLogFile("Graph.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int nodeID : graph.keySet())
		{
			log.log("node ID: "+nodeID);
			log.log("=>"+graph.get(nodeID));
		}
		log.close();
	}
	
	public HashMap<String, ArrayList<String>> readNovel()
	{
		HashMap<String ,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		
		
		return map;
	}
	
	public int BFS(HashMap<Integer, ArrayList<Integer>> graph, int rootID)
	{
		iniVisited();
		/*
		LogIt log = new LogIt();
		try {
			log.setLogFile("BFS"+rootID+".csv");
			log.log("root,"+rootID);
			log.log("NodeID,Level");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>(); 
		
		
		int i = 0;
		int level = -1;
		int max_level = 0;
		//for(i = 1; i < visited.length; i++)
		//{
			//System.out.println(visited[i]+"..............."+i);
			if(level == -1)
				i = rootID;
			
			level = 0;
			if(visited[i] == 0)
			{
				queue.offer(i);
				visited[i] = 1;
				
				
				//System.out.println(i+","+level);
				while(queue.isEmpty() == false)
				{
					int nodeID = queue.poll();
					level = visited[nodeID];
					//log.log(nodeID+","+level);
					if(level > max_level)
						max_level = level;
					
					for(int k = 0; k < graph.get(nodeID).size(); k++)
					{
						int tempNode = graph.get(nodeID).get(k);
						if(visited[tempNode] == 0)
						{
							queue.offer(tempNode);
							visited[tempNode] = level+1;
							//System.out.println(tempNode+","+level);
						}
					}
				}
			}
		//}
		//log.log("max level,"+max_level);
		return max_level;
	}
	
	public double ClusteringCoef(HashMap<Integer, ArrayList<Integer>> graph)
	{
		LogIt log = new LogIt();
		try {
			
			log.setLogFile("ClusteringCoef.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.log("NodeID,CC,Link of Neighbor,Neighbor");
		double clusteringCoef = 0;
		for(int nodeID : graph.keySet())
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			list = graph.get(nodeID);
			int n = list.size();
			
			int linkOfNeighbor = 0;
			if(n == 1)
			{
				//System.out.println(nodeID+" CC: "+0+" link Of Neighbor: "+0);
				log.log(nodeID+",0,0,"+n);
				continue;
			}
			for(int i = 0; i < n; i++ )
			{
				for(int j = i; j < n; j++)
				{
					if(graph.get(list.get(i)).contains(list.get(j)) == true)
					{
						linkOfNeighbor++;
					}
				}
			}
			clusteringCoef = (double)2*linkOfNeighbor/(n*(n-1)) + clusteringCoef;
			//System.out.println(nodeID+" CC: "+(double)2*linkOfNeighbor/(n*(n-1))+"  link Of Neighbor: "+linkOfNeighbor);
			//log.log(nodeID+" CC: "+(double)2*linkOfNeighbor/(n*(n-1))+" link Of Neighbor: "+linkOfNeighbor);
			log.log(nodeID+","+(double)2*linkOfNeighbor/(n*(n-1))+","+linkOfNeighbor+","+n);
		}
		log.close();
		return clusteringCoef/(double)graph.size();
	}
	
	public HashMap<Integer, ArrayList<Integer>> readGraph(String filename) throws IOException
	{//com-amazon.ungraph.txt
		iniVisited();
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		FileReader file = new FileReader(filename);
		BufferedReader buff = new BufferedReader(file);
		String line = null;
		while((line = buff.readLine()) != null)
		{
			if(line.contains("#"))
				continue;
			else
			{
				
				String[] result = line.split("\\s");
			    
				int fromNode = Integer.valueOf(result[0]);
				int toNode = Integer.valueOf(result[1]);
				
				visited[fromNode] = 0;
				
				if(map.containsKey(fromNode) == false)
				{
					ArrayList<Integer> ary = new ArrayList<Integer>();
					ary.add(toNode);
					map.put(fromNode, ary);
				}
				else
				{
					map.get(fromNode).add(toNode);
				}
				
				if(map.containsKey(toNode) == false)
				{
					ArrayList<Integer> ary = new ArrayList<Integer>();
					ary.add(fromNode);
					map.put(toNode, ary);
				}
				else
				{
					map.get(toNode).add(fromNode);
				}
			}
		}
		
		return map;
	}
	
	public HashMap<Integer, Integer> eachDegreeOfNodeNum(HashMap<Integer, ArrayList<Integer>> graph)
	{
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(Integer nodeID : graph.keySet())
		{
			int degree = graph.get(nodeID).size();
			if(map.containsKey(degree) == false)
			{
				map.put(degree, 1);
			}
			else
			{
				int temp = map.get(degree) + 1;
				map.remove(degree);
				map.put(degree, temp);
			}
		}
		return map;
	}
	
	//hw2 method
	public ArrayList<String> genActors(String filename) throws IOException
	{
		ArrayList<String> charactors = new ArrayList<String>();
		//String filename = "角色列表.txt";
		String[] pat = {"—","'","（","“","「","："}; 
		LogIt log = new LogIt();
		log.setLogFile("角色列表result.txt");
		
		FileReader file = new FileReader(filename);
		BufferedReader buff = new BufferedReader(file);
		String line = null;
		while((line = buff.readLine()) != null)
		{
			for (String tmp : pat)
			{
				String str = null;
				str = getSubstringBefore(line,tmp);
				if(str != null && str.length() > 1)
				{
					charactors.add(str);
					log.log(str);
				}
			}	
		}
		
		return charactors;
	}
	
	private String getSubstringBefore(String line, String endstr)
	{
		if(line.contains(endstr))
		{
			int i = line.indexOf(endstr);
			if(i >= 6)
				return null;
			line = line.substring(0,i);
			return line;
		}
		else
			return null;
	}

	public HashMap<String,ArrayList<String>> twoWay2OneWayGraph(HashMap<String, ArrayList<String>> relationship)
	{
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		
		for(String i:relationship.keySet())
		{
			if(relationship.containsKey(i) == false)
			{
				ArrayList<String> tempList = new ArrayList<String>(relationship.get(i));
				for(String j: map.keySet())
				{
					tempList.remove(j);
				}
				map.put(i, tempList);
			}
			
		}
		
		
		return map;
	}
	
	
}
