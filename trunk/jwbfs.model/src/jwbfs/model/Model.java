package jwbfs.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.Constants;
import jwbfs.model.utils.FileUtils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class Model {
	
	private GameBean gameBean = new GameBean();
	private GameBean selectedGame = new GameBean();
	private SettingsBean settingsBean = new SettingsBean();
	private static List<GameBean> games = new ArrayList<GameBean>();
	
	protected static LinkedHashMap<String, Object> beans = new LinkedHashMap<String, Object>();
	
	public Model(){
		
		games = Model.listGames(settingsBean.getDiskPath());
		
		beans.put(GameBean.INDEX, gameBean);
		beans.put("selectedGame", selectedGame);
		beans.put(SettingsBean.INDEX, settingsBean);
		beans.put("games",games);

	}
	
	public static LinkedHashMap<String, Object> getBeans(){
		return beans;
	}


	public static void setSelectedGame(GameBean selectedGame) {
		Model.getBeans().put("selectedGame",selectedGame);
	}
	
	public static void setGames(List<GameBean> games) {
		Model.games = games;
	}

	public static List<GameBean> getGames() {
		return games;
	}

	public static GameBean getSelectedGame() {
		return (GameBean) Model.getBeans().get("selectedGame");
	}
	
	public static SettingsBean getSettingsBean() {
		return (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
	}
	
	public static GameBean getConvertGameBean() {
		return (GameBean) Model.getBeans().get(GameBean.INDEX);
	}
		
	public static List<GameBean> listGames(String folder) {
		List<GameBean> game = new ArrayList<GameBean>();
		
		FileUtils.checkAndCreateFolder(folder);
		
		File f = new File(folder);
		File[] folderList = f.listFiles();
		if(folderList == null){ //NO GAMES in folder
			return game;
		}
				
		for(int x = 0; x<folderList.length;x++){
			File f2 = folderList[x];
			if(f2.isDirectory()){
				File[] files = f2.listFiles();
				if(files != null){
					for(int j = 0; j < files.length; j++){
						game = checkFile(files,j,game);
						if(game.size() > j){
							setSelectedGame(game.get(j));
							
							IHandlerService handlerService = (IHandlerService) 
												PlatformUI.getWorkbench()
												.getActiveWorkbenchWindow()
												.getService(IHandlerService.class);
							
							try {
								handlerService.executeCommand(Constants.COMMAND_CHECKDISK_ID, null);
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NotDefinedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NotEnabledException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NotHandledException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					

					}
				}
			}else{
				game = checkFile(folderList,x,game);
			}
		}
		
		return game;
	}

	private static List<GameBean> checkFile(File[] files, int j, List<GameBean> game) {
		if(files[j].getName().endsWith(".wbfs")){
			GameBean g = new GameBean();
			g.setFilePath(files[j].getAbsolutePath());
			System.out.println(g.getFilePath());
			game.add(g);
		}
		return game;
	}

}
