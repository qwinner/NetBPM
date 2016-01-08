package org.netbpm.gpd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.undo.UndoableEdit;

import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.Edge;
import org.jgraph.graph.ParentMap;
import org.netbpm.gpd.cell.DefaultGpdCell;
import org.netbpm.gpd.cell.Transition;


//
// Custom Model
//

// A Custom Model that does not allow Self-References

public class GpdGraphModel extends DefaultGraphModel {
	//Default constructor
		public GpdGraphModel() {
			super();
		}
		// Override Superclass Method
		public boolean acceptsSource(Object edge, Object port) {
			// Source only Valid if not Equal Target
			return (((Edge) edge).getTarget() != port);
		}

		public boolean acceptsTarget(Object edge, Object port) {
			// Target only Valid if not Equal Source
			return (((Edge) edge).getSource() != port);
		}

		public void  insert (Object[] roots, Map attributes, ConnectionSet cs, ParentMap pm, UndoableEdit[] e) {
			super.insert(roots, attributes, cs, pm, e);
		}

		protected void handleConnection(ConnectionSet.Connection c) {
			super.handleConnection(c);
		}
		public Map cloneCells(Object[] cells) {
			return super.cloneCells(cells);
		}

		public void remove(Object[] roots) {
			ArrayList returnArray = new ArrayList();
			for (int i=0;i<roots.length;i++){
				returnArray.add(roots[i]);
				if (!(roots[i] instanceof Transition) && roots[i] instanceof DefaultGpdCell){
					returnArray.addAll(findTransitionBySourceOrTarget((DefaultGpdCell)roots[i]));
				}
			}
			super.remove(returnArray.toArray());
		}

		/**
		 * Return a list of all transition that has the same source
		 * @param model
		 * @param startCell
		 * @return
		 */
		private List findTransitionBySourceOrTarget(DefaultGpdCell sourceCell) {
			List result=new LinkedList();
			int max = getRootCount();
			for (int i = 0; i < max; i++) {
				Object cell = getRootAt(i);
				if (cell instanceof Transition){
					Transition transitionCell = (Transition)cell;
					if (transitionCell.getSource()==sourceCell.getFirstChild()){
						result.add(transitionCell);
					} else if (transitionCell.getTarget()==sourceCell.getFirstChild()){
						result.add(transitionCell);
					}
				} 
			}

			return result;
		}

}
