package com.juliar.nodes;

import com.juliar.interpreter.ActivationFrame;
import com.juliar.interpreter.Interpreter;

import static com.juliar.nodes.NodeType.EvaluatableType;

public class EvaluatableNode extends NodeImpl {

    @Override
    public NodeType getType() {
        return EvaluatableType;

    }


    @Override
    public void EvaluateNode(ActivationFrame frame, Interpreter interpreter) {
        for ( Node n : this.getInstructions()) {
            if (n instanceof BooleanOperatorNode || n instanceof ParenthesizedExpressionNode) {
                n.EvaluateNode(frame, interpreter);
            }
        }

        /*
        if (n instanceof FinalNode) {
            interpreter.pushOperatorStack(n);
        } else if (n instanceof VariableNode || n instanceof FunctionCallNode || n instanceof LiteralNode) {
            interpreter.pushOperandStack(n);
        }
        if (n.getInstructions().size() == 3) {
            EvaluateNode(n.getInstructions().get(0));
            EvaluateNode(n.getInstructions().get(1));
            EvaluateNode(n.getInstructions().get(2));
        }*/


    }
/*
    public void evaluateExpression(ActivationFrame frame, Interpreter interpreter) {
        try {


            Node node = this;
            Node lvalue = null;
            boolean isEqualEqual;
            Node operatorTypeNode = null;
            Node rvalue = null;
            this.interpreter = interpreter;


            // This is ugly code. Need to find a better way to
            // handle these cases.
            // Multiple ifs will only cause confusion.
            FinalNode updatedLvalue = null;

            int instructionCount = getInstructions().size();

            if (instructionCount == 3) {

                lvalue = getInstructions().get(0);
                operatorTypeNode = getInstructions().get(1);
                rvalue = getInstructions().get(2);

                EvaluateNode(this);

                // frame.pushReturnNode( booleanNode );
            }
        } catch (Exception ex) {
            Logger.log(ex.getMessage());
        }
   }
   */
}
