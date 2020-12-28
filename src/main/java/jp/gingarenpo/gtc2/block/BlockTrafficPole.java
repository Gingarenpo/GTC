package jp.gingarenpo.gtc2.block;

import jp.gingarenpo.gtc2.GTC2;
import jp.gingarenpo.gtc2.tab.GTC2Tab;
import jp.gingarenpo.gtc2.tileentity.TileEntityTrafficPole;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * このブロックインスタンスは、信号機の電柱とアームを設置するためのブロックを作成するものとなっています。
 * 電柱の作成方法はやや特殊です。まず必要なMQOファイルについての仕様です。仕様は頻繁に変更されます。ご注意ください。
 * なお、電柱とアームはセットとなっているのでご注意ください！
 *
 * MQOファイルは、高さを1に圧縮する形で使用するため、できることなら-0.5～0.5の範囲で作成するべきです。
 * そうしない場合でも勝手に最適化されますが、等倍縮小を試みるためめっちゃくちゃ細い電柱になったりしてしまう可能性が高いです。
 * サンプルの電柱MQOファイルを用意しているので、そちらをご覧いただきながら制作してみてください。
 *
 * MQOファイルには、「電柱」「接続部分」の2つのオブジェクトが必要となります（正確には接続部分が複数あります）。
 * 接続部分に関しては、「片方接続」「両方接続」の2パターンが必要です。両方接続がない場合は片方接続で描画するため少々みすぼらしくなります。
 *
 * オブジェクト名は、JSONファイルで指定します。その書き方に関してはもうここに書くと肥大化しまくるので割愛します。
 *
 * ソースコード閲覧者に対する説明は後々コメントで書き加えます。
 */
public class BlockTrafficPole extends GTC2BlockBase {

	/**
	 * コンストラクタにおいて、このブロックの主要なデータを決めてしまいます。
	 */
	public BlockTrafficPole() {
		super();
		this.setRegistryName(new ResourceLocation(GTC2.MODID, "gtc2_traffic_pole")); // ブロックの登録名。
		this.setUnlocalizedName("gtc2_traffic_pole"); // ブロックの翻訳をする際に使うキー。「tile.gtc2_traffic_pole.name」とか
		this.setResistance(800.0f); // 爆破耐性。クリエイティブで使うことを想定しているので設定の意味はないんだけど
		this.setCreativeTab(GTC2Tab.gtc2Tab);
	}

	/**
	 * このブロックのTileEntityを返します。TileEntityで返されるのは、このブロック自身のもので固有です。基本的に処理はこちらで
	 * 行っていくので、ここでは普通に新しいTileEntityを返せばOKです。
	 * @param worldIn このブロックが置かれているWorldのオブジェクト。
	 * @param meta このブロックのメタデータ（0-15）。使いません。
	 * @return
	 */
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTrafficPole(); // 新しいTileEntityを作成
	}

	/**
	 * このブロックが何かの現象によって破壊されたときに呼び出されます。誰が（or何が）壊したかに限らず、壊されたときに呼び出されます。
	 * onDestroy～のあとにおそらく実行されます。この中でTileEntityを消去する処理を行っていそうなので、superの前にTE関連は
	 * 処理してしまったほうがよさそうです。
	 *
	 * @param worldIn このブロックが置かれていた世界。
	 * @param pos このブロックが置かれていた座標。
	 * @param state このブロックの状態。
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * このブロックが誰かによって設置されたときに呼び出されます。Playerの可能性もありますし、例えばエンダーマンなどが持ち運びをする
	 * 場合もあります。ここではPlayerしか置けないはず（何か追加で処理するModを入れない限り）なので、基本的にPlayerであることを前提に
	 * コードを記述しています。
	 *
	 * （Override元）このメソッドはItemBlockがBlockとして世界に置かれたあとに呼び出されます。設置後の処理を主に記述します。
	 *
	 * @param worldIn このブロックが置かれている世界。
	 * @param pos このブロックが置かれている座標。
	 * @param state このブロックの状態。
	 * @param placer このブロックを置いたEntity。
	 * @param stack このブロックを置いたときにそのEntityがもっていたItemStackだろうけどよくわからん
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
}
