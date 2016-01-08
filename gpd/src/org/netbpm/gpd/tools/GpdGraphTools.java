/*
 * @(#)GPGraphTools.java	1.2 11/11/02
 *
 * 2001 Gaudenz Alder
 */

package org.netbpm.gpd.tools;

import java.awt.Point;
import java.util.Map;

import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.netbpm.gpd.GpdGraph;

public class GpdGraphTools {

	public static void layout(GpdGraph graph) {
		Touch touchLayout=new Touch(graph);
		int delay = 250; //milliseconds
		touchLayout.resetDamper();
		touchLayout.start();
		try {
			Thread.sleep(delay);
		} catch (Exception ex) {
			// ignore
		} finally {
			touchLayout.stop();
			CellView[] all =
			graph.getGraphLayoutCache().getAllDescendants(
			graph.getGraphLayoutCache().getRoots());
			Map attributeMap =
				GraphConstants.createAttributes(all, graph.getGraphLayoutCache());
			graph.getGraphLayoutCache().edit(attributeMap, null, null, null);
		}

	}

	public static double getLength(CellView view) {
		double cost = 1;
		if (view instanceof EdgeView) {
			EdgeView edge = (EdgeView) view;
			Point last = null, current = null;
			for (int i = 0; i < edge.getPointCount(); i++) {
				current = edge.getPoint(i);
				if (last != null)
					cost += last.distance(current);
				last = current;
			}
		}
		return cost;
	}

}