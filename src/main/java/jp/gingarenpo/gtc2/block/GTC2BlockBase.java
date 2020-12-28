package jp.gingarenpo.gtc2.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

/**
 * いくつかの主要な共通メソッドを先にオーバーライドしてまとめたもの。基本的にこのクラスをオーバーライドして使用することが求められます。
 * このクラス自体は抽象クラスのためインスタンスの生成はできません。インスタンスの生成は継承先で行うようにしてください。
 */
public abstract class GTC2BlockBase extends BlockContainer {

	public GTC2BlockBase() {
		super(Material.ROCK); // 材質を石と仮定する
	}

	/**
	 * このブロックをレンダーするためのタイプを指定します。ただし、ここでは指定する必要がありません。後方互換性の為に予約しています。
	 * @param state そのブロックの状態。
	 * @return 以前は整数を返すはずだったけど今はEnumになったらしい
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return super.getRenderType(state);
	}

	/**
	 * このブロックが普通の立方体で構成されている場合はtrue、そうでない場合はfalseを返します。
	 * 非推奨になっていますが今はBlockStateで指定するのかもしれません。ただこれやらないとレンダーおかしくなるので使います。
	 * @param state そのブロックの状態。
	 * @return 立方体ならtrue、そうでないならfalse。
	 */
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false; // TileEntityで独自にレンダーするんだから立方体なわけがない
	}

	/**
	 * このブロックは完全な立方体かどうかを表すメソッド。そうでないならばfalseを返します。
	 * これも非推奨ですが大事を取ってオーバーライドしています。
	 * @param state ブロックの状態。
	 * @return true or false
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return false; // だから立方体じゃないんだってば
	}

	/**
	 * このブロックは透明度を持ったブロックかどうかを表すメソッド。実はこのメソッドで、カスタムレンダーに任せるかどうかを区別している
	 * そうです。ここをfalseとするとカスタムレンダーが事実上有効になります。
	 * 毎度の如く非推奨ですがもう気にしない方が吉です。
	 * @param state ブロックの状態。
	 * @return true or false
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * このブロックがレッドストーンと接続できるかを表します。trueを指定すると回路の一部とみなされるため、電力供給などをすることが
	 * できます。今回はレッドストーンには基本的に頼らないのでfalseを指定します。別途使用する場合はここを継承先でオーバーライドします。
	 * @param state ブロックの状態。
	 * @param world ブロックが置かれている世界。
	 * @param pos ブロックの座標（ワールド座標）。
	 * @param side レッドストーン信号がどの向きから来ているか。
	 * @return レッドストーン接続できるならtrue、できないならfalse
	 */
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return false;
	}
}
