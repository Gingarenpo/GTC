package jp.gingarenpo.gtc2;

import jp.gingarenpo.api.core.Version;
import jp.gingarenpo.api.interfaces.IModCore;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.ArrayList;

/**
 * RTM対応版GTC0.0～1.0の機能を活かしつつ、独立して作動させることができるようになったMod。
 * 前提Modとして使用するのは、「GingaCore」のみとなります。
 *
 * 当ModはRTM対応版GTCと共存できるように作られています。したがって、当Modは「GTC2」という名称になります。
 * バージョン番号も2.0より割り振りを始める予定です。
 *
 * @version 2.0
 * @author 銀河連邦
 * @since 2020-12-23
 *
 * This Mod's description, comment, and other are only Japanese.
 */

@Mod(modid = GTC2.MODID, name = GTC2.MOD_NAME, version = GTC2.STRING_VERSION, dependencies = "required-after:rtm;required-after:gingacore@[2.2,)")
public class GTC2 implements IModCore {
	/* ---------------------------- ここから定数 ---------------------------------- */
	/**
	 * このModを一意に識別するModIDとなります。ModIDは重複してはならず、既に「gtc」はRTM版で使用しているため当Modは「gtc2」です。
	 * 小文字じゃないといけないので若干めんどくさいですが…。
	 */
	public static final String MODID = "gtc2";

	/**
	 * このModの名前を指定します。tomlみたいなファイルに記してもいいんですが、旧来のやり方のほうが好きだし覚えていないのでこのまんま。
	 * こちらに関しても重複はOKにしろなんか嫌なので別名称にしてあります。
	 */
	public static final String MOD_NAME = "GTC2 - Ginren Traffic Control Mod Ver.2";

	/**
	 * このModのメジャーバージョンを入力します。基本的に「2」です。これ以外変更しません。
	 */
	public static final int MAJOR_VERSION = 2;

	/**
	 * このModのマイナーバージョンを指定します。細やかなバージョン変化で使用します。
	 * ここは将来仕様変更する可能性があります（配列にしたい）
	 */
	public static final int MINOR_VERSION = 0;

	/**
	 * このModのビルド番号を指定します。ビルド番号はバージョン間で固有のものにする必要があり、整数（1～2147483647）にする必要があります。
	 * 規則に関しては内部のみで使用しているので公開するつもりはありません。
	 */
	public static final int BUILD_VERSION = 1; // ちなみにここを-1にするとそのバージョンの正式版となります

	/**
	 * このModのバージョンをインスタンス化したものです。GingaCoreの「Version」クラスを参照してください。
	 * 主にバージョンを比較するために使用するものです。
	 * 現在決め打ちで0番目しか利用していませんが、これは処理を実装できないからです。。。
	 */
	public static final Version VERSION = new Version(MAJOR_VERSION + "." + MINOR_VERSION, BUILD_VERSION);

	/**
	 * このModのバージョンを文字列として格納したものになります。これも定数として式を確定させないとアノテーションで使用できないので…
	 * 書式（2.0とか2.1 build1とか）
	 */
	public static final String STRING_VERSION =
			MAJOR_VERSION + "." + MINOR_VERSION + ((BUILD_VERSION > 0) ? " build" + BUILD_VERSION : "");

	/**
	 * Mod内で使用するネットワークハンドラを作成します。パケットアダプタを介してサーバーとクライアントで同期をとるのに必要な
	 * インスタンスとなります。
	 */
	public static final SimpleNetworkWrapper MOD_NETWORK =
			NetworkRegistry.INSTANCE.newSimpleChannel(MODID); // おまじないのようだけどしかたない。

	/**
	 * Modの設定を格納するコンフィグインスタンスとなります。すべてはここから読み出し、書き込みを行います。
	 */
	public static Configuration config; // 使用する場合に初期化は別メソッドで行います。

	/**
	 * Mod内で自分自身のインスタンスを参照したいときに使う自分自身のインスタンス変数です。ネットワークなどの設定を行う際に必要です。
	 */
	public static GTC2 instance; // ここではインスタンスは生成されていないため代入することができない

	/**
	 * Mod内で開くべきGUIのIDを設定したものです。GUIIDはめったに使いませんが、決め打ちでやるのもどうかと思ったので配列で管理しています。
	 */
	public static ArrayList<String> GUI_ID = new ArrayList<String>(); // indexがGUIID、StringがそのGUIを示すキー。

	/* ---------------------------- ここまで定数 ---------------------------------- */

	/* ---------------------------- ここからイベントメソッド ---------------------------------- */

	/**
	 * Minecraftが起動した後、CoreModが読み込まれたのちにすぐ実行するイベントメソッドです。Forgeでは非推奨となっています。
	 * ただ、何よりも先にインスタンス代入などの処理を行いたいため、当イベントメソッドを使用します。
	 */
	@EventHandler
	public void construct(FMLConstructionEvent e) {
		instance = this; // 自分自身のインスタンスを入れ込みます。
	}

	/**
	 * MinecraftにおいてConstructの次のステージとなります。ここで8割の作業を行います。このクラスに全てを記述するとソースが肥大化
	 * してしまうため、すべてのクラスにおいてinitメソッドを使用して初期化しています。
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {

	}

	/**
	 * Minecraftの第3段階の処理ステージで、レシピなどの追加はここで行います。
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {

	}

	/**
	 * Modの最終読み込みステージとなります。この時点で、ほかのModのリソースなどはすべて読み込まれているので、ワールド起動前に
	 * 何かしたい場合は利用できます。ただこのModでは特に使うつもりがありません。
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

	}

	/* ---------------------------- ここまでイベントメソッド ---------------------------------- */

	/* ---------------------------- ここからIModCore実装 ---------------------------------- */

	@Override
	public int getMajorVersion() {
		return GTC2.MAJOR_VERSION; // メジャーバージョンを返します
	}

	@Override
	public int[] getMinorVersion() {
		return new int[] { GTC2.MINOR_VERSION }; // マイナーバージョンを返します
	}

	@Override
	public String getModId() {
		return GTC2.MODID; // ModIDを返します
	}

	@Override
	public String getURL() {
		return "https://ginren.info/G-factory/Data/Addon/minecraft_new.txt"; // 更新内容が記されている場所です。
	}

	/* ---------------------------- ここまでIModCore実装 ---------------------------------- */

	/* ---------------------------- ここから独自メソッド実装 ---------------------------------- */

	/**
	 * 指定したGUI名称でGUIを登録します。GUI名を渡すと、そのstringに対応するGUIIDが返ってきます。以降、そこで指定したGUI名を
	 * 使用してIDを取得することができます。
	 *
	 * @param name GUIの名前。
	 * @return int GUIID。
	 */
	public int registerGUI(String name) {
		GTC2.GUI_ID.add(name); // 名前をキーとするものを配列に追加
		return GTC2.GUI_ID.size() - 1; // 配列は0から始まるのでindexも1減らさないといけない
	}
}
