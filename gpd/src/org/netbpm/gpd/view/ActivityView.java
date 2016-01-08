package org.netbpm.gpd.view;

import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;
import org.netbpm.gpd.renderer.ActivityRenderer;

public class ActivityView extends VertexView {

	public static ActivityRenderer renderer = new ActivityRenderer();

	public ActivityView(Object cell, JGraph graph, CellMapper cm) {
		super(cell, graph, cm);
	}

	

	public CellViewRenderer getRenderer() {
		if (renderer==null)
			renderer= new ActivityRenderer();
		return renderer;
	}
}
