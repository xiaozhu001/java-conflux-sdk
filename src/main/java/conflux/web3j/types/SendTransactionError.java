package conflux.web3j.types;

import org.web3j.protocol.core.Response.Error;

public enum SendTransactionError {
	
	Rlp(null, "rlp", "Rlp", "RLP"),
	TxAlreadyExists("tx already exist"),
	TxPoolFull("txpool is full"),
	
	InvalidEpochHeight("transaction epoch height"),
	InvalidChainId("transaction chain_id"),
	InvalidGasLimitExceedsMax("transaction gas", "exceeds the maximum value"),
	InvalidGasLimitLessThanIntrinsic("transaction gas", "less than intrinsic gas"),
	InvalidGasPriceTooSmall("transaction gas price", "less than the minimum value"),
	InvalidSignature(null, "invalid signature", "Invalid EC signature", "Cannot recover public key"),
	InvalidNonceTooStale("Transaction", "is discarded due to a too stale nonce"),
	InvalidNonceTooFuture("Transaction", "is discarded due to in too distant future"),
	
	Internal(null, 
			"Failed to read account_cache from storage",
			"Failed imported to deferred pool"),
	
	Unknown(null, "unknown");
	
	private String prefix;
	private String[] texts;
	
	private SendTransactionError(String prefix, String... texts) {
		this.prefix = prefix;
		this.texts = texts;
	}
	
	private boolean matches(String message) {
		if (this.prefix != null && !message.startsWith(this.prefix)) {
			return false;
		}
		
		if (this.texts == null || this.texts.length == 0) {
			return true;
		}
		
		for (String text : this.texts) {
			if (message.contains(text)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static SendTransactionError parse(Error rpcError) {
		if (rpcError == null) {
			return null;
		}
		
		String message = rpcError.getMessage();
		if (message == null || message.isEmpty()) {
			return null;
		}
		
		for (SendTransactionError error : SendTransactionError.values()) {
			if (error.matches(message)) {
				return error;
			}
		}
		
		return Unknown;
	}

}
