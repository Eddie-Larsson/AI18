package lo2.search.framework;

import ch.rfin.ai.search.Node;

public class NodeWrapper<S, A> implements Comparable<NodeWrapper<S, A>> {
	public final Node<S, A> node;
	
	public NodeWrapper(Node<S, A> node) {
		assert(node != null);
		assert(node.state != null);
		this.node = node;
		
	}

	@Override
	public int hashCode() {
		return node.state.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if(node.state.getClass() == obj.getClass()) {
			return node.state.equals(obj);
		}
		if (getClass() != obj.getClass()) return false;
		
		NodeWrapper<?, ?> other = (NodeWrapper<?, ?> ) obj;
		if(other.node == null) return false;
		if(other.node.state == null) return false;
		
		return node.state.equals(other.node.state);
	}

	@Override
	public int compareTo(NodeWrapper<S, A> o) {
		return node.compareTo(o.node);
	}
	
	@Override
	public String toString() {
		return node.state.toString();
	}
}
