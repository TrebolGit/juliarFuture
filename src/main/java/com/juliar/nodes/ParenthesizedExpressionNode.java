package com.juliar.nodes;

import static com.juliar.nodes.NodeType.ParenthesizedNode;

public class ParenthesizedExpressionNode extends NodeImpl  {
    @Override
    public NodeType getType() {
        return ParenthesizedNode;
    }

    @Override
    public IntegralType getIntegralType() {
        return super.getIntegralType();
    }
}
