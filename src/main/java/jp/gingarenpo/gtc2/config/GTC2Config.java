package jp.gingarenpo.gtc2.config;

import jp.gingarenpo.gtc2.GTC2;
import jp.gingarenpo.gtc2.interfaces.IInit;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * GTC2専用のコンフィグファイルとなります。コンフィグファイルはMod名で作られます。Forge式コンフィグを利用しています。
 * なお、ここではinitを行うだけで、実際のコンフィグはstaticのクラスに格納しています。
 * GUIでの書き換えは技術がなくできないです。。。
 */

public class GTC2Config implements IInit<FMLPreInitializationEvent> {


	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public void init(FMLPreInitializationEvent e) {
		// コンフィグファイルを作成して代入する
		GTC2.config = new Configuration(new File(e.getModConfigurationDirectory().toString() + "/" + GTC2.MODID + ".cfg")); // コンフィグ作成
	}

	/**
	 * 実際に格納されるコンフィグファイルです。staticアクセスを可能としているので、どこでもアクセスすることができます。
	 */
	@Config(modid = GTC2.MODID, name = GTC2.MODID, type = Config.Type.INSTANCE, category = "general")
	public static class Config4GTC2 {

		/**
		 * （実装する見込みは限りなく薄い）カスタムスクリプトのON / OFF切り替え。常時ONでいいと思うので…。
		 */
		@Config.LangKey("config.useCustom")
		@Config.Comment({"カスタムスクリプトを使用するかしないかを指定します。", "常時ONにしておくべきです。つまりtrueのまま変更しない方がいいです。"})
		public static boolean useCustom = true;
	}
}
