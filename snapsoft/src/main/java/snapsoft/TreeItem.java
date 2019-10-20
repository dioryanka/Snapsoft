package snapsoft;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {

	private String name;

	private List<TreeItem> children = new ArrayList<>();

	public TreeItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<TreeItem> getChildren() {
		return children;
	}

	public void addChild(TreeItem treeItem) {
		children.add(treeItem);
	}
}
