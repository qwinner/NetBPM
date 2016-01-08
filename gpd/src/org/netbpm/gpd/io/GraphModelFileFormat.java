package org.netbpm.gpd.io;

import java.net.URL;
import java.util.Hashtable;

import org.jgraph.graph.GraphModel;
import org.netbpm.gpd.GpdGraph;

/**This is the interface to implement a file format
 * for a specific graph model provider.<br>
 * <br>
 * You must implement all methods and the corresponding
 * graph model provider must return this file format.
 *
 * @see GraphModelProvider#getGraphModelFileFormats()
 * @author luzar
 * @version 1.0
 */
public interface GraphModelFileFormat {

	/** Returns the File Extension for this
	 *  file format (without a point)
	 *
	 */
	public String getFileExtension();

	/** Writes the graph model with this file format.
	 *
	 *  @param file The file (with extension)
	 *  @param properties The write properties for this file format.
	 *  @param gpGraph The current graph.
	 *  @param graphModel The current graph model
	 */
	public void write(
		URL file,
		Hashtable properties,
		GpdGraph gpGraph,
		GraphModel graphModel)
		throws Exception;


	/** Reads the graph from the filename and returns the
	 *  new model for the graph.
	 *
	 *  @param filename The URL for the file
	 *  @param properties The properties for the read process
	 *  @param gpGraph The current Graph
	 *  @return The new Graph model or null to cancel the load
	 * 			process without an Exception.
	 */
	public GraphModel read(
		URL file,
		Hashtable properties,
		GpdGraph gpGraph)
		throws Exception;

}
