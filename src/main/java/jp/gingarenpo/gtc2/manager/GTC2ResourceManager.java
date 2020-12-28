package jp.gingarenpo.gtc2.manager;

import jp.gingarenpo.api.helper.GFileHelper;
import jp.gingarenpo.gtc2.interfaces.IInit;
import jp.gingarenpo.gtc2.log.GTC2Log;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * GTC2のリソースを読み込んで、そのリソースを保持しておくクラス。ZIPバンドルをパック分保持する形なので、メモリ不足に注意！
 * リソース関連はここからアクセスすることになります。IInitを継承しています。
 */
public class GTC2ResourceManager implements IInit<FMLPostInitializationEvent> {

	/**
	 * Zipファイルたちを代入しておくところ。ZipFileのキーはZipの名前（拡張子抜き）。
	 * InputStreamじゃないと開けないみたいで
	 */
	public static HashMap<String, ZipFile> zipFiles;

	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * ここでは、GTC2のリソースを読み込んでZIPバンドルに格納していきます。ZIPバンドルはハッシュマップによって管理されます。
	 * @param e クラス時に指定したイベント
	 */
	@Override
	public void init(FMLPostInitializationEvent e) {
		// ある程度固まってきたらSwingで情報を表示できるようにする
		File mods = GFileHelper.getModsDir(); // Modsフォルダを検証するため。
		File[] zips = mods.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// ファイルの名前で判断する
				return (name.endsWith(".zip"));
			}
		}); // 拡張子がzipであるものを全部抽出する（GTC用のリソースでなくても）
		GTC2Log.log(I18n.format("message.foundZip", zips.length)); // ログ

		zipFiles = new HashMap<String, ZipFile>(); // ここで初めて初期化する

		for (File zip : zips) {
			// zipファイルの数だけ繰り返す
			try {
				ZipFile zf = new ZipFile(zip, Charset.defaultCharset()); // ZipFileインスタンスとして取得する
				Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zf.entries(); // 全てのエントリを一時的に取得する
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement(); // 次のファイルを読み込む
					if (entry.getName().contains("gtc2.txt")) {
						// GTC2のリソースであることが保証されているのでZipFileをエントリーに追加
						String packID = ""; // ID
						try (Scanner s = new Scanner(zf.getInputStream(entry))) {
							// gtc2.txtに書いてある名前をResourceLocationで使用する名前とする
							// 1行しか読み込まない
							packID = s.nextLine(); // 1行だけ読み取り
						}
						zipFiles.put(packID, zf); // 追加する
						GTC2Log.log(I18n.format("message.loadZip", zip.getName())); // 完了ログ
						break;
					}
				}

				GTC2Log.log(I18n.format("message.completeZip", zipFiles.size())); // 完了ログを出す

			} catch (ZipException e2) {
				// Zipファイルの読み取りができなかった場合（Zip自体の不正）
				e2.printStackTrace();
				GTC2Log.warn(I18n.format("message.cannotOpenZip", zip.getName()));
			} catch (IOException e3) {
				// Zipファイルの読み取りができなかった場合（そもそもファイル自体読み込めない）
				e3.printStackTrace();
				GTC2Log.error(I18n.format("message.cannotOpenZip", zip.getName()));
			}
		}

	}

	/**
	 * ZIPファイルを指定するとそれのInputStreamを返します。
	 * @param file ファイル。
	 * @return そのZipを読み取るためのInputStream。
	 *
	 * @throws IOException 何らかの影響でファイルが開けない場合。
	 */
	private ZipInputStream openZipFile(File file) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(Files.readAllBytes(file.toPath())); // メモリに展開する
		return new ZipInputStream(bais); // メモリ上に確保する
	}
}
