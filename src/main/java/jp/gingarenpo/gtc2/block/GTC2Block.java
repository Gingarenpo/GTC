package jp.gingarenpo.gtc2.block;

import jp.gingarenpo.gtc2.GTC2;
import jp.gingarenpo.gtc2.interfaces.IInit;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * GTC2内で使用するブロックのインスタンス、ならびにそれを登録する処理を記したブロックのおおもととなるクラスです。
 * IInitを実装しています。ブロックの追加は優先度そこまで高くありませんが、依存している機能もあるので…。
 *
 * 基本的にブロックのインスタンスはここからアクセスします。
 */
public class GTC2Block implements IInit<FMLPreInitializationEvent> {

	/**
	 * 信号機用電柱のブロックインスタンス。
	 */
	public static BlockTrafficPole TRAFFIC_POLE;


	@Override
	public int getPriority() {
		return 5;
	}

	@Override
	public void init(FMLPreInitializationEvent e) {
		// インスタンスを生成
		TRAFFIC_POLE = new BlockTrafficPole(); // 電柱のインスタンスを生成。

		// インスタンス生成したブロックを登録
		ForgeRegistries.BLOCKS.register(TRAFFIC_POLE); // ブロックレジストリに登録

		// アイテムブロックとしても登録
		ForgeRegistries.ITEMS.registerAll
				(new ItemBlock(GTC2Block.TRAFFIC_POLE).setRegistryName(GTC2Block.TRAFFIC_POLE.getRegistryName())); // アイテムレジストリに登録

		// 【クライアント限定】ブロックのレンダーモデル登録処理（GTC2ClientProxyの「registerBlockModel」を呼び出す
		GTC2.proxy.registerBlockModel();
	}
}
