package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

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


    @Override
    public void EvaluateNode(ActivationFrame frame, Interpreter interpreter) {
        for (Node n : this.getInstructions()) {
            if (n instanceof BooleanOperatorNode || n instanceof ParenthesizedExpressionNode) {
                n.EvaluateNode(frame, interpreter);
            }
            else {
                if ( n instanceof FinalNode && ( !((FinalNode) n).dataString().equals("(") && !((FinalNode) n).dataString().equals( ")") )){
                    interpreter.pushOperatorStack(n);
                } else if ( n instanceof VariableNode || n instanceof FunctionCallNode || n instanceof  LiteralNode){
                    interpreter.pushOperandStack( n );
                }
            }
        }
    }
}
