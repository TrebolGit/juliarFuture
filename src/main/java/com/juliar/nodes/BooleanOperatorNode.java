package com.juliar.nodes;

import com.juliar.errors.Logger;
import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by don on 4/1/17.
 */
@SuppressWarnings("serial")
public class BooleanOperatorNode extends NodeImpl {

    private Interpreter interpreter;

    @Override
    public NodeType getType() {
        return NodeType.BooleanOperatorType;
    }


    @Override
    public void EvaluateNode(ActivationFrame frame, Interpreter interpreter) {
        boolean shouldEvaluate = false;
        for (Node n : this.getInstructions()) {
            shouldEvaluate = true;
            if (n instanceof BooleanOperatorNode || n instanceof ParenthesizedExpressionNode) {
                n.EvaluateNode(frame, interpreter);
            }
            else {
                if ( n instanceof FinalNode){
                    interpreter.pushOperatorStack(n);
                }
                if ( n instanceof VariableNode || n instanceof FunctionCallNode || n instanceof  LiteralNode){
                    interpreter.pushOperandStack( n );
                }
            }
        }

        if ( shouldEvaluate ) {
            interpreter.evaluateExpressionStack();
        }
    }
}
