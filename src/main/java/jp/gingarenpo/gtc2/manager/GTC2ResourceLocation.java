package jp.gingarenpo.gtc2.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

/**
 * MinecraftのResourceLocationと相互変換できるようにどうにかこうにかしたいけどできなかったため独自クラスとしてまとめたものです。
 * GTC2のパックリソースを指定するクラスです。概ねResourceLocationと同じ使い方です。
 * GTC2用のリソースは「gtc2.txt」にIDを指定してあります。そのIDと読み替えてください。
 */
public class GTC2ResourceLocation {

	/**
	 * このリソースのパックID。
	 */
	private String packID;

	/**
	 * このリソースが指定するパス。
	 */
	private String path;

	/**
	 * 指定したパックIDと、指定したパスを使用して新しいリソースを指し示すResourceLocationを作成します。
	 * 存在チェックはここでは行いません。
	 * @param packID パックID。gtc2.txtに記しています。
	 * @param path そのリソースのパス。Zipファイルからのルートで記述します。
	 */
	public GTC2ResourceLocation(String packID, String path) {
		this.packID = packID;
		this.path = path;
	}

	/**
	 * 指定したリソース形式のパスを使用した新しいリソースを指し示すResourceLocationを作成します。
	 * 存在チェックは行いません。ここで指定するリソース形式とは、Minecraft系列の書式となります。
	 * 例えば、「hogehoge:textures/abc.png」みたいな感じです。なお、この形になっていない場合は
	 * 例外がスローされます。
	 *
	 * @param resourcePath リソース形式の書式。
	 *
	 * @throws IllegalArgumentException リソース形式書式ではないものが指定されたか、解釈できない場合。
	 */
	public GTC2ResourceLocation(String resourcePath) {
		if (!resourcePath.contains(":")) throw new IllegalArgumentException("resource path is invalid!");
		String[] parts = resourcePath.split(":"); // 分解する
		if (parts.length != 2) throw new IllegalArgumentException("Cannot parse input path!"); // 3つ以上あるのはおかしいし1つもおかしい
		this.packID = parts[0];
		this.path = parts[1];
	}

	/**
	 * 通常使用すべきではありません。このコンストラクタは事実上のnullを表します。
	 */
	private GTC2ResourceLocation() {

	}

	/**
	 * 指定したパスのリソースがあるかどうかを確認し、リソースを発見できた場合はそのリソースをもとに生成したGTC2ResourceLocationを
	 * 返します。存在しない場合はNullを返します。PostInitの後にモデルが読み込まれるため、それまではZipEntryが空の状態です。その際に
	 * アクセスした場合は例外がスローされます。
	 * なお、この検出方法はリソースパスが重複している場合、どのリソースが選ばれるかは不定です。
	 *
	 * @param path リソースが存在するか調べたいパス。
	 * @return リソースが存在する場合はそのリソースの位置を示すResourceLocation。ない場合はnull
	 *
	 * @throws IllegalStateException まだモデルが何一つ読み込まれていないのに実行しようとした場合
	 */
	public static GTC2ResourceLocation foundResource(String path) {
		// ZipFilesを参照するが
		GTC2ResourceLocation res = new GTC2ResourceLocation();
		if (GTC2ResourceManager.zipFiles == null) throw new IllegalStateException("Resources has not already loaded!!"); // ロードされていない
		// ループして探す
		GTC2ResourceManager.zipFiles.forEach((s, zipFile) -> {
			// ZipFile全てにおいて回す
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries(); // エントリー一覧を取得して
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement(); // 次のエレメントを取得
				if (entry.getName().contentEquals(path)) {
					// パスが見つかった場合は
					res.path = path;
					res.packID = s; // resに改めて代入する
				}
			}
		});

		return res;
	}

	/**
	 * このリソースが指し示すInputStreamを返します。もし、リソースの指し示す場所に何も存在しない場合はnullを返します。
	 * @return InputStream、存在しない場合はnull。
	 *
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public InputStream getInputStream() throws IOException {
		if (!this.exists()) return null; // 存在しないのでnull
		return GTC2ResourceManager.zipFiles.get(this.packID).getInputStream(
				GTC2ResourceManager.zipFiles.get(this.packID).getEntry(this.path)
		); // これで返せる
	}

	/**
	 * このリソースが指し示す場所に本当にリソースが存在しているかを返します。
	 * @return 存在する場合はtrue、しない場合はfalse。
	 */
	public boolean exists() {
		if (!GTC2ResourceManager.zipFiles.containsKey(this.packID)) return false; // そもそもそんなpackIDが存在しない
		ZipEntry entry = GTC2ResourceManager.zipFiles.get(this.packID).getEntry(this.path); // エントリーを取得してみる
		if (entry == null) return false; // nullが返されたときはエントリーが存在しない
		return true; // 存在するので返す
	}
}
