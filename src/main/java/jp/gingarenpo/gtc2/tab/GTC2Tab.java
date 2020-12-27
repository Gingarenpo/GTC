package jp.gingarenpo.gtc2.tab;

import jp.gingarenpo.gtc2.GTC2;
import jp.gingarenpo.gtc2.block.GTC2Block;
import jp.gingarenpo.gtc2.interfaces.IInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GTC2Tab implements IInit<FMLPreInitializationEvent> {

	/**
	 * GTC2のタブを格納するインスタンス。
	 */
	public static CreativeTabs gtc2Tab;

	public void init(FMLPreInitializationEvent e) {
		// インスタンスを作成します
		// label →　「itemGroup.GTC2」という名前で言語キー指定できる
		gtc2Tab = new CreativeTabs("GTC2") {

			// タブに表示するアイコンを返します。これはItemStackを返さなくてはならないためBlockから生成します
			// 制御機のアイコンにしておきましょう。
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(GTC2Block.TRAFFIC_POLE); // 返しました
			}

		};

		// タブを登録します
	}

	@Override
	public int getPriority() {
		return 10; // ラストでいい
	}
}
