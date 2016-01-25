import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class minimumdistance 
{
	  public List<vertex> nodes;
	  public List<edge> edges;
	  public Set<vertex> settledNodes;
	  public Set<vertex> unSettledNodes;
	  public Map<vertex, vertex> predecessors;//2nd vertex is the predecessor of the 1st vertex 
	  public Map<vertex, Integer> distance;
	  //constructor takes all nodes and edges of the graph
	  public minimumdistance (graph graph) 
	  {
		    // create a copy of the array so that we can operate on this array
		    this.nodes = new ArrayList<vertex>(graph.getVertexes());
		    this.edges = new ArrayList<edge>(graph.getEdges());
	  }
	  //returns vertex OF THIS CLASS corresponding to a string
	  public vertex ret_vertex(String str)
	  {
		  //System.out.println("here"+str);
		  for (vertex vertex : nodes)
			{
			  	//System.out.println("1234"+vertex.name);
				if(vertex.name.equalsIgnoreCase(str))
				{
					return vertex;
				}
			}
			return null;
	  }
	  //It allots 0 distance to the source and acts as the starting point of this class
	  public void execute(vertex source) 
	  {
		    settledNodes = new HashSet<vertex>();
		    unSettledNodes = new HashSet<vertex>();
		    distance = new HashMap<vertex, Integer>();
		    predecessors = new HashMap<vertex, vertex>();
		    distance.put(source, 0);
		    unSettledNodes.add(source);
		    while (unSettledNodes.size() > 0) 
		    {
		      vertex node = getMinimum(unSettledNodes);
		      settledNodes.add(node);
		      unSettledNodes.remove(node);
		      findMinimalDistances(node);
		    }
	  }
	  //It returns the vertex with the minimum shortest distance
	  public vertex getMinimum(Set<vertex> vertexes) 
	  {
		    vertex minimum = null;
		    for (vertex vertex : vertexes) 
		    {
		      if (minimum == null) 
		      {
		        minimum = vertex;
		      } else 
		      {
		        if (getShortestDistance(vertex) < getShortestDistance(minimum)) 
		        {
		          minimum = vertex;
		        }
		      }
		    }
		    return minimum;
	  }
	  //if the distance has not been alloted till now then this it returns the max value else it returns the alloted distance
	  public int getShortestDistance(vertex destination) 
	  {
		    Integer d = distance.get(destination);
		    if (d == null) 
		    {
		      return Integer.MAX_VALUE;
		    } else 
		    {
		      return d;
		    }
	  }
	  //this function adds the neighbors of a vertex "which is being added onto the settled list" to the unsettled list and assigns them distance=distance(vertex)+edge  
	  public void findMinimalDistances(vertex node) 
	  {
		    List<vertex> adjacentNodes = getNeighbors(node);
		    for (vertex target : adjacentNodes) 
		    {
		      if (getShortestDistance(target) > getShortestDistance(node)+ getDistance(node, target)) 
		      {
		        distance.put(target, getShortestDistance(node)+ getDistance(node, target));
		        predecessors.put(target, node);
		        unSettledNodes.add(target);
		      }
		    }
	  }
	  //valid only when node is source and target is destination of any edge 
	  public int getDistance(vertex node, vertex target) 
	  {
		    for (edge edge : edges) 
		    {
		      if (edge.source.equals(node) && edge.destination.equals(target)) 
		      {
		        return edge.cost;
		      }
		    }
		    throw new RuntimeException("Should not happen");
	  }
	  //returns the neighbors which which are not in the settled list till now
	  public List<vertex> getNeighbors(vertex node) 
	  {
		    List<vertex> neighbors = new ArrayList<vertex>();
		    for (edge edge : edges) 
		    {
		      if (edge.source.equals(node) && !isSettled(edge.destination)) 
		      {
		        neighbors.add(edge.destination);
		      }
		    }
		    return neighbors;
	  }
	  //tells whether this node is settled or not
	  public boolean isSettled(vertex vertex) 
	  {
		    return settledNodes.contains(vertex);
	  }
	  //This method returns the path from the source to the selected target and NULL if no path exists, it uses the predecessor mapping to find out this path
	  public LinkedList<vertex> getPath(vertex target) 
	  {
		  //System.out.println("1"+target.name);
		    LinkedList<vertex> path = new LinkedList<vertex>();
		    vertex step = target;
		    // check if a path exists
		    if (predecessors.get(step) == null) 
		    {
		      return null;
		    }
		    path.add(step);
		    while (predecessors.get(step) != null) 
		    {
		      step = predecessors.get(step);
		      path.add(step);
		    }
		    // Put it into the correct order
		    Collections.reverse(path);
		    return path;
	  }

}
