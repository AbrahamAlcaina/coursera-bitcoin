

public class TxHandler {

    private UTXOPool in;
    private UTXOPool out;
    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
        this.in = utxoPool;
        this.out = new UTXOPool (this.in);
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {

        boolean allOutputsAreInUTXPool = true;
        boolean allSignaturesAreValid = true;
        boolean allUTXOareClaimed = true;
        boolean allOutputsAreNotNegative = true;
        boolean hasPositiveBalance;
        double sumInputs = 0.0;
        double sumOutputs = 0.0;
        for (Transaction.Input input: tx.getInputs()) {
            // 1
            UTXO txToFind = new UTXO(input.prevTxHash, input.outputIndex);
            allOutputsAreInUTXPool = allOutputsAreInUTXPool && this.out.contains(txToFind);
            // 2 wrong public key
            Transaction.Output out = this.out.getTxOutput(txToFind);
            //out.address
            allSignaturesAreValid = allSignaturesAreValid && Crypto.verifySignature(
                    tx.getOutput(input.outputIndex).address, tx.getRawDataToSign(input.outputIndex), input.signature);

            sumInputs += out.value;
        }
        // 3 pending
        // 4
        for (Transaction.Output output: tx.getOutputs()) {
            allOutputsAreNotNegative = allOutputsAreNotNegative && (output.value >= 0);
            sumOutputs += output.value;
        }
        // 5
        hasPositiveBalance = sumInputs > sumOutputs;
        return allOutputsAreInUTXPool && allSignaturesAreValid && allUTXOareClaimed && allOutputsAreNotNegative && hasPositiveBalance;

    }



    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS
        Transaction [] transactions = new Transaction[]{};
        return transactions;
    }

}
