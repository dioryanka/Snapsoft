package snapsoft;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * App class contains the implementation of getWritableFolderStructure
 *
 * @author Gabor Csukonyi email: dioryanka@gmail.com
 */
public class App {

    public TreeItem getWritableFolderStructure(List<String> readableFolders,
                                               List<String> writableFolders) {

        TreeItem treeItem = new TreeItem("root");
        if (isEmpty(readableFolders) || isEmpty(writableFolders)) {
            return treeItem;
        }

        sortListByNumberOfSlashes(readableFolders);
        sortListByNumberOfSlashes(writableFolders);
        Set<String> readableAndWritableFolders = getReadableFolderNames(readableFolders);

        for (String writableFolder : writableFolders) {
            List<String> folderNames = Arrays.asList(writableFolder.split("/"));
            String lastFolderName = folderNames.get(folderNames.size() - 1);
            if (!folderNames.isEmpty() && containsOnlyAccessibleParentFolders(
                folderNames, readableAndWritableFolders, lastFolderName)) {
                readableAndWritableFolders.add(lastFolderName);
                //exclude unnecessary empty character
                Iterator<String> iterator = folderNames.iterator();
                iterator.next();
                buildTree(treeItem, iterator);
            }
        }

        return treeItem;
    }

    private void buildTree(TreeItem root, Iterator<String> folderNames) {
        TreeItem treeItem = root;
        String folderName = folderNames.next();
        TreeItem subTreeItem;
        if (treeItem.getChildren().stream().anyMatch(item -> folderName.equals(item.getName()))) {
            subTreeItem = treeItem.getChildren().stream()
                .filter(item -> folderName.equals(item.getName())).findFirst().orElse(null);
        } else {
            subTreeItem = new TreeItem(folderName);
            treeItem.addChild(subTreeItem);
        }

        if (folderNames.hasNext()) {
            buildTree(subTreeItem, folderNames);
        }
    }

    private void sortListByNumberOfSlashes(List<String> folders) {
        Collections.sort(folders, (o1, o2) -> {
            // if you use org.apache.commons:commons-lang3 dependency then
            // you can replace the following calculation with StringUtils.countMatches
            long o1NumberOfSlashes = o1.chars().filter(ch -> ch == '/').count();
            long o2NumberOfSlashes = o2.chars().filter(ch -> ch == '/').count();
            return Long.compare(o1NumberOfSlashes, o2NumberOfSlashes);
        });
    }

    private Set<String> getReadableFolderNames(List<String> folders) {
        final Set<String> result = new HashSet<>();

        result.add("");
        folders.stream().forEach(str -> {
            List<String> folderNames = Arrays.asList(str.split("/"));
            String lastFolderName = folderNames.get(folderNames.size() - 1);

            if (containsOnlyAccessibleParentFolders(folderNames, result, lastFolderName)) {
                result.add(lastFolderName);
            }
        });

        return result;
    }

    private boolean containsOnlyAccessibleParentFolders(List<String> folderNames,
                                                        Set<String> readableFolderNames,
                                                        String lastFolderName) {
        for (String folderName : folderNames) {
            if (!folderName.equals(lastFolderName) && !readableFolderNames.contains(folderName)) {
                return false;
            }
        }

        return true;
    }

    private boolean isEmpty(List<String> folders) {
        return folders == null && folders.isEmpty();
    }
}
