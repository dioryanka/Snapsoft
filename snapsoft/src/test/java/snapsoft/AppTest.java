package snapsoft;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class AppTest {

	private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetWritableFolderStructureWhenReadableFoldersAndWritableFoldersContainValidFolders() {
    	App app = new App();
		List<String> readableFolders = new ArrayList<>();
		List<String> writableFolders = new ArrayList<>();

		readableFolders.add("/var/main/java/snapsoft");
		readableFolders.add("/var/main/java");
		readableFolders.add("/var/main");
		readableFolders.add("/var");
		writableFolders.add("/var/main/java/snapsoft/App");
		writableFolders.add("/var/main/java/snapsoft/TreeItem");
		//We does not have any permission to access the test folder
		writableFolders.add("/var/test/main/java/TreeItem");

		TreeItem treeItem = app.getWritableFolderStructure(readableFolders, writableFolders);
		String result = "";
		String expected = "{\"name\":\"root\",\"children\":[{\"name\":\"var\",\"children\":[{\"name\":\"main\",\"children\":[{\"name\":\"java\",\"children\":[{\"name\":\"snapsoft\",\"children\":[{\"name\":\"App\",\"children\":[]},{\"name\":\"TreeItem\",\"children\":[]}]}]}]}]}]}";
		try {
			result = objectMapper.writeValueAsString(treeItem);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		assertEquals(result, expected);
    }

    @Test
	public void testGetWritableFolderStructureWhenReadableFolderIsEmpty() {
		App app = new App();
		List<String> readableFolders = new ArrayList<>();
		List<String> writableFolders = new ArrayList<>();

		writableFolders.add("/var/main/java/snapsoft/App");
		writableFolders.add("/var/main/java/snapsoft/TreeItem");

		TreeItem treeItem = app.getWritableFolderStructure(readableFolders, writableFolders);
		assertNotNull(treeItem);
		assertEquals(treeItem.getName(), "root");
		assertTrue(treeItem.getChildren().isEmpty());
	}

	@Test
	public void testGetWritableFolderStructureWhenWritableFolderIsEmpty() {
		App app = new App();
		List<String> readableFolders = new ArrayList<>();
		List<String> writableFolders = new ArrayList<>();

		readableFolders.add("/var/main/java/snapsoft");
		readableFolders.add("/var/main/java");
		readableFolders.add("/var/main");
		readableFolders.add("/var");

		TreeItem treeItem = app.getWritableFolderStructure(readableFolders, writableFolders);
		assertNotNull(treeItem);
		assertEquals(treeItem.getName(), "root");
		assertTrue(treeItem.getChildren().isEmpty());
	}

	@Test
	public void testGetWritableFolderStructureWhenReadableFolderAndWritableFolderAreEmpty() {
		App app = new App();
		List<String> readableFolders = new ArrayList<>();
		List<String> writableFolders = new ArrayList<>();

		TreeItem treeItem = app.getWritableFolderStructure(readableFolders, writableFolders);
		assertNotNull(treeItem);
		assertEquals(treeItem.getName(), "root");
		assertTrue(treeItem.getChildren().isEmpty());
	}


}
