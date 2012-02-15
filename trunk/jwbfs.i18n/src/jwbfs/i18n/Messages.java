package jwbfs.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "jwbfs.i18n.messages"; //$NON-NLS-1$
	public static String generic_not_linux;
	public static String icon_creation_creating;
	public static String icon_creation_error;
	public static String icon_creation_exists;
	public static String icon_creation_prompt;
	public static String settings_cover_2d;
	public static String settings_cover_3d;
	public static String settings_cover_3d_download;
	public static String settings_cover_disc;
	public static String settings_cover_disc_download;
	public static String settings_cover_enable;
	public static String settings_cover_save_path;
	public static String settings_group_cover;
	public static String settings_group_cover_type;
	public static String settings_group_txt_file;
	public static String settings_partitions;
	public static String settings_partitions_copy;
	public static String settings_partitions_split_size;
	public static String settings_region;
	public static String settings_tab_disk;
	public static String settings_tab_general;
	public static String settings_txt_file_enable;
	public static String settings_txt_file_layout;
	public static String settings_update;
	public static String settings_usb_loader_cfg;
	public static String settings_usb_loader_gx;
	public static String settings_usb_loader_wiiflow;
	public static String view_actions;
	public static String view_add;
	public static String view_cover;
	public static String view_cover_disc;
	public static String view_delete;
	public static String view_disk_open;
	public static String view_disk_path;
	public static String view_export;
	public static String view_gamelist_column_id;
	public static String view_gamelist_column_name;
	public static String view_gamelist_column_selection;
	public static String view_gamelist_column_size;
	public static String view_gamelist_update;
	public static String view_table_rename_tooltip;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
