package jwbfs.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;

public class Model {
	
	private GameBean gameBean = new GameBean();
	private GameBean selectedGame = new GameBean();
	private SettingsBean settingsBean = new SettingsBean();
	private static List<GameBean> games = new ArrayList<GameBean>();
	
	protected static LinkedHashMap<String, Object> beans = new LinkedHashMap<String, Object>();
	
	public Model(){
				
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
	
	public static GameBean getExportGameBean() {
		return (GameBean) Model.getBeans().get(GameBean.INDEX);
	}

	public static void setExportGameBean(GameBean exportGame) {
		Model.getBeans().put(GameBean.INDEX,exportGame);
	}
	
	public static void cleanSelectedGame() {
		setSelectedGame(new GameBean());
	}
	public static void cleanExportGame() {
		setExportGameBean(new GameBean());
	}
}
