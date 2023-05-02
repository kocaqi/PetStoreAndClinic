/**
 * Given a node, returns the ownerDocument if it exists, otherwise the
 * global document. (Maybe this should go in a root utils file?)
 */
export default function ownerDocument(node?: HTMLElement | null): Document;
