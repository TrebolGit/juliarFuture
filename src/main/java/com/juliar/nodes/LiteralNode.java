package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;

public class LiteralNode extends NodeImpl {
    @Override
    public NodeType getType() {
        return NodeType.LiteralType;
    }

    public Boolean isEqual( LiteralNode literalNode){
        return (( FinalNode )getInstructions().get( 0 )).dataString().equals( ( (FinalNode )literalNode.getInstructions().get(0)).dataString());
    }

    @Override
    public Object getRealValue(ActivationFrame frame) {
        return (( FinalNode )getInstructions().get( 0 )).dataString();
    }
}
