package lo2.search.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class SearchQueue<N extends Comparable<N>> {
	private final Map<Integer, ArrayList<N>> map = new HashMap<>();
	private final Queue<N> queue;
	private final boolean enforceUnique;
	
	public SearchQueue(boolean enforceUnique, Queue<N> queue) {
		this.queue = queue;
		this.enforceUnique = enforceUnique;
	}
	public void replaceIfHigher(N replacement) {
		Integer key = replacement.hashCode();
		if(map.containsKey(key)) {
			List<N> values = map.get(key);
			if(!enforceUnique) {
				values.add(replacement);
				queue.add(replacement);
				return;
			}
			if(values.size() > 1) throw new RuntimeException("A size greater than 1 when enforceUnique is enabled should be impossible.");
			
			if(values.size() == 0) {
				values.add(replacement);
				queue.add(replacement);
			} else if(values.get(0).compareTo(replacement) > 0) {
				queue.remove(values.get(0));
				queue.add(replacement);
				values.clear();
				values.add(replacement);
			}
		} else {
			map.put(key, new ArrayList<>(Arrays.asList(replacement)));
			queue.add(replacement);
		}
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public boolean add(N value) {
		Integer key = value.hashCode();
		if(enforceUnique && map.containsKey(key)) return false;
		map.putIfAbsent(key, new ArrayList<N>());
		List<N> values = map.get(key);
		values.add(value);
		queue.add(value);
		return true;
	}
	
	public boolean contains(Object value) {
		return map.containsKey(value.hashCode());
	}
	
	public N poll() {
		N val = queue.poll();
		if(val == null) return null;
		
		
		Integer key = val.hashCode();
		List<N> values = map.get(key);
		if(values == null) {
			map.remove(key);
			return val;
		}
		
		int toRemove = -1;
		for(int i = 0; i < values.size(); ++i) {
			if(values.get(i) == val) {
				toRemove = i;
				break;
			}
		}
		if(toRemove != -1) {
			values.remove(toRemove);
		}
		if(values.size() == 0) map.remove(key);
		return val;
	}
	
	public void clear() {
		queue.clear();
		map.clear();
	}
}
