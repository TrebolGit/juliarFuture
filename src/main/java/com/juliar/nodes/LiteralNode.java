package com.juliar.nodes;

public class LiteralNode extends NodeImpl {
    @Override
    public NodeType getType() {
        return NodeType.LiteralType;
    }

    public Boolean isEqual( LiteralNode literalNode){
        return (( FinalNode )getInstructions().get( 0 )).dataString().equals( ( (FinalNode )literalNode.getInstructions().get(0)).dataString());
    }
}
