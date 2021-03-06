package jadx.core.dex.instructions;

import jadx.core.dex.info.MethodInfo;
import jadx.core.dex.instructions.args.ArgType;
import jadx.core.dex.instructions.args.InsnArg;
import jadx.core.dex.nodes.InsnNode;
import jadx.core.utils.InsnUtils;
import jadx.core.utils.Utils;

import com.android.dx.io.instructions.DecodedInstruction;

public class InvokeNode extends InsnNode {

	private final InvokeType type;
	private final MethodInfo mth;

	public InvokeNode(MethodInfo mth, DecodedInstruction insn, InvokeType type, boolean isRange, int resReg) {
		super(InsnType.INVOKE, mth.getArgsCount() + (type != InvokeType.STATIC ? 1 : 0));
		this.mth = mth;
		this.type = type;

		if (resReg >= 0) {
			setResult(InsnArg.reg(resReg, mth.getReturnType()));
		}

		int k = isRange ? insn.getA() : 0;
		if (type != InvokeType.STATIC) {
			int r = isRange ? k : InsnUtils.getArg(insn, k);
			addReg(r, mth.getDeclClass().getType());
			k++;
		}

		for (ArgType arg : mth.getArgumentsTypes()) {
			addReg(isRange ? k : InsnUtils.getArg(insn, k), arg);
			k += arg.getRegCount();
		}
	}

	public InvokeType getInvokeType() {
		return type;
	}

	public MethodInfo getCallMth() {
		return mth;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InvokeNode) || !super.equals(obj)) {
			return false;
		}
		InvokeNode that = (InvokeNode) obj;
		return type == that.type && mth.equals(that.mth);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + type.hashCode();
		result = 31 * result + mth.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return InsnUtils.formatOffset(offset) + ": "
				+ InsnUtils.insnTypeToString(insnType)
				+ (getResult() == null ? "" : getResult() + " = ")
				+ Utils.listToString(getArguments())
				+ " " + mth
				+ " type: " + type;
	}
}
