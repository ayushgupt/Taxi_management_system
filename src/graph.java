import java.util.ArrayList;
import java.util.List;


public class graph 
{
	public List<vertex> vertexes=new ArrayList<vertex>();
	public List<edge> edges=new ArrayList<edge>();
	public List<vertex> getVertexes()
	{
		return vertexes;
	}
	public List<edge> getEdges()
	{
		return edges;
	}
	public vertex ret_vertex(String str)
	{
		for (vertex vertex : vertexes)
		{
			if(vertex.name.equals(str))
			{
				return vertex;
			}
		}
		return null;
	}
	
	
	
}
