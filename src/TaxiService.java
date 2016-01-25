import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class TaxiService
{
	public graph graph;
	public List<taxi> taxis=new ArrayList<taxi>();
	
	public TaxiService() 
	{
		graph=new graph();
	}

	public void performAction(String actionMessage) 
	{
		System.out.println("action to be performed: " + actionMessage);
		String[] tokens = actionMessage.split(" ");
		String a1;
		a1=tokens[0];
		
		if(a1.equals("edge"))
		{
			vertex src=new vertex(tokens[1]);
			vertex des=new vertex(tokens[2]);
			int ct=Integer.parseInt(tokens[3]);
			
//			if(graph==null)
//			{
//				System.out.println("Yes");
//			}
			boolean afound=false;
			boolean bfound=false;
			for(vertex ver:graph.vertexes)
			{
				if(ver.name.equals(tokens[1]))
				{
					afound=true;
					src=ver;
					break;
				}
			}
			for(vertex ver:graph.vertexes)
			{
				if(ver.name.equals(tokens[2]))
				{
					bfound=true;
					des=ver;
					break;
				}
			}
			if(!afound)
			{
				graph.vertexes.add(src);
			}
			
			if(!bfound)
			{
				graph.vertexes.add(des);
			}
			edge edg=new edge(src,des,ct);
			edge ed=new edge(des,src,ct);
			graph.edges.add(edg);
			graph.edges.add(ed);
		}
		
		if(a1.equalsIgnoreCase("taxi"))
		{
			taxi tax=new taxi(tokens[1],tokens[2]);
			taxis.add(tax);
		}
		
		if(a1.equalsIgnoreCase("customer"))
		{
			minimumdistance min=new minimumdistance(graph);
			int customer_time=Integer.parseInt(tokens[3]);
			min.execute(min.ret_vertex(tokens[1]));
			System.out.println("Available Taxis:");
			int time=Integer.MAX_VALUE;
			taxi selec=null;
			for(taxi ta:taxis)
			{
				if(ta.time<=customer_time)
				{
					if(selec==null)
					{
						selec=ta;
					}
					System.out.print("Path of "+ta.name+" : ");
					LinkedList<vertex> pat=new LinkedList<vertex>();
					//System.out.println(min.ret_vertex(ta.currentpos)+"Ayush");
					//System.out.println(ta.currentpos+"this is ok");
					pat=min.getPath(min.ret_vertex(ta.currentpos));
					if(ta.currentpos.equals(tokens[1]))
					{
						System.out.print(ta.currentpos+".");
					}
					else
					{
						if(pat==null)
						{
							System.out.println("Path is not present");
						}
						Collections.reverse(pat);
						for (vertex ver:pat)
						{
							System.out.print(ver.name+", ");
						}
					}
					System.out.print(" Time taken is "+min.getShortestDistance(min.ret_vertex(ta.currentpos))+" units." );
					if(min.getShortestDistance(min.ret_vertex(ta.currentpos))<time)
					{
						time=min.getShortestDistance(min.ret_vertex(ta.currentpos));
						selec=ta;
					}
					System.out.println("");
				}
			}
			System.out.println("** Choose "+selec.name+" to service the customer request ***");
			
			selec.time=( (customer_time) +     (   min.getShortestDistance(min.ret_vertex(selec.currentpos))   )   +   ( min.getShortestDistance(min.ret_vertex(tokens[2]))     )    );
			selec.currentpos=tokens[2];	
			System.out.println("Path of customer: ");
			LinkedList<vertex> custo=min.getPath(min.ret_vertex(tokens[2]));
			for (vertex ver : custo)
			{
				System.out.print(ver.name+",");
			}
			System.out.println("Time Taken is "+min.getShortestDistance(min.ret_vertex(tokens[2]))+" units.");
			System.out.println("");
		}
		
		if(a1.equalsIgnoreCase("printTaxiPosition"))
		{
			int customer_time=Integer.parseInt(tokens[1]);
			for(taxi ta:taxis)
			{
				if(ta.time<=customer_time)
				{
					System.out.println(ta.name+" : "+ta.currentpos);
				}
			}
			System.out.println("");
		}
		
	}
}
