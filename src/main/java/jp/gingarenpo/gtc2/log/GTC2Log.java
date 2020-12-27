package jp.gingarenpo.gtc2.log;

import jp.gingarenpo.gtc2.interfaces.IInit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GTC2Log implements IInit<FMLPreInitializationEvent> {

	private static Logger logger; // ログのキートリガー

	@Override
	public int getPriority() {
		return 0;
	}

	public void init(FMLPreInitializationEvent e) {
		logger = LogManager.getLogger("GTC2"); // Modのログを取得（イベントトリガーにはしなかった）
	}

	public static void log(String s) {
		logger.log(Level.INFO, s);
	}

	public static void warn(String s) {
		logger.log(Level.WARN, s);
	}

	public static void error(String s) {
		logger.log(Level.ERROR, s);
	}

	public static void fatal(String s) {
		logger.log(Level.FATAL, s);
	}

	public static void debug(String s) {
		logger.log(Level.DEBUG, s);
	}
}
