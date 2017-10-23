package dnasplicing;

public class LinkedDnaStrand implements DnaStrand {

	DnaSequenceNode top = null;
	DnaSequenceNode current;
	DnaSequenceNode dummyP;
	DnaSequenceNode dummyC;
	DnaSequenceNode dummyN;
	int nodeCount = 0;
	int appendCount = 0;
	LinkedDnaStrand ret = null;

	public LinkedDnaStrand(String dna) {
		DnaSequenceNode sampleDna = new DnaSequenceNode(dna);
		top = sampleDna;
		nodeCount++;

	}

	/**
	 * Returns the number of nucleotides in this strand.
	 * 
	 * @return the number of base-pairs in this strand
	 */
	@Override
	public long getNucleotideCount() {

		return toString().length();
	}

	/**
	 * Appends the given dnaSequence on to the end of this DnaStrand.
	 * appendCount is incremented. Note: If this DnaStrand is empty, append()
	 * should just do the same thing as the constructor. In this special case,
	 * appendCount is not incremented.
	 * 
	 * @param dnaSequence
	 *            is the DNA string to append
	 */
	@Override
	public void append(String dnaSequence) {
		if (top == null) {
			DnaSequenceNode sampleDna = new DnaSequenceNode(dnaSequence);
			top = sampleDna;
			nodeCount++;
		} else {
			current = top;
			while (current.next != null) {
				current = current.next;
			}
			DnaSequenceNode sampleDna = new DnaSequenceNode(dnaSequence);
			sampleDna.previous = current;
			current.next = sampleDna;
			appendCount++;
			nodeCount++;
		}

	}

	/**
	 * This method creates a <bold>new</bold> DnaStrand that is a clone of the
	 * current DnaStrand, but with every instance of enzyme replaced by splicee.
	 * For example, if the LinkedDnaStrand is instantiated with "TTGATCC", and
	 * cutSplice("GAT", "TTAAGG") is called, then the linked list should become
	 * something like (previous pointers not shown):
	 * 
	 * first -> "TT" -> "TTAAGG" -> "CC" -> null
	 * 
	 * <b>NOTE</b>: This method will only be called when the linke list has just
	 * one node, and it will only be called once for a DnaStrand. This means
	 * that you do not need to worry about searching for enzyme matches across
	 * node boundaries.
	 * 
	 * @param enzyme
	 *            is the DNA sequence to search for in this DnaStrand.
	 * 
	 * @param splicee
	 *            is the DNA sequence to append in place of the enzyme in the
	 *            returned DnaStrand
	 * 
	 * @return A <bold>new</bold> strand leaving the original strand unchanged.
	 */
	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {
		int pos = 0;
		int start = 0;
		StringBuilder search = new StringBuilder(toString());
		boolean first = true;
		while ((pos = search.indexOf(enzyme, pos)) >= 0) {

			if ((pos != start)) {
				if (first) {
					ret = new LinkedDnaStrand(search.substring(start, pos));
					first = false;
				} else {
					ret.append(search.substring(start, pos));
				}

			}
			if (first) {
				ret = new LinkedDnaStrand(splicee);
				first = false;
			} else
				ret.append(splicee);

			start = pos + enzyme.length();
			pos++;
		}
		if (start < search.length()) {
			// NOTE: This is an important special case! If the enzyme
			// is never found, return an empty String.
			if (ret == null) {
				ret = new LinkedDnaStrand("");
			} else {
				ret.append(search.substring(start));

			}
		}

		return ret;
	}

	/**
	 * Returns a <bold>new</bold> DnaStrand that is the reverse of this strand,
	 * e.g., if this DnaStrand contains "CGAT", then the returned DnaStrand
	 * should contain "TAGC".
	 * 
	 * @return A <bold>new</bold> strand containing a reversed DNA sequence.
	 */

	@Override
	public DnaStrand createReversedDnaStrand() {
		StringBuilder copy = new StringBuilder(toString());
		LinkedDnaStrand ss = new LinkedDnaStrand(copy.reverse().toString());

		return ss;

	}

	@Override
	public int getAppendCount() {
		// TODO Auto-generated method stub
		return appendCount;
	}

	@Override
	public DnaSequenceNode getFirstNode() {
		if (nodeCount == 0)
			return null;

		return top;
	}

	@Override
	public int getNodeCount() {

		return nodeCount;
	}

	/**
	 * @return The entire DNA sequence represented by this DnaStrand.
	 */
	@Override
	public String toString() {
		current = top;
		StringBuilder sb = new StringBuilder();
		while (current != null) {
			sb.append(current.dnaSequence);
			current = current.next;
		}
		return sb.toString();
	}

}

/*
 * String dnaFragment = null; LinkedDnaStrand linkedDnaStrand = null;
 * StringBuilder sb = new StringBuilder(); int index = sb.indexOf(enzyme); int
 * indexCurrent = 0; int localCount = 0;
 * 
 * // building Original dna strand to sb while (current != null) {
 * sb.append(current.dnaSequence); current = current.next; }
 * 
 * // putting dna strands in linked list with changes
 * 
 * while (index != -1) {
 * 
 * dnaFragment = sb.substring(indexCurrent, index); sb.replace(index, index +
 * enzyme.length(), splicee); index += splicee.length(); // Move to the end of
 * the replacement index = sb.indexOf(enzyme, index);
 * 
 * if (localCount == 0) { linkedDnaStrand = new LinkedDnaStrand(dnaFragment);
 * localCount++; } else linkedDnaStrand.append(dnaFragment);
 * 
 * }
 * 
 * return linkedDnaStrand;
 */

/*
 * // P = Previous C = Current N = Next
 * 
 * while (dummyC != null) {
 * 
 * // dealing with first node if (dummyC.previous == null) { dummyN =
 * dummyC.next; // dummyN is next node // re-hooking previous and next nodes
 * dummyC.previous = dummyN; dummyC.next = null; // setting up for next node //
 * P is previous dummyP = dummyC; // C is current dummyC = dummyN; }
 * 
 * // dealing with middle nodes if (dummyN != null) { // N is next dummyN =
 * dummyC.next; // re-hooking previous and next nodes dummyC.previous = dummyN;
 * dummyC.next = dummyP; // p is previous dummyP = dummyC; // C is current
 * dummyC = dummyN; }
 * 
 * }
 */
