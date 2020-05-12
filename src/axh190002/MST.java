/**
 * IDSA Short Project 9
 * Team Members:
 * Adarsh Raghupati  NetID: axh190002
 * Keerti Keerti     NetID: kxk190012
 */

package axh190002;

import axh190002.BinaryHeap.Index;
import axh190002.BinaryHeap.IndexedHeap;
import axh190002.Graph.Edge;
import axh190002.Graph.Factory;
import axh190002.Graph.GraphAlgorithm;
import axh190002.Graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;
    
    MST(Graph g) {
	super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
    MSTVertex parentVertex;
    int distance;
    Vertex vertex;
    int rank;
    int index;
    boolean seen;
	
	MSTVertex(Vertex u) {
		parentVertex = this;
		rank=0;
		this.vertex =u;
	}

	MSTVertex(MSTVertex u) {  // for prim2
		this.vertex = u.vertex;
		this.distance = u.distance;
	}

	public MSTVertex make(Vertex u) { return new MSTVertex(u); }

	public void putIndex(int index) {
		this.index=index;
	}

	public int getIndex() { return this.index; }

	public int compareTo(MSTVertex other) {
		return Integer.compare(this.distance,other.distance);
	}
    }

    public long kruskal() {
	algorithm = "Kruskal";
	Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }

	/**
	 * Calculates weight of minimum spanning tree using boruvka's algorithm
	 * @return
	 */
    public long boruvka() {
	algorithm = "Boruvka";
	wmst = 0;
	Graph forestGraph = new Graph(this.g.n);
	//Start with forest
	MST forest = new MST(forestGraph);
	int count = countAndLabel(forest);
		Edge[] edgeArray = this.g.getEdgeArray();

	//Add all safe edges untill number the number of components becomes 1
	while (count>1){
		addSafeEdges(edgeArray,forest,count);
		//Count the no.of components and assign rank(component number to each vertex)
		count=countAndLabel(forest);
	}
	//Calculate the weight of obtained mst
	HashSet<String> set = new HashSet<>();
		Edge[] mstEdges = forest.g.getEdgeArray();
		for(Edge edge: mstEdges){
			 String str1 = edge.from.name+"-"+edge.to.name;
			 String str2 = edge.to.name+"-"+edge.from.name;
			if(!set.contains(str1)&& !set.contains(str2)){
				wmst+=edge.weight;
				set.add(str1);
			}

		 }

	return wmst;
    }

	/**
	 * Finds all safe edges and add them to the respective component in the forest
	 */

	private void addSafeEdges(Edge[] edgeList, MST forest, int count) {
    	Edge[] safe = new Edge[count];
        for(int i=0;i<count;i++){
        	safe[i] = null;
		}
        for(Edge e: edgeList){

			MSTVertex u = get(e.from);
			MSTVertex v = get(e.to);
        	if(get(e.from).rank != get(e.to).rank ){
        		if(safe[u.rank]==null || e.compareTo(safe[u.rank])<0){
        			safe[u.rank] = e;
				}
				if(safe[v.rank]==null || e.compareTo(safe[v.rank])<0){
					safe[v.rank] = e;
				}
			}
		}
        for(int i=0;i<count;i++){
        	Edge e = safe[i];

        	int wt = e.weight;
        	forest.g.addEdge(e.from,e.to,wt,0);
		}
	}

	/**
	 * Find the number of components and assign component number to each vertex
	 * @param mst
	 * @return
	 */
	private int countAndLabel(MST mst) {
    	int count=0;
    	for(Vertex vertex: mst.g.getVertexArray()){
			get(vertex).seen=false;
		}
		for(Vertex vertex: mst.g.getVertexArray()){
			if(get(vertex).seen==false){
				label(vertex,count,mst.g);
				count++;
			}
		}
       return count;
	}

	/**
	 * Does BFS iteration to find a component
	 * @param vertex
	 * @param count
	 * @param g
	 */
	private void label(Vertex vertex, int count, Graph g) {
    	Queue<Vertex> queue = new LinkedList<>();
    	queue.add(vertex);
    	while (queue.size()>0){
    		Vertex v = queue.poll();
    		if(get(v).seen==false){
    			get(v).seen=true;
    			get(v).rank=count;
				Iterable<Edge> edges = g.adj(v).outEdges;
				for (Edge e: edges){
					queue.add(e.to);
					queue.add(e.from);
				}
			}
		}
	}

	/**
	 * Calculates weight of minimum spanning tree using Prim's algorithm
	 * @param s
	 * @return
	 */
	public long prim2(Vertex s) {
	algorithm = "indexed heaps";
        mst = new LinkedList<>();
	wmst = 0;
	IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
		for(Vertex u : g){
			get(u).seen = false;
			get(u).parentVertex = null;
			get(u).distance = Integer.MAX_VALUE;
		}
		get(s).distance = 0;
		for(Vertex u : g){
			q.add(get(u));
		}
		while(!q.isEmpty()){
			MSTVertex u = q.remove();
			u.seen = true;
			wmst += u.distance;
			for(Edge e : g.incident(u.vertex)){
				MSTVertex v = get(e.otherEnd(u.vertex));
				if(!v.seen && e.getWeight() < v.distance){
					v.distance = e.getWeight();
					v.parentVertex = u;
					q.decreaseKey(v);
				}
			}
		}
	return wmst;
    }

    public long prim1(Vertex s) {
	algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<Edge> q = new PriorityQueue<>();
	return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
	MST m = new MST(g);
	switch(choice) {
	case 0:
	    m.boruvka();
	    break;
	case 1:
	    m.prim1(s);
	    break;
	case 2:
	    m.prim2(s);
	    break;
	case 3:
	    m.kruskal();
	    break;
	default:
	    
	    break;
	}
	return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	int choice = 0;  // prim3
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

	if (args.length > 1) { choice = Integer.parseInt(args[1]); }
	Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

	Timer timer = new Timer();
	MST m = mst(g, s, choice);
	System.out.println(m.algorithm + "\n" + m.wmst);
	System.out.println(timer.end());
    }
}
