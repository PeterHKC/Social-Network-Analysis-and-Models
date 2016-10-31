package SocialNetworkAnalysisAndModels;

import java.io.*;
import java.util.*;

import com.anselm.plm.utilobj.LogIt;

public class hw2 {

	public static void main(String[] args)
	{
		
		LogIt log = new LogIt();
		
		ArrayList<String> grouplist = new ArrayList<String>();
		ArrayList<String> charactors = new ArrayList<String>();
		HashMap<String,ArrayList<String>> relationship = new HashMap<String,ArrayList<String>>();
		HashMap<Integer,ArrayList<String>> group = new HashMap<Integer,ArrayList<String>>();
		
		String filename = "新文字文件.txt";
		Peter hw2 = new Peter();
		try {
			log.setLogFile("result.txt");
			charactors = hw2.genActors("角色列表.txt");
			//System.out.println("讀取角色完畢");
			
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
			String line = null;
			
			int count = 0;
			
			
			while((line = buff.readLine()) != null)
			{
				String linetrim = line.trim();
				//System.out.println(linetrim);
				if(linetrim.indexOf("第") == 2 && (linetrim.indexOf("回") == 4 || linetrim.indexOf("回") == 5 || linetrim.indexOf("回") == 6))
				{
					ArrayList<String> templist = new ArrayList<String>(grouplist);

					//System.out.print("第"+count+"回");
					//System.out.println(linetrim);
					group.put(count, templist);
					count++;
					grouplist.clear();
				}
				for(int i = 0; i < charactors.size(); i++)
				{
					if(linetrim.contains(charactors.get(i)))
					{	
						if(grouplist.contains(charactors.get(i)) == false)
						{
							//System.out.print("得到 "+charactors.get(i)+" ");
							grouplist.add(charactors.get(i));
						}
					}
				}
			}
			ArrayList<String> templist = new ArrayList<String>(grouplist);

			//System.out.print("第"+count+"回");
			//System.out.println(line);
			group.put(count, templist);
			count++;
			for (String name:charactors)
			{
				ArrayList<String> temprelationship = new ArrayList<String>();
				for(Integer i : group.keySet())
				{
					if(group.get(i).contains(name) == false)
						continue;
					for(int k = 0; k < group.get(i).size(); k++ )
					{
						if(temprelationship.contains(group.get(i).get(k)) == false && group.get(i).get(k).equals(name) == false)
						{
							temprelationship.add(group.get(i).get(k));
						}
					}
					//System.out.println(name+"出現在第"+i+"回");
				}
				relationship.put(name, temprelationship);
				//System.out.println(name+": "+relationship.get(name));
				if(relationship.get(name).size() > 1)
				{
					log.log("{\"name:\""+name+"\",\r\n\"type\":\"group0\",\r\n\"depends\":"+relationship.get(name)+"},");
				}
			}
			//System.out.println("test: "+relationship.get(name));
			/*
			HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>(hw2.twoWay2OneWayGraph(relationship));
			
			for(String i:map.keySet())
			{
				System.out.println(i+": ");
				System.out.println(map.get(i));
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
}
